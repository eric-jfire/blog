package link.jfire.blog.action;

import javax.annotation.Resource;
import link.jfire.mvc.annotation.ActionClass;
import link.jfire.mvc.annotation.ActionMethod;
import link.jfire.mvc.config.ResultType;
import link.jfire.mvc.core.ViewAndModel;

@Resource
@ActionClass("/")
public class IndexAction
{
    
    @ActionMethod(resultType = ResultType.Beetl)
    public ViewAndModel admin()
    {
        return new ViewAndModel("/admin/index.html");
    }
    
}
