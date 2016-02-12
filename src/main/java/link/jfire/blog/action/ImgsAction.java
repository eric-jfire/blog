package link.jfire.blog.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import link.jfire.blog.service.UploadToQiniuService;
import link.jfire.mvc.annotation.ActionClass;
import link.jfire.mvc.annotation.ActionMethod;
import link.jfire.mvc.annotation.ContentType;
import link.jfire.mvc.annotation.RequestMethod;
import link.jfire.mvc.binder.UploadItem;
import link.jfire.mvc.config.ResultType;

@Resource
@ActionClass("imgs")
public class ImgsAction
{
    @Resource
    private UploadToQiniuService service;
    public static final String   TMP_IMGS      = "tmpimgs" + System.currentTimeMillis();
    public static final String   QINIU_DOMAIN  = "qiniudomain" + System.currentTimeMillis();
    public static final String   BucketManager = "bucketmanager" + System.currentTimeMillis();
                                               
    @SuppressWarnings("unchecked")
    @ActionMethod(resultType = ResultType.String, contentType = ContentType.JSON, url = "wangEditor", methods = { RequestMethod.POST })
    public String post(UploadItem item, HttpSession session)
    {
        try
        {
            String url = service.upload(item.getPart().getInputStream());
            if (session.getAttribute(TMP_IMGS) == null)
            {
                List<String> urls = new LinkedList<>();
                urls.add(url);
                session.setAttribute(TMP_IMGS, urls);
                session.setAttribute(QINIU_DOMAIN, service.getDomain());
                session.setAttribute(BucketManager, service.getBucketManager());
            }
            else
            {
                List<String> urls = (List<String>) session.getAttribute(TMP_IMGS);
                urls.add(url);
            }
            return url;
        }
        catch (IOException e)
        {
            return "error|" + e.getMessage();
        }
    }
    
    @SuppressWarnings("unchecked")
    @ActionMethod(resultType = ResultType.Json, contentType = ContentType.JSON, url = "md", methods = { RequestMethod.POST })
    public Map<String, Object> post2(UploadItem item, HttpSession session)
    {
        try
        {
            String url = service.upload(item.getPart().getInputStream());
            if (session.getAttribute(TMP_IMGS) == null)
            {
                List<String> urls = new LinkedList<>();
                urls.add(url);
                session.setAttribute(TMP_IMGS, urls);
                session.setAttribute(QINIU_DOMAIN, service.getDomain());
                session.setAttribute(BucketManager, service.getBucketManager());
            }
            else
            {
                List<String> urls = (List<String>) session.getAttribute(TMP_IMGS);
                urls.add(url);
            }
            HashMap<String, Object> result = new HashMap<>();
            result.put("success", 1);
            result.put("url", url);
            return result;
        }
        catch (IOException e)
        {
            HashMap<String, Object> result = new HashMap<>();
            result.put("success", 0);
            result.put("message", e.getMessage());
            return result;
        }
    }
}
