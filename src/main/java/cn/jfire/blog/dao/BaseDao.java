package cn.jfire.blog.dao;

import javax.annotation.Resource;
import com.jfireframework.sql.function.SessionFactory;
import com.jfireframework.sql.function.SqlSession;

@Resource
public class BaseDao
{
    @Resource
    protected SessionFactory sessionFactory;
    
    public void flush()
    {
        getSession().flush();
    }
    
    public void save(Object entity)
    {
        SqlSession session = getSession();
        session.save(entity);
    }
    
    public void delete(Object entity)
    {
        SqlSession session = getSession();
        session.delete(entity);
    }
    
    public SqlSession getSession()
    {
        SqlSession session = sessionFactory.getCurrentSession();
        if (session == null)
        {
            session = sessionFactory.openSession();
            sessionFactory.setCurrentSession(session);
        }
        return session;
    }
    
    public ArticleOp getArticleOp()
    {
        return getSession().getMapper(ArticleOp.class);
    }
    
    public AdminOp getAdminOp()
    {
        return getSession().getMapper(AdminOp.class);
    }
    
}
