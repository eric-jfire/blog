package link.jfire.blog.entity;

import java.sql.Date;
import link.jfire.sql.annotation.Id;
import link.jfire.sql.annotation.TableEntity;

@TableEntity(name = "blog")
public class Blog
{
    @Id
    private Integer id;
    private String  title;
    private String  content;
    private Date    createtime;
    private Date    updatetime;
    
    public Integer getId()
    {
        return id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public String getContent()
    {
        return content;
    }
    
    public void setContent(String content)
    {
        this.content = content;
    }
    
    public Date getCreatetime()
    {
        return createtime;
    }
    
    public void setCreatetime(Date createtime)
    {
        this.createtime = createtime;
    }
    
    public Date getUpdatetime()
    {
        return updatetime;
    }
    
    public void setUpdatetime(Date updatetime)
    {
        this.updatetime = updatetime;
    }
    
}
