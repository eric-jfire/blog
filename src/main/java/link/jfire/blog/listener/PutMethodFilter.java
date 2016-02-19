package link.jfire.blog.listener;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import link.jfire.mvc.interceptor.impl.DataBinderInterceptor;
import link.jfire.mvc.util.PutRequestHelpFilter;

@WebFilter(value = "/*", asyncSupported = true)
public class PutMethodFilter extends PutRequestHelpFilter
{
    private Charset charset = Charset.forName("utf-8");
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        HttpServletRequest rq = (HttpServletRequest) request;
        if (rq.getMethod().equals("PUT"))
        {
            request.setCharacterEncoding("utf-8");
            byte[] src = new byte[rq.getContentLength()];
            rq.getInputStream().read(src);
            request.setAttribute(DataBinderInterceptor.DATABINDERKEY, URLDecoder.decode(new String(src, charset), "utf-8"));
            chain.doFilter(request, response);
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
