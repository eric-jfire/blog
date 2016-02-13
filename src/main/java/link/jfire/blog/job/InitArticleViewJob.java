package link.jfire.blog.job;

import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import link.jfire.blog.dao.BaseDao;
import link.jfire.blog.entity.Article;
import link.jfire.blog.service.ArticleService;
import link.jfire.core.ContextInitFinish;
import link.jfire.job.Job;
import link.jfire.job.JobManager;
import link.jfire.job.trigger.impl.SimpleIntervalTrigger;

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
