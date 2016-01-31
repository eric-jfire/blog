package link.jfire.blog.action;

import javax.annotation.Resource;
import link.jfire.blog.entity.Article;
import link.jfire.blog.service.ArticleService;
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
    private ArticleService articleService;
    
    @ActionMethod(resultType = ResultType.Json, url = "/", methods = { RequestMethod.POST })
    public BaseResult post(@RequestParam("") Article article)
    {
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
