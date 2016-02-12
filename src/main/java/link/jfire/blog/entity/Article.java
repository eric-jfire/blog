package link.jfire.blog.entity;

import java.util.Date;
import link.jfire.sql.annotation.Id;
import link.jfire.sql.annotation.TableEntity;

@TableEntity(name = "article")
public class Article
{
    @Id
    private Integer         id;
    private String          title;
    private String          text_content;
    private String          html_content;
    private Date            createtime;
    private Date            updatetime;
    private String          imgs;
    private int             type;
    public static final int NORMAL   = 0;
    public static final int TOP      = 1;
    private String          category;
    private boolean         markdown;
    public static final int HTML     = 0;
    public static final int MARKDOWN = 1;
                                     
    public String getImgs()
    {
        return imgs;
    }
    
    public void setImgs(String imgs)
    {
        this.imgs = imgs;
    }
    
    public int getType()
    {
        return type;
    }
    
    public void setType(int type)
    {
        this.type = type;
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
