//package com.dxf.demospringbootoss.uitl;
//
//import org.junit.Test;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.List;
//import com.aliyun.oss.OSSClient;
//
//
///**
// * @PackageName:com.dxf.demospringbootoss.uitl
// * @Auther: 独善其身的狗
// * @Data: 2018/12/13 0013 下午 1:50
// */
//public class OSSUtilTest {
//    private OSSClient ossClient = OSSUtil.getOSSClient();
//    private String bucketName = "你的bucketName";
//
//    @Test
//    public void testUploadByNetworkStream(){
//        //测试通过网络流上传文件
//        try {
//            URL url = new URL("https://www.aliyun.com/");
//            OSSUtil.uploadByNetworkStream(ossClient, url, bucketName, "test/aliyun.html");
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void testUploadByInputStream(){
//        //测试通过输入流上传文件
//        try {
//            InputStream inputStream = new FileInputStream(new File("D:/applicationContext.xml"));
//            OSSUtil.uploadByInputStream(ossClient, inputStream, bucketName, "test/a.xml");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void testUploadByFile(){
//        //测试通过file上传文件
//        File file = new File("D:/applicationContext.xml");
//        OSSUtil.uploadByFile(ossClient, file, bucketName, "test/applicationContext.xml");
//    }
//
//    @Test
//    public void testDeleteFile(){
//        //测试根据key删除oss服务器上的文件
//        OSSUtil.deleteFile(ossClient, bucketName, "test/a.xml");
//    }
//
//    @Test
//    public void testGetInputStreamByOSS(){
//        //测试根据key获取服务器上的文件的输入流
//        try {
//            InputStream content = OSSUtil.getInputStreamByOSS(ossClient, bucketName, "test/applicationContext.xml");
//            if (content != null) {
//                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
//                while (true) {
//                    String line = reader.readLine();
//                    if (line == null) break;
//                    System.out.println("\n" + line);
//                }
//                // 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
//                content.close();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void testQueryAllObject(){
//        //测试查询某个bucket里面的所有文件
//        List<String> results = OSSUtil.queryAllObject(OSSUtil.getOSSClient(), bucketName);
//        System.out.println(results);
//    }
//}