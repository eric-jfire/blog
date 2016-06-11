package cn.jfire.blog.action.admin;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import com.jfireframework.baseutil.code.RandomString;
import com.jfireframework.extra.wx.util.TemplateMsgTool;
import com.jfireframework.extra.wx.vo.TemplateInfo;
import com.jfireframework.extra.wx.vo.WxCfg;
import com.jfireframework.mvc.annotation.Controller;
import com.jfireframework.mvc.annotation.RequestMapping;
import com.jfireframework.mvc.config.ResultType;
import com.jfireframework.mvc.core.ModelAndView;
import com.jfireframework.mvc.util.RequestMethod;
import cn.jfire.blog.entity.Admin;
import cn.jfire.blog.service.LoginService;
import cn.jfire.blog.vo.BaseResult;
import cn.jfire.blog.vo.WxCode;

@Controller
@RequestMapping("/admin")
public class IndexAction
{
    private ModelAndView loginVm = new ModelAndView("/login/login.html", true);
    private WxCfg        wxCfg   = new WxCfg("wx9b4deaff486c68af", "1458c254924e952a8684f7f2c89e9032");
    @Resource
    private LoginService loginService;
    
    @RequestMapping(resultType = ResultType.Beetl, value = "/", method = RequestMethod.GET)
    public ModelAndView login(HttpSession session)
    {
        sendWxCode(session);
        return loginVm;
    }
    
    @RequestMapping(resultType = ResultType.Beetl, value = "/index", method = RequestMethod.GET)
    public ModelAndView index()
    {
        return new ModelAndView("/admin/index.bt");
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
    
    @RequestMapping(resultType = ResultType.Json, rest = true, value = "/admins", method = RequestMethod.GET)
    public BaseResult login(String username, String password, String code, HttpSession session)
    {
        String session_code = (String) session.getAttribute("code");
        BaseResult res = new BaseResult();
        if (session_code.equals(code))
        {
            Admin admin = loginService.login(username, password, session_code);
            if (admin != null)
            {
                res.setSuccess(true);
                session.setAttribute("admin", admin);
            }
            else
            {
                res.setSuccess(false);
            }
        }
        else
        {
            res.setSuccess(false);
        }
        return res;
    }
}
