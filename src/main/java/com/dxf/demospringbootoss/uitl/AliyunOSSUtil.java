package com.dxf.demospringbootoss.uitl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import com.dxf.demospringbootoss.config.ConstantConfig;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @PackageName:com.dxf.demospringbootoss.uitl
 * @Auther: 独善其身的狗
 * @Data: 2018/12/13 0013 上午 11:42
 */
@Component
public class AliyunOSSUtil {

    @Autowired
    private ConstantConfig constantConfig ;

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AliyunOSSUtil.class);

    /** 上传文件*/
    public String upLoad(File file){
        logger.info("------OSS文件上传开始--------"+file.getName());
        String endpoint=constantConfig.getLXIMAGE_END_POINT();
        System.out.println("获取到的Point为:"+endpoint);
        String accessKeyId=constantConfig.getLXIMAGE_ACCESS_KEY_ID();
        String accessKeySecret=constantConfig.getLXIMAGE_ACCESS_KEY_SECRET();
        String bucketName=constantConfig.getLXIMAGE_BUCKET_NAME1();
        String fileHost=constantConfig.getLXIMAGE_FILE_HOST();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        String dateStr=format.format(new Date());
        String fileGetUrl = null;
        // 判断文件
        if(file==null){
            return null;
        }
        OSSClient client=new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try {
            // 判断容器是否存在,不存在就创建
            if (!client.doesBucketExist(bucketName)) {
                client.createBucket(bucketName);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                client.createBucket(createBucketRequest);
            }
            // 设置文件路径和名称
            String fileUrl = fileHost + "/" + (dateStr + "/" + UUID.randomUUID().toString().replace("-", "") + "-" + file.getName());
            System.out.println("A+fileUrl:"+fileUrl);
            // 上传文件
            PutObjectResult result = client.putObject(new PutObjectRequest(bucketName, fileUrl, file));
            // 设置权限(公开读)
            client.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            if (result != null) {
                logger.info("------OSS文件上传成功------" + fileUrl);
                logger.info("文件上传路径："+"https://"+constantConfig.getLXIMAGE_BUCKET_NAME1()+"."+constantConfig.getLXIMAGE_END_POINT()+"/"+fileUrl);
                fileGetUrl = "https://"+constantConfig.getLXIMAGE_BUCKET_NAME1()+"."+constantConfig.getLXIMAGE_END_POINT()+"/"+fileUrl;
                return fileGetUrl;
            }
        }catch (OSSException oe){
            logger.error(oe.getMessage());
        }catch (ClientException ce){
            logger.error(ce.getErrorMessage());
        }finally{
            if(client!=null){
                client.shutdown();
            }
        }

        return null;
    }

}
