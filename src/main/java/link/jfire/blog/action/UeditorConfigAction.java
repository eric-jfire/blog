package link.jfire.blog.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import link.jfire.baseutil.simplelog.ConsoleLogFactory;
import link.jfire.baseutil.simplelog.Logger;
import link.jfire.blog.service.UploadToQiniuService;
import link.jfire.codejson.JsonObject;
import link.jfire.codejson.JsonTool;
import link.jfire.mvc.annotation.ActionClass;
import link.jfire.mvc.annotation.ActionMethod;
import link.jfire.mvc.binder.UploadItem;
import link.jfire.mvc.config.ResultType;

/**
 * 用来执行ueditor的配置回传和上传图片的功能
 * 
 * @author 林斌
 * 
 */
@Resource
@ActionClass("ueditorConfigAction")
public class UeditorConfigAction
{
    private Logger               logger = ConsoleLogFactory.getLogger();
    
    @Resource
    private UploadToQiniuService service;
    
    @ActionMethod(resultType = ResultType.Json)
    public JsonObject jsConfig(String action, UploadItem item, HttpServletRequest request) throws URISyntaxException, IOException
    {
        if (action.equals("config"))
        {
            logger.debug("输出ueditor配置");
            File configFile = new File(this.getClass().getClassLoader().getResource("config.json").toURI());
            FileInputStream inputStream = new FileInputStream(configFile);
            byte[] array = new byte[inputStream.available()];
            inputStream.read(array);
            JsonObject jsonObject = (JsonObject) JsonTool.fromString(new String(array));
            return jsonObject;
        }
        else if (action.equals("uploadimage"))
        {
            JsonObject jsonObject = new JsonObject();
            jsonObject.put("state", "SUCCESS");
            jsonObject.put("url", service.upload(item.getPart().getInputStream()));
            return jsonObject;
        }
        else
        {
            return null;
        }
    }
}
