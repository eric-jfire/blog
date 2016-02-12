package link.jfire.blog.action;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javax.annotation.Resource;
import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import link.jfire.codejson.JsonTool;
import link.jfire.core.ContextInitFinish;
import link.jfire.mvc.annotation.ActionClass;
import link.jfire.mvc.annotation.ActionMethod;
import link.jfire.mvc.config.ResultType;

@Resource
@ActionClass("monitor")
public class MonitorAction implements ContextInitFinish
{
    private Logger logger = LogManager.getLogger();
    
    @ActionMethod(resultType = ResultType.None, url = "/")
    public void monitor(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            response.setContentType("text/html;charset=UTF-8");
            response.setHeader("Cache-Control", "private");
            response.setHeader("Pragma", "no-cache");
            response.setCharacterEncoding("UTF-8");
            final AsyncContext ac = request.startAsync();
            ac.setTimeout(1000 * 60 * 60);
            new Thread() {
                public void run()
                {
                    while (true)
                    {
                        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                        HashMap<String, String> result = new HashMap<>();
                        try
                        {
                            Thread.sleep(900);
                            Sigar sigar = new Sigar();
                            double cpuuse;
                            double memuse;
                            cpuuse = sigar.getCpuPerc().getCombined();
                            Mem mem = sigar.getMem();
                            memuse = mem.getUsedPercent();
                            result.clear();
                            result.put("cpuuse", String.valueOf((int) (cpuuse * 100)));
                            result.put("time", format.format(new Date()));
                            result.put("memuse", String.valueOf((int) memuse));
                            String content = "<script>window.parent.update(" + JsonTool.write(result) + ")</script>";
                            ac.getResponse().getOutputStream().write(content.getBytes(Charset.forName("utf8")));
                            ac.getResponse().getOutputStream().flush();
                        }
                        catch (IOException | InterruptedException | SigarException e)
                        {
                            logger.debug("服务器主动推送停止");
                            break;
                        }
                    }
                }
            }.start();
        }
        catch (IllegalStateException e)
        {
            logger.error("", e);
        }
    }
    
    public int getOrder()
    {
        return 100;
    }
    
    public void afterContextInit()
    {
        String name = System.getProperty("os.name");
        if (name.startsWith("Win"))
        {
            try
            {
                File file = new File(this.getClass().getClassLoader().getResource("sigar-amd64-winnt.dll").toURI());
                System.load(file.getCanonicalPath());
            }
            catch (Exception e)
            {
                logger.error("监控启动失败", e);
            }
        }
        else
        {
            try
            {
                File file = new File(this.getClass().getClassLoader().getResource("libsigar-amd64-linux.so").toURI());
                System.load(file.getCanonicalPath());
            }
            catch (Exception e)
            {
                logger.error("监控启动失败", e);
            }
        }
    }
}
