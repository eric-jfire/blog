package cn.jfire.blog.dao;

import com.jfireframework.sql.annotation.Query;
import cn.jfire.blog.entity.Admin;

public interface AdminOp
{
    @Query(sql = "select * from Admin where username = $username", paramNames = "username")
    public Admin get(String username);
}
