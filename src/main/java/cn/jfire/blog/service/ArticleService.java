package cn.jfire.blog.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import com.jfireframework.context.aop.annotation.AutoCloseResource;
import com.jfireframework.context.aop.annotation.Transaction;
import com.jfireframework.mvc.util.BeetlRender;
import com.jfireframework.sql.page.MysqlPage;
import cn.jfire.blog.dao.BaseDao;
import cn.jfire.blog.entity.Article;

@Resource
public class ArticleService
{
    @Resource
    private BaseDao        baseDao;
    @Resource
    private BeetlRender    render;
    @Resource
    private ServletContext servletContext;
    private String         path;
    
    @PostConstruct
    public void init()
    {
        path = servletContext.getRealPath("/article");
    }
    
    @Transaction
    public void save(Article article)
    {
        if (article.getId() == null)
        {
            article.setCreatetime(new Date());
            article.setUpdatetime(article.getCreatetime());
            article.setTop(false);
            baseDao.save(article);
        }
        else
        {
            article.setUpdatetime(new Date());
            baseDao.getSession().selectUpdate(article, "text_content,html_content,updatetime,title,top");
        }
        try
        {
            render(article);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public void render(Article article) throws IOException
    {
        try (FileOutputStream fileOutputStream = new FileOutputStream(new File(path + File.separator + article.getId())))
        {
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("title", article.getTitle());
            data.put("content", article.getText_content());
            if (article.isMarkdown())
            {
                render.render("/admin/template/md.html", data, servletContext, fileOutputStream);
            }
            else
            {
                render.render("/admin/template/richtext.html", data, servletContext, fileOutputStream);
            }
        }
        catch (IOException e)
        {
            throw e;
        }
    }
    
    @AutoCloseResource
    public void list(MysqlPage page, String title)
    {
        baseDao.getArticleOp().list(title, page);
    }
    
    @AutoCloseResource
    public List<Article> listWithContent()
    {
        return baseDao.getArticleOp().listWithContent();
    }
    
    @AutoCloseResource
    public Article get(int id)
    {
        return baseDao.getSession().get(Article.class, id);
    }
    
    @Transaction
    public void delete(int[] ids)
    {
        baseDao.getArticleOp().delete(ids);
    }
}
