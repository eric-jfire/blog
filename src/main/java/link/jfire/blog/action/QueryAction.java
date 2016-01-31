package link.jfire.blog.action;

import javax.annotation.Resource;
import link.jfire.baseutil.collection.StringCache;
import link.jfire.blog.service.ArticleService;
import link.jfire.blog.vo.BaseResult;
import link.jfire.codejson.function.WriterAdapter;
import link.jfire.codejson.strategy.WriteStrategy;
import link.jfire.core.bean.annotation.field.InitMethod;
import link.jfire.mvc.annotation.ActionClass;
import link.jfire.mvc.annotation.ActionMethod;
import link.jfire.mvc.annotation.ContentType;
import link.jfire.mvc.annotation.RequestMethod;
import link.jfire.mvc.config.ResultType;
import link.jfire.sql.page.MysqlPage;

@Resource
@ActionClass("query")
public class QueryAction
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
                value = value.replace("\"", "\\\"");
                cache.append('"').append(value).append('"');
            }
        });
        strategy.addFieldStrategy("link.jfire.blog.entity.Article.text_content", new WriterAdapter() {
            @Override
            public void write(Object field, StringCache cache, Object entity)
            {
                String value = (String) field;
                value = value.replace("\"", "\\\"");
                cache.append('"').append(value).append('"');
            }
        });
    }
    
    @ActionMethod(resultType = ResultType.String, methods = { RequestMethod.POST }, contentType = ContentType.JSON)
    public String articles(int page, int rows, String title)
    {
        MysqlPage mysqlPage = new MysqlPage();
        mysqlPage.setPage(page);
        mysqlPage.setPageSize(rows);
        articleService.list(mysqlPage, title);
        BaseResult result = new BaseResult();
        result.setTotal(mysqlPage.getTotal());
        result.setRows(mysqlPage.getData());
        return strategy.write(result);
    }
}
