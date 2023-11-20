/**
 * @Classname FileUploadServiceImpl
 * @Date 2023/11/5 20:59
 * @Created by Mingkai Feng
 * @Description TODO
 */
package com.atguigu.ssyx.product.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.atguigu.ssyx.product.service.FileUploadService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.DateTimeException;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${aliyun.endpoint}")
    private String endPoint;
    @Value("${aliyun.keyid}")
    private String accessKey;
    @Value("${aliyun.keysecret}")
    private String secreKey;
    @Value("${aliyun.bucketname}")
    private String bucketName;

    @Override
    public String fileUpload(MultipartFile file) {
        try {
            // 创建OSSClient实例
            OSS ossClient = new OSSClientBuilder().build(endPoint, accessKey, secreKey);
            // 上传文件输入流
            InputStream inputStream = file.getInputStream();
            // 创建PutObjectRequest对象。
            String objectName = file.getOriginalFilename();
            // 生成随机唯一值，使用 UUID， 添加到文件名称里面
            String uuid = UUID.randomUUID().toString().replace("-", "");
            objectName = uuid + objectName;
            // 创建当前日期，创建文件夹，上传到创建文件夹里面
            // 将其变成 2023/11/06/01.jpg 的形式
            String timeUrl = new DateTime().toString("yyyy/MM/dd");
            objectName = timeUrl + "/" + objectName;
            // 调用方法实现上传
            // PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream);
            ossClient.putObject(bucketName, objectName, inputStream);
            // 关闭 OSSClient
            ossClient.shutdown();
            // 上传后文件路径示例
            // https://ssyx-fguigu.oss-cn-beijing.aliyuncs.com/01.jpg
            String url = "https://" + bucketName + "." + endPoint + "/" + objectName;
            // 返回存储图片路径
            return url;
        } catch (Exception ce) {
            ce.printStackTrace();
            return null;
        }
    }
}
