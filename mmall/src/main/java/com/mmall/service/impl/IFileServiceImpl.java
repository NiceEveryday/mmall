package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service("iFileService")
public class IFileServiceImpl implements IFileService {

    private Logger logger = LoggerFactory.getLogger(IFileService.class);

    public String uploadImg(MultipartFile multipartFile,String path){
        if(multipartFile == null){
            return "";
        }
        String fileName = multipartFile.getOriginalFilename();
        String regexName = fileName.substring(fileName.lastIndexOf(".")+1);
        String fileServerName = UUID.randomUUID().toString() + "." + regexName;

        File fileDir = new File(path);
        if(!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        logger.info("开始上传tomcat,文件类型{}.文件名{},文件路径{}",regexName,fileServerName,path+fileServerName);
        File fileImg = new File(path,fileServerName);
        try {
            multipartFile.transferTo(fileImg);
            FTPUtil.uploadFile(Lists.newArrayList(fileImg));
            fileImg.delete();
        } catch (IOException e) {
            logger.error("上传失败",e);
            return "";
        }

        return fileImg.getName();
    }
}
