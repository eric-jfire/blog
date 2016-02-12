package link.jfire.blog.action;

import javax.annotation.Resource;
import link.jfire.baseutil.collection.StringCache;
import link.jfire.blog.entity.Article;
import link.jfire.blog.service.ArticleService;
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
    
    @InitMethod
    public void init()
    {
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
    
    @ActionMethod(resultType = ResultType.Json, url = "{id}", methods = { RequestMethod.POST })
    public BaseResult post(@RequestParam("") Article article, int id)
    {
        article.setId(id);
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
