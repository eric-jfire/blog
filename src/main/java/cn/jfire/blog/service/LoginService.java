package cn.jfire.blog.service;

import cn.jfire.blog.entity.Admin;

public interface LoginService
{
    public Admin login(String username, String password, String code);
}
