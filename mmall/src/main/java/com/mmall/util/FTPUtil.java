package com.mmall.util;





import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

public class FTPUtil {

    private  static Logger logger = LoggerFactory.getLogger(FTPUtil.class);
    private  static  String ftpIP = PropertiesUtil.getProperty("ftp.server.ip","192.168.2.200");
    private  static  String ftpUser = PropertiesUtil.getProperty("ftp.user","ftpsuer");
    private  static  String ftpPassword = PropertiesUtil.getProperty("ftp.password","123456");
    private  static  int ftpPort = Integer.valueOf(PropertiesUtil.getProperty("ftp.port","21"));

    private String ip;
    private String user;

    private int port;
    private String password;

    public FTPUtil(String ip, String user, int port, String password) {
        this.ip = ip;
        this.user = user;
        this.port = port;
        this.password = password;
    }



    private static FTPClient ftpClient ;



    public static boolean uploadFile(List<File> files) throws IOException {
         FTPUtil ftpUtil = new FTPUtil(ftpIP,ftpUser,ftpPort,ftpPassword);
         logger.info("开始连接FTP服务器");
         boolean flag =   ftpUtil.upload("img",files);
         logger.info("上传图片{}",flag);
         return flag;
    }

    private boolean connect(){
        ftpClient = new FTPClient();
        boolean connectFlag = true;
        try {
            ftpClient.connect(this.ip,this.port);
            ftpClient.login(this.user,this.password);
        } catch (IOException e) {
            connectFlag = false;
            logger.error("连接FTP服务器失败",e);
        }
        return connectFlag;
    }

    private  boolean upload(String remotePath,List<File> files) throws IOException {
        FileInputStream  fileInputStream = null;
        boolean flag = true;
        if(connect()) {
            try {
                ftpClient.changeWorkingDirectory(remotePath);
                ftpClient.setControlEncoding("UTF-8");
                ftpClient.setBufferSize(1024);
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();
                for (File f : files) {
                    fileInputStream = new FileInputStream(f);
                    ftpClient.storeFile(f.getName(), fileInputStream);
                }
            } catch (IOException e) {
                 logger.error("文件服务器设置异常", e);
                 flag = false;
            } finally {
                fileInputStream.close();
                ftpClient.disconnect();
            }
        }else {
            flag = false;
        }
        return flag;
    }

}
