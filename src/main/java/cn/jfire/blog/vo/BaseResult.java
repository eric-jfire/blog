package cn.jfire.blog.vo;

import java.util.List;

public class BaseResult
{
    protected boolean success = true;
    protected String  msg;
    protected Object  data;
    protected int     total;
    protected List<?> rows;
    
    public int getTotal()
    {
        return total;
    }
    
    public void setTotal(int total)
    {
        this.total = total;
    }
    
    public List<?> getRows()
    {
        return rows;
    }
    
    public void setRows(List<?> rows)
    {
        this.rows = rows;
    }
    
    public Object getData()
    {
        return data;
    }
    
    public void setData(Object data)
    {
        this.data = data;
    }
    
    public boolean isSuccess()
    {
        return success;
    }
    
    public void setSuccess(boolean success)
    {
        this.success = success;
    }
    
    public String getMsg()
    {
        return msg;
    }
    
    public void setMsg(String msg)
    {
        this.msg = msg;
    }
    
}
