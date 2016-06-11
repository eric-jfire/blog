package cn.jfire.blog.entity;

import java.util.Date;
import com.jfireframework.sql.annotation.Id;
import com.jfireframework.sql.annotation.TableEntity;

@TableEntity(name = "article")
public class Article
{
    @Id
    private Integer id;
    private String  title;
    private String  text_content;
    private String  html_content;
    private Date    createtime;
    private Date    updatetime;
    // 是否置顶
    private boolean top = false;
    private String  category;
    // 是否是markdown文章
    private boolean markdown;
    
    public boolean isTop()
    {
        return top;
    }
    
    public void setTop(boolean top)
    {
        this.top = top;
    }
    
    public boolean isMarkdown()
    {
        return markdown;
    }
    
    public void setMarkdown(boolean markdown)
    {
        this.markdown = markdown;
    }
    
    public String getCategory()
    {
        return category;
    }
    
    public void setCategory(String category)
    {
        this.category = category;
    }
    
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
    
    public String getText_content()
    {
        return text_content;
    }
    
    public void setText_content(String text_content)
    {
        this.text_content = text_content;
    }
    
    public String getHtml_content()
    {
        return html_content;
    }
    
    public void setHtml_content(String html_content)
    {
        this.html_content = html_content;
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
