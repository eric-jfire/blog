package link.jfire.blog.dao;

import link.jfire.sql.annotation.Query;

public interface LoginOp
{
    @Query(sql = "select count(id) from user where username=$username and password=$password", paramNames = { "username", "password" })
    public int login(String username, String password);
}
