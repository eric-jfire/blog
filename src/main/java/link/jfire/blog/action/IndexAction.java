package link.jfire.blog.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import link.jfire.baseutil.code.RandomString;
import link.jfire.baseutil.encrypt.Md5Util;
import link.jfire.blog.service.LoginService;
import link.jfire.blog.vo.BaseResult;
import link.jfire.blog.vo.WxCode;
import link.jfire.mvc.annotation.ActionClass;
import link.jfire.mvc.annotation.ActionMethod;
import link.jfire.mvc.annotation.RequestMethod;
import link.jfire.mvc.config.ResultType;
import link.jfire.mvc.core.ViewAndModel;
import link.jfire.wxsdk.util.TemplateMsgTool;
import link.jfire.wxsdk.vo.TemplateInfo;
import link.jfire.wxsdk.vo.WxCfg;

@Resource
@ActionClass("/")
public class IndexAction
{
    private ViewAndModel loginVm = new ViewAndModel("/login/login.html", true);
    private WxCfg        wxCfg   = new WxCfg("wx9b4deaff486c68af", "1458c254924e952a8684f7f2c89e9032");
    @Resource
    private LoginService loginService;
                         
    @ActionMethod(resultType = ResultType.Beetl)
    public ViewAndModel admin()
    {
        return new ViewAndModel("/admin/index.html");
    }
    
    @ActionMethod(resultType = ResultType.Beetl, url = "login", methods = { RequestMethod.GET })
    public ViewAndModel loginview(HttpSession session)
    {
        long t0 = System.currentTimeMillis();
        sendWxCode(session);
        System.out.println("发送完毕 ,耗时：" + (System.currentTimeMillis() - t0) + "毫秒");
        return loginVm;
    }
    
    private void sendWxCode(HttpSession session)
    {
        TemplateInfo info = new TemplateInfo();
        info.setTemplate_id("d3r6aLBe9C0zb3ayN7L_cXpECc5wjPDD0gIbZpwgxTE");
        info.setTouser("opaCJwSg9Mk-T3shE_6el8wfwOtU");
        String code = RandomString.getNumber(4);
        session.setAttribute("code", code);
        WxCode wxCode = new WxCode(code);
        info.setData(wxCode);
        TemplateMsgTool.send(info, wxCfg);
    }
    
    @ActionMethod(resultType = ResultType.Json, url = "login", methods = { RequestMethod.PUT })
    public BaseResult login(String username, String password, String code, HttpSession session)
    {
        String session_code = (String) session.getAttribute("code");
        if (session_code.equals(code))
        {
            if (loginService.login(username, Md5Util.md5Str(password)))
            {
                return new BaseResult();
            }
        }
        BaseResult result = new BaseResult();
        result.setSuccess(false);
        result.setMsg("用户名或者密码错误");
        return result;
    }
}
