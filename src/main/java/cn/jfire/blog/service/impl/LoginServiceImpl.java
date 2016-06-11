package cn.jfire.blog.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import javax.annotation.Resource;
import com.jfireframework.baseutil.encrypt.Md5Util;
import com.jfireframework.context.aop.annotation.Transaction;
import cn.jfire.blog.dao.BaseDao;
import cn.jfire.blog.entity.Admin;
import cn.jfire.blog.service.LoginService;

@Resource
public class LoginServiceImpl implements LoginService
{
    @Resource
    private BaseDao basedao;
    
    @Override
    @Transaction
    public Admin login(String username, String password, String code)
    {
        try
        {
            username = URLDecoder.decode(username, "utf8");
        }
        catch (UnsupportedEncodingException e)
        {
        }
        Admin admin = basedao.getAdminOp().get(username);
        if (admin == null)
        {
            return null;
        }
        if (password.equals(Md5Util.md5Str(admin.getPassword() + code)))
        {
            admin.setLastLoginTime(new Date());
            basedao.getSession().selectUpdate(admin, "lastLoginTime");
            return admin;
        }
        else
        {
            
            return null;
        }
    }
    
}
