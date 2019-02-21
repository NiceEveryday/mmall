package com.mmall.service;

import com.mmall.common.ResponseContent;
import org.springframework.web.multipart.MultipartFile;

public interface IFileService {
    String uploadImg(MultipartFile multipartFile, String path);
}
