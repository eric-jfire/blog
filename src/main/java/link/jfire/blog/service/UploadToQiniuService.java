package link.jfire.blog.service;

import java.io.IOException;
import java.io.InputStream;
import javax.annotation.Resource;
import link.jfire.codejson.JsonObject;
import link.jfire.codejson.JsonTool;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

@Resource
public class UploadToQiniuService
{
    private String                  domain;
    private String                  accessKey;
    private String                  secretKey;
    private ThreadLocal<UploadUtil> uploadUtils = new ThreadLocal<UploadUtil>()
                                                {
                                                    protected UploadUtil initialValue()
                                                    {
                                                        return new UploadUtil(domain, accessKey, secretKey);
                                                    }
                                                };
    
    public String upload(InputStream inputStream)
    {
        try
        {
            byte[] src = new byte[inputStream.available()];
            inputStream.read(src);
            return uploadUtils.get().upload(src);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}

class UploadUtil
{
    private UploadManager uploadManager = new UploadManager();
    private Auth          auth;
    private long          dealLine;
    private String        domain;
    private String        token;
    
    public UploadUtil(String domain, String accessKey, String secretKey)
    {
        auth = Auth.create(accessKey, secretKey);
        this.domain = domain;
    }
    
    public String upload(byte[] src) throws QiniuException
    {
        if (System.currentTimeMillis() > dealLine)
        {
            token = auth.uploadToken("blog");
            dealLine = System.currentTimeMillis() + 3500 * 1000;
        }
        Response response1 = uploadManager.put(src, null, token);
        return domain + ((JsonObject) JsonTool.fromString(response1.bodyString())).getWString("key");
    }
}
