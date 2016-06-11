package cn.jfire.blog.dao;

import java.util.List;
import com.jfireframework.sql.annotation.Query;
import com.jfireframework.sql.annotation.Update;
import com.jfireframework.sql.page.MysqlPage;
import cn.jfire.blog.entity.Article;

public interface ArticleOp
{
    @Query(sql = "select title,createtime,updatetime,markdown,id,category from Article [$title] where title like $%title% #", paramNames = "title")
    public List<Article> list(String title, MysqlPage page);
    
    @Update(sql = "delete from Article where id in $~ids", paramNames = "ids")
    public void delete(int[] ids);
    
    @Query(sql = "select id,title,text_content,markdown,category from Article", paramNames = "")
    public List<Article> listWithContent();
}
