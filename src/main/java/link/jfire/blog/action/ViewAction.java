package link.jfire.blog.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import link.jfire.mvc.annotation.ActionClass;
import link.jfire.mvc.annotation.ActionMethod;
import link.jfire.mvc.annotation.RequestMethod;
import link.jfire.mvc.config.ResultType;

@Resource
@ActionClass("view")
public class ViewAction
{
    @Resource
    private ServletContext                     servletContext;
    private ConcurrentHashMap<Integer, byte[]> views = new ConcurrentHashMap<>();
    
    @ActionMethod(resultType = ResultType.Bytes, contentType = "text/html", methods = { RequestMethod.GET }, url = "article/{id}")
    public byte[] article(int id)
    {
        if (views.containsKey(id))
        {
            return views.get(id);
        }
        try
        {
            String path = servletContext.getRealPath("view");
            FileInputStream inputStream = new FileInputStream(new File(path + File.separator + id));
            byte[] src = new byte[inputStream.available()];
            inputStream.read(src);
            inputStream.close();
            views.put(id, src);
            return src;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        
    }
    
    public void delete(int id)
    {
        views.remove(id);
    }
}
