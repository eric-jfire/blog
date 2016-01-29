package link.jfire.blog.action;

import java.io.IOException;
import java.util.HashMap;
import javax.annotation.Resource;
import link.jfire.blog.service.UploadToQiniuService;
import link.jfire.codejson.JsonTool;
import link.jfire.mvc.annotation.ActionClass;
import link.jfire.mvc.annotation.ActionMethod;
import link.jfire.mvc.annotation.ContentType;
import link.jfire.mvc.annotation.RequestMethod;
import link.jfire.mvc.binder.UploadItem;
import link.jfire.mvc.config.ResultType;

@Resource
@ActionClass("admin/imgs")
public class ImgsAction
{
    @Resource
    private UploadToQiniuService service;
    
    @ActionMethod(resultType = ResultType.String, contentType = ContentType.JSON,url="/",methods={RequestMethod.POST})
    public String post(UploadItem item)
    {
        try
        {
            String url = service.upload(item.getPart().getInputStream());
            HashMap<String, Object> result = new HashMap<>();
            result.put("success", 1);
            result.put("url", url.replace("\"", "\\\""));
            return JsonTool.write(result);
        }
        catch (IOException e)
        {
            HashMap<String, Object> result = new HashMap<>();
            result.put("success", 0);
            return JsonTool.write(result);
        }
    }
}
