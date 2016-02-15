package link.jfire.blog.vo;

import java.util.HashMap;
import java.util.Map;

public class WxCode extends HashMap<String, Map<String, String>>
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public WxCode(String code)
    {
        HashMap<String, String> map = new HashMap<>();
        map.put("value", code);
        map.put("color", "#173177");
        this.put("code", map);
    }
    
}
