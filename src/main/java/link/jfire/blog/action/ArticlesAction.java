package link.jfire.blog.action;

import java.util.LinkedList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import link.jfire.blog.entity.Article;
import link.jfire.blog.service.ArticleService;
import link.jfire.blog.service.UploadToQiniuService;
import link.jfire.blog.vo.BaseResult;
import link.jfire.mvc.annotation.ActionClass;
import link.jfire.mvc.annotation.ActionMethod;
import link.jfire.mvc.annotation.RequestMethod;
import link.jfire.mvc.annotation.RequestParam;
import link.jfire.mvc.config.ResultType;

@ActionClass("articles")
@Resource
public class ArticlesAction
{
    @Resource
    private ArticleService       articleService;
    @Resource
    private UploadToQiniuService uploadToQiniuService;
                                 
    @SuppressWarnings("unchecked")
    @ActionMethod(resultType = ResultType.Json, url = "/", methods = { RequestMethod.POST })
    public BaseResult post(@RequestParam("") Article article, HttpSession session)
    {
        List<String> urls = (List<String>) session.getAttribute(ImgsAction.TMP_IMGS);
        List<String> deleteUrls = new LinkedList<>();
        if (urls != null)
        {
            for (String each : urls)
            {
                if (article.getText_content().contains(each) == false)
                {
                    deleteUrls.add(each);
                }
            }
        }
        String domain = uploadToQiniuService.getDomain();
        BucketManager bucketManager = uploadToQiniuService.getBucketManager();
        for (String each : deleteUrls)
        {
            try
            {
                bucketManager.delete("blog", each.replace(domain, ""));
            }
            catch (QiniuException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        session.setAttribute(ImgsAction.TMP_IMGS, null);
        articleService.save(article);
        return new BaseResult();
    }
}
