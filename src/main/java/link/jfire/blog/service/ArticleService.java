package link.jfire.blog.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import link.jfire.blog.action.ViewAction;
import link.jfire.blog.dao.BaseDao;
import link.jfire.blog.entity.Article;
import link.jfire.core.aop.annotation.AutoCloseResource;
import link.jfire.core.aop.annotation.Transaction;
import link.jfire.core.bean.annotation.field.InitMethod;
import link.jfire.mvc.util.BeetlRender;
import link.jfire.sql.page.MysqlPage;

@Resource
public class ArticleService
{
    @Resource
    private BaseDao        baseDao;
    @Resource
    private BeetlRender    render;
    @Resource
    private ServletContext servletContext;
    @Resource
    private ViewAction     viewAction;
    private String         path;
                           
    @InitMethod
    public void init()
    {
        path = servletContext.getRealPath("/view");
    }
    
    @Transaction
    public void save(Article article)
    {
        if (article.getId() == null)
        {
            article.setCreatetime(new Date());
            article.setUpdatetime(article.getCreatetime());
            article.setType(Article.NORMAL);
            baseDao.save(article);
        }
        else
        {
            article.setUpdatetime(new Date());
            baseDao.getSession().selectUpdate(article, "text_content,html_content,updatetime,title,imgs");
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
            viewAction.delete(article.getId());
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
    
    @AutoCloseResource
    public String getImgs(int id)
    {
        return baseDao.getSession().get(Article.class, id, "imgs").getImgs();
    }
    
    @Transaction
    public void delete(int[] ids)
    {
        baseDao.getArticleOp().delete(ids);
    }
}
