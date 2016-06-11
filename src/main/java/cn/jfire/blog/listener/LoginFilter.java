package cn.jfire.blog.listener;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(value = "/*", asyncSupported = true)
public class LoginFilter implements Filter
{
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        HttpServletRequest rq = (HttpServletRequest) request;
        if (permit(rq))
        {
            chain.doFilter(request, response);
        }
        else
        {
            ((HttpServletResponse) response).sendRedirect(request.getServletContext().getContextPath() + "/admin");
        }
    }
    
    private boolean permit(HttpServletRequest request)
    {
        if (request.getSession().getAttribute("admin") != null)
        {
            System.out.println("登陆后放行");
            return true;
        }
        String path = request.getRequestURI();
        if (path.endsWith("/admin") //
                || path.endsWith(".js") //
                || path.endsWith(".jpg")//
                || path.endsWith(".css") //
                || path.endsWith("gif"))
        {
            return true;
        }
        if (path.startsWith(request.getServletContext().getContextPath() + "/admin/admins") && request.getMethod().equals("GET"))
        {
            return true;
        }
        return false;
    }
    
    @Override
    public void destroy()
    {
        // TODO Auto-generated method stub
        
    }
    
}
