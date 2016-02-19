package link.jfire.blog.listener;

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
import javax.servlet.http.HttpSession;

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
        HttpSession session = rq.getSession();
        String path = rq.getRequestURI();
        if (path.endsWith("login"))
        {
            chain.doFilter(request, response);
            return;
        }
        if (session.getAttribute("code") == null)
        {
            ((HttpServletResponse) response).sendRedirect(request.getServletContext().getContextPath() + "/login");
        }
        else
        {
            chain.doFilter(request, response);
        }
    }
    
    @Override
    public void destroy()
    {
        // TODO Auto-generated method stub
        
    }
    
}
