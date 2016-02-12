package link.jfire.blog.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import link.jfire.blog.action.ViewAction;
import link.jfire.blog.dao.BaseDao;
import link.jfire.blog.entity.Article;
import link.jfire.core.aop.annotation.AutoCloseResource;
import link.jfire.core.aop.annotation.Transaction;
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
            baseDao.getSession().selectUpdate(article, "text_content,html_content,updatetime,title");
        }
        String path = servletContext.getRealPath("/view");
        if (article.isMarkdown())
        {
        }
        else
        {
            try
            {
                FileOutputStream fileOutputStream = new FileOutputStream(new File(path + File.separator + article.getId()));
                Map<String, Object> data = new HashMap<String, Object>();
                data.put("title", article.getTitle());
                data.put("content", article.getText_content());
                render.render("/admin/template/richtext.html", data, servletContext, fileOutputStream);
                fileOutputStream.close();
                viewAction.delete(article.getId());
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    @AutoCloseResource
    public void list(MysqlPage page, String title)
    {
        baseDao.getArticleOp().list(title, page);
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
