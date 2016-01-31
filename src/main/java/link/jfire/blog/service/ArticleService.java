package link.jfire.blog.service;

import java.util.Date;
import javax.annotation.Resource;
import link.jfire.blog.dao.BaseDao;
import link.jfire.blog.entity.Article;
import link.jfire.core.aop.annotation.AutoCloseResource;
import link.jfire.core.aop.annotation.Transaction;
import link.jfire.sql.page.MysqlPage;

@Resource
public class ArticleService
{
    @Resource
    private BaseDao baseDao;
    
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
