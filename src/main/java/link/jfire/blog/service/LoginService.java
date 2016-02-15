package link.jfire.blog.service;

import javax.annotation.Resource;
import link.jfire.blog.dao.BaseDao;
import link.jfire.core.aop.annotation.AutoCloseResource;

@Resource
public class LoginService
{
    @Resource
    private BaseDao baseDao;
    
    @AutoCloseResource
    public boolean login(String username, String password)
    {
        if (baseDao.getLoginOp().login(username, password) == 1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
