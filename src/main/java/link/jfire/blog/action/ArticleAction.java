package link.jfire.blog.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import link.jfire.baseutil.collection.StringCache;
import link.jfire.blog.entity.Article;
import link.jfire.blog.service.ArticleService;
import link.jfire.blog.service.QiniuService;
import link.jfire.blog.vo.BaseResult;
import link.jfire.codejson.function.WriterAdapter;
import link.jfire.codejson.strategy.WriteStrategy;
import link.jfire.core.bean.annotation.field.InitMethod;
import link.jfire.mvc.annotation.ActionClass;
import link.jfire.mvc.annotation.ActionMethod;
import link.jfire.mvc.annotation.RequestMethod;
import link.jfire.mvc.annotation.RequestParam;
import link.jfire.mvc.config.ResultType;

@Resource
@ActionClass("article")
public class ArticleAction
{
    @Resource
    private ArticleService articleService;
    private WriteStrategy  strategy = new WriteStrategy();
    @Resource
    private QiniuService   qiniuService;
                           
    private File           dir;
    @Resource
    private ServletContext context;
                           
    @InitMethod
    public void init()
    {
        dir = new File(context.getRealPath("tmpimg"));
        strategy.addFieldStrategy("link.jfire.blog.entity.Article.html_content", new WriterAdapter() {
            @Override
            public void write(Object field, StringCache cache, Object entity)
            {
                String value = (String) field;
                value = value.replace("\"", "\\\"").replace("\r\n", "\\r\\n").replace("\n", "\\n").replace("\r", "\\r");
                cache.append('"').append(value).append('"');
            }
        });
        strategy.addFieldStrategy("link.jfire.blog.entity.Article.text_content", new WriterAdapter() {
            @Override
            public void write(Object field, StringCache cache, Object entity)
            {
                String value = (String) field;
                value = value.replace("\"", "\\\"").replace("\r\n", "\\r\\n").replace("\n", "\\n").replace("\r", "\\r");
                cache.append('"').append(value).append('"');
            }
        });
    }
    
    @ActionMethod(resultType = ResultType.String, url = "{id}", methods = { RequestMethod.GET })
    public String get(int id)
    {
        BaseResult result = new BaseResult();
        result.setData(articleService.get(id));
        return strategy.write(result);
    }
    
    @SuppressWarnings("unchecked")
    @ActionMethod(resultType = ResultType.Json, url = "{id}", methods = { RequestMethod.POST })
    public BaseResult post(@RequestParam("") Article article, int id, HttpSession session)
    {
        article.setId(id);
        String content = article.getText_content();
        String imgs = articleService.getImgs(id);
        StringCache cache = new StringCache();
        for (String each : imgs.split(","))
        {
            if (content.contains(each) == false)
            {
                qiniuService.delete(each);
            }
            else
            {
                cache.append(each).appendComma();
            }
        }
        if (session.getAttribute(ImgsAction.TMP_IMGS) != null)
        {
            for (String each : (List<String>) session.getAttribute(ImgsAction.TMP_IMGS))
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
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        article.setText_content(content);
        article.setImgs(cache.toString());
        articleService.save(article);
        return new BaseResult();
    }
    
    @ActionMethod(resultType = ResultType.Json, url = "{ids}", methods = { RequestMethod.DELETE })
    public BaseResult delete(String ids)
    {
        String[] tmp = ids.split("-");
        int[] int_ids = new int[tmp.length];
        for (int i = 0, l = tmp.length; i < l; i++)
        {
            int_ids[i] = Integer.valueOf(tmp[i]);
        }
        articleService.delete(int_ids);
        return new BaseResult();
    }
    
}
