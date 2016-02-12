package link.jfire.blog.service;

import java.io.IOException;
import java.io.InputStream;
import javax.annotation.Resource;
import link.jfire.codejson.JsonObject;
import link.jfire.codejson.JsonTool;
import link.jfire.core.bean.annotation.field.InitMethod;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

@Resource
public class UploadToQiniuService
{
    private String                 domain;
    private String                 accessKey;
    private String                 secretKey;
    private BucketManager          bucketManager;
    private ThreadLocal<QiniuUtil> qiniuUtils = new ThreadLocal<QiniuUtil>() {
                                                  protected QiniuUtil initialValue()
                                                  {
                                                      return new QiniuUtil(domain, accessKey, secretKey);
                                                  }
                                              };
                                              
    @InitMethod
    public void init()
    {
        bucketManager = new BucketManager(Auth.create(accessKey, secretKey));
    }
    
    public String getDomain()
    {
        return domain;
    }
    
    public BucketManager getBucketManager()
    {
        return bucketManager;
    }
    
    public String upload(InputStream inputStream)
    {
        try
        {
            byte[] src = new byte[inputStream.available()];
            inputStream.read(src);
            return qiniuUtils.get().upload(src);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}

class QiniuUtil
{
    private UploadManager uploadManager = new UploadManager();
    private BucketManager bucketManager;
    private Auth          auth;
    private long          dealLine;
    private String        domain;
    private String        token;
                          
    public QiniuUtil(String domain, String accessKey, String secretKey)
    {
        auth = Auth.create(accessKey, secretKey);
        this.domain = domain;
        bucketManager = new BucketManager(auth);
    }
    
    private String getToken()
    {
        if (System.currentTimeMillis() > dealLine)
        {
            token = auth.uploadToken("blog");
            dealLine = System.currentTimeMillis() + 3500 * 1000;
        }
        return token;
    }
    
    public String upload(byte[] src) throws QiniuException
    {
        Response response1 = uploadManager.put(src, null, getToken());
        return domain + ((JsonObject) JsonTool.fromString(response1.bodyString())).getWString("key");
    }
    
    public void delete(String key)
    {
        try
        {
            bucketManager.delete("blog", key);
        }
        catch (QiniuException e)
        {
            throw new RuntimeException(e);
        }
    }
}
