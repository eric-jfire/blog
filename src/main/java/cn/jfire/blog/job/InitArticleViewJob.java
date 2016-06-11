package cn.jfire.blog.job;

import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import com.jfireframework.context.ContextInitFinish;
import com.jfireframework.job.Job;
import com.jfireframework.job.JobManager;
import com.jfireframework.job.trigger.impl.SimpleIntervalTrigger;
import cn.jfire.blog.dao.BaseDao;
import cn.jfire.blog.entity.Article;
import cn.jfire.blog.service.ArticleService;

@Resource
public class InitArticleViewJob implements Job, ContextInitFinish
{
    @Resource
    private BaseDao        baseDao;
    @Resource
    private ArticleService service;
    @Resource
    private JobManager     jobmanage;
                           
    @Override
    public void doJob()
    {
        List<Article> list = service.listWithContent();
        for (Article each : list)
        {
            try
            {
                service.render(each);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public boolean nextRound()
    {
        return false;
    }
    
    @Override
    public int getOrder()
    {
        return 0;
    }
    
    @Override
    public void afterContextInit()
    {
        SimpleIntervalTrigger trigger = new SimpleIntervalTrigger(this, 1000);
        jobmanage.addTrigger(trigger);
    }
    
}
