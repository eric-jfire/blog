package link.jfire.blog.vo;

public class MdResult
{
    private int    success = 1;
    private String message;
    private String url;
    
    public int getSuccess()
    {
        return success;
    }
    
    public void setSuccess(int success)
    {
        this.success = success;
    }
    
    public String getMessage()
    {
        return message;
    }
    
    public void setMessage(String message)
    {
        this.message = message;
    }
    
    public String getUrl()
    {
        return url;
    }
    
    public void setUrl(String url)
    {
        this.url = url;
    }
    
}
