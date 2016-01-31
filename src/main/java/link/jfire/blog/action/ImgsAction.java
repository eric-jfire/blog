package link.jfire.blog.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
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
    
    @ActionMethod(resultType = ResultType.String, contentType = ContentType.JSON, url = "wangEditor", methods = { RequestMethod.POST })
    public String post(UploadItem item)
    {
        try
        {
            String url = service.upload(item.getPart().getInputStream());
            return url;
        }
        catch (IOException e)
        {
            return "error|" + e.getMessage();
        }
    }
    
    @ActionMethod(resultType = ResultType.Json, contentType = ContentType.JSON, url = "md", methods = { RequestMethod.POST })
    public Map<String, Object> post2(UploadItem item)
    {
        try
        {
            String url = service.upload(item.getPart().getInputStream());
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
