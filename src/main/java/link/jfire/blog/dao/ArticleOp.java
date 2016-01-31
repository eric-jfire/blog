package link.jfire.blog.dao;

import java.util.List;
import link.jfire.blog.entity.Article;
import link.jfire.sql.annotation.Query;
import link.jfire.sql.annotation.Update;
import link.jfire.sql.page.MysqlPage;

public interface ArticleOp
{
    @Query(sql = "select title,createtime,updatetime,markdown,id,category from Article [$title] where title like $%title% #", paramNames = { "title", "page" })
    public List<Article> list(String title, MysqlPage page);
    
    @Update(sql = "delete from Article where id in $~ids", paramNames = { "ids" })
    public void delete(int[] ids);
}
