package com.dxf.demospringbootoss.controller;

import com.dxf.demospringbootoss.uitl.AliyunOSSUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @PackageName:com.dxf.demospringbootoss.controller
 * @Auther: 独善其身的狗
 * @Data: 2018/12/13 0013 上午 11:46
 */
@Controller
public class UpLoadController {


    private final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
    private static final String TO_PATH = "upLoad";
    private static final String RETURN_PATH = "success";

    @Autowired
    private AliyunOSSUtil aliyunOSSUtil;

    @RequestMapping("/toUpLoadFile")
    public String toUpLoadFile() {
        return TO_PATH;
    }

    /**
     * 文件上传
     */
    @RequestMapping(value = "/uploadFile")
    @ResponseBody
    public String uploadBlog(@RequestParam("file") MultipartFile file) {
        logger.info("文件上传");
        String filename = file.getOriginalFilename();
        System.out.println(filename);
        String uploadUrl = null;
        try {

            if (file != null) {
                if (!"".equals(filename.trim())) {
                    File newFile = new File(filename);
                    FileOutputStream os = new FileOutputStream(newFile);
                    os.write(file.getBytes());
                    os.close();
                    file.transferTo(newFile);
                    // 上传到OSS
                    uploadUrl = aliyunOSSUtil.upLoad(newFile);

                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return uploadUrl;
    }

    @RequestMapping("index")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> upload(HttpServletRequest request) {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        Map<String, Object> result = new HashMap<String, Object>();
        if (multipartResolver.isMultipart(request)) {
            // 转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            // 取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                // 取得上传文件
                MultipartFile f = multiRequest.getFile(iter.next());
                System.out.println(f.getContentType());
                logger.info("文件上传");
                String filename = f.getOriginalFilename();
                System.out.println(filename);
                String url = uploadBlog(f);
                result.put("success", 1);
                result.put("message", "上传成功");
                result.put("url", url);
            }
        }
            System.out.println(result.toString());
            return result;
        }

    @RequestMapping(value="/uploadimg")
    public @ResponseBody Map<String,Object> demo(@RequestParam(value = "editormd-image-file", required = false) MultipartFile file, HttpServletRequest request) {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        logger.info("文件上传");
        String filename = file.getOriginalFilename();
        System.out.println(filename);
        String uploadUrl = null;
        try {

            if (file != null) {
                if (!"".equals(filename.trim())) {
                    File newFile = new File(filename);
                    FileOutputStream os = new FileOutputStream(newFile);
                    os.write(file.getBytes());
                    os.close();
                    file.transferTo(newFile);
                    // 上传到OSS
                    uploadUrl = aliyunOSSUtil.upLoad(newFile);
                    resultMap.put("success", 1);
                    resultMap.put("message", "上传成功！");
                    resultMap.put("url",uploadUrl);
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultMap;
    }



//    @RequestMapping(value = "/upload", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> upload(HttpServletRequest request) {
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
//                request.getSession().getServletContext());
//        Map<String, Object> result = new HashMap<String, Object>();
//        if (multipartResolver.isMultipart(request)) {
//            // 转换成多部分request
//            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
//            // 取得request中的所有文件名
//            Iterator<String> iter = multiRequest.getFileNames();
//            while (iter.hasNext()) {
//                // 取得上传文件
//                MultipartFile f = multiRequest.getFile(iter.next());
//                System.out.println(f.getContentType());
//                logger.info("文件上传");
//                String filename = f.getOriginalFilename();
//                System.out.println(filename);
//                    try {
//                        if (f != null) {
//                            if (!"".equals(filename.trim())) {
//                                File newFile = new File(filename);
//                                FileOutputStream os = new FileOutputStream(newFile);
//                                os.write(f.getBytes());
//                                os.close();
//                                f.transferTo(newFile);
//                                // 上传到OSS
//                                String uploadUrl = aliyunOSSUtil.upLoad(newFile);
//                                result.put("success", 1);
//                                result.put("message", "上传成功");
//                                result.put("url", uploadUrl);
//                            }
//                        }
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//            }
//        } else {
//            //图片上传失败返回json数据给editor.Md
//            result.put("success", 0);
//            result.put("message", "上传失败");
//        }
//        System.out.println(result.toString());
//        return result;
//    }

    }
