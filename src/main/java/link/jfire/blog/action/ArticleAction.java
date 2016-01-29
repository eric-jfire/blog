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

@Resource
@ActionClass("admin/article")
public class ArticleAction
{
    @Resource
    private ArticleService articleService;
    
    @ActionMethod(resultType = ResultType.Json, url = "/", methods = { RequestMethod.POST })
    public BaseResult post(@RequestParam("") Article article)
    {
        articleService.save(article);
        return new BaseResult();
    }
}
