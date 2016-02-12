package link.jfire.blog.action;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import link.jfire.baseutil.collection.StringCache;
import link.jfire.blog.entity.Article;
import link.jfire.blog.service.ArticleService;
import link.jfire.blog.service.QiniuService;
import link.jfire.blog.vo.BaseResult;
import link.jfire.core.bean.annotation.field.InitMethod;
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
    private ArticleService articleService;
    @Resource
    private QiniuService   qiniuService;
                           
    private File           dir;
    @Resource
    private ServletContext context;
                           
    @InitMethod
    public void InitMethod()
    {
        dir = new File(context.getRealPath("tmpimg"));
    }
    
    @SuppressWarnings("unchecked")
    @ActionMethod(resultType = ResultType.Json, url = "/", methods = { RequestMethod.POST })
    public BaseResult post(@RequestParam("") Article article, HttpSession session)
    {
        List<String> urls = (List<String>) session.getAttribute(ImgsAction.TMP_IMGS);
        StringCache cache = new StringCache();
        if (urls != null)
        {
            Iterator<String> it = urls.iterator();
            String content = article.getText_content();
            while (it.hasNext())
            {
                String each = it.next();
                if (content.contains(each) == false)
                {
                    it.remove();
                }
            }
            for (String each : urls)
            {
                int index = each.indexOf("tmpimg");
                FileInputStream inputStream;
                try
                {
                    inputStream = new FileInputStream(new File(dir, each.substring(index + 6)));
                    String url = qiniuService.upload(inputStream);
                    inputStream.close();
                    content = content.replace(each, url);
                    cache.append(url).appendComma();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            article.setText_content(content);
        }
        session.setAttribute(ImgsAction.TMP_IMGS, null);
        article.setImgs(cache.toString());
        articleService.save(article);
        return new BaseResult();
    }
}
