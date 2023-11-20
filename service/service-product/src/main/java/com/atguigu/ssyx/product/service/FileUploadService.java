/**
 * @Classname FileUploadService
 * @Date 2023/11/5 20:58
 * @Created by Mingkai Feng
 * @Description TODO
 */
package com.atguigu.ssyx.product.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    String fileUpload(MultipartFile file) throws Exception;
}
