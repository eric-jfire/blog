package link.jfire.blog.dao;

import java.util.List;
import link.jfire.blog.entity.Article;
import link.jfire.sql.annotation.Query;
import link.jfire.sql.page.MysqlPage;

public interface ArticleOp
{
    @Query(sql = "select * from Article [$title] where title like $%title% #", paramNames = { "title", "page" })
    public List<Article> list(String title, MysqlPage page);
}
