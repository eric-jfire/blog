package link.jfire.blog.listener;

import java.util.List;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import link.jfire.blog.action.ImgsAction;

@WebListener
public class UrlSessionListener implements HttpSessionListener
{
    
    @Override
    public void sessionCreated(HttpSessionEvent se)
    {
        // TODO Auto-generated method stub
        
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void sessionDestroyed(HttpSessionEvent se)
    {
        HttpSession session = se.getSession();
        List<String> urls = (List<String>) session.getAttribute(ImgsAction.TMP_IMGS);
        if (urls != null)
        {
            BucketManager bucketManager = (BucketManager) session.getAttribute(ImgsAction.BucketManager);
            String domain = (String) session.getAttribute(ImgsAction.QINIU_DOMAIN);
            for (String each : urls)
            {
                try
                {
                    bucketManager.delete("blog", each.replace(domain, ""));
                }
                catch (QiniuException e)
                {
                    e.printStackTrace();
                }
            }
        }
        
    }
    
}
