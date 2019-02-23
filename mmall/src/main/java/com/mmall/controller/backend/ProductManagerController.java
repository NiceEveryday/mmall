package com.mmall.controller.backend;

import com.github.pagehelper.PageInfo;
import com.mmall.common.Const;
import com.mmall.common.ResponseContent;
import com.mmall.common.ReturnCode;
import com.mmall.common.Role;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IFileService;
import com.mmall.service.IProductService;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.ProductDetailVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/backend/product")
public class ProductManagerController {

    @Autowired
    private IProductService iProductService;

    @Autowired
    private IFileService iFileService;

    @RequestMapping(value = "save.do",method = RequestMethod.POST)
    @ResponseBody
    public ResponseContent<String> save(HttpSession session, Product product){
        User user = (User) session.getAttribute(Const.CURRENT_USER.getName());
        if(user == null){
            return  ResponseContent.createByErrorWithCM(ReturnCode.LOGIN.getCode(),"用户未登陆");
        }
        if(user.getRole() != Role.ADMIN.getCode()){
            return ResponseContent.createByErrorWithMsg("不是管理员不能新增商品");
        }
        return  iProductService.saveOrUpdateProduct(product);
    }

    @RequestMapping(value = "update_product_status.do",method = RequestMethod.POST)
    @ResponseBody
    public ResponseContent<String> update_product_status(HttpSession session, int productId,int status){
        User user = (User) session.getAttribute(Const.CURRENT_USER.getName());
        if(user == null){
            return  ResponseContent.createByErrorWithCM(ReturnCode.LOGIN.getCode(),"用户未登陆");
        }
        if(user.getRole() != Role.ADMIN.getCode()){
            return ResponseContent.createByErrorWithMsg("不是管理员不能更新商品状态");
        }
        return  iProductService.updateProductStatus(productId,status);
    }

    @RequestMapping(value = "detail.do",method = RequestMethod.GET)
    @ResponseBody
    public ResponseContent<ProductDetailVO> detail(HttpSession session, Integer productId){
        User user = (User) session.getAttribute(Const.CURRENT_USER.getName());
        if(user == null){
            return  ResponseContent.createByErrorWithCM(ReturnCode.LOGIN.getCode(),"用户未登陆");
        }
        if(user.getRole() != Role.ADMIN.getCode()){
            return ResponseContent.createByErrorWithMsg("不是管理员不能更新商品状态");
        }
        return  iProductService.detail(productId,false);
    }

    @RequestMapping(value = "list.do",method = RequestMethod.GET)
    @ResponseBody
    public ResponseContent<PageInfo> list(HttpSession session, @RequestParam(value = "pageNum" ,defaultValue = "1") Integer  pageNum,
                                          @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
        User user = (User) session.getAttribute(Const.CURRENT_USER.getName());
        if(user == null){
            return  ResponseContent.createByErrorWithCM(ReturnCode.LOGIN.getCode(),"用户未登陆");
        }
        if(user.getRole() != Role.ADMIN.getCode()){
            return ResponseContent.createByErrorWithMsg("不是管理员不能查看后台商品列表");
        }
        return  iProductService.list(pageNum,pageSize);
    }

    @RequestMapping(value = "search.do",method = RequestMethod.GET)
    @ResponseBody
    public ResponseContent<PageInfo> search(HttpSession session,String productName,Integer productId ,@RequestParam(value = "pageNum" ,defaultValue = "1") Integer  pageNum,
                                          @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
        User user = (User) session.getAttribute(Const.CURRENT_USER.getName());
        if(user == null){
            return  ResponseContent.createByErrorWithCM(ReturnCode.LOGIN.getCode(),"用户未登陆");
        }
        if(user.getRole() != Role.ADMIN.getCode()){
            return ResponseContent.createByErrorWithMsg("不是管理员不能查看后台商品列表");
        }
        return  iProductService.search(productName,productId,pageNum,pageSize);
    }

    @RequestMapping(value = "img_load.do",method = RequestMethod.POST)
    @ResponseBody
    public ResponseContent<Map> img_load(HttpSession session, @RequestParam(value = "multipartFile") MultipartFile multipartFile, HttpServletRequest httpRequest){
        User user = (User) session.getAttribute(Const.CURRENT_USER.getName());
        if(user == null){
            return  ResponseContent.createByErrorWithCM(ReturnCode.LOGIN.getCode(),"用户未登陆");
        }
        if(user.getRole() != Role.ADMIN.getCode()){
            return ResponseContent.createByErrorWithMsg("不是管理员不能上传图片");
        }
        String path = httpRequest.getSession().getServletContext().getRealPath("upload");
        String imgName =  iFileService.uploadImg(multipartFile,path);
        if(StringUtils.isBlank(imgName)){
            return ResponseContent.createByErrorWithMsg("图片上传失败,请重试");
        }
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix","") + imgName;

        Map map = new HashMap();
        map.put("uri",imgName);
        map.put("url",url);
        return  ResponseContent.createBySuccessWithMD("上传成功",map);
    }

    @RequestMapping(value = "editor_img_load.do",method = RequestMethod.POST)
    @ResponseBody
    public Map editor_img_load(HttpSession session, @RequestParam(value = "multipartFile") MultipartFile multipartFile, HttpServletRequest httpRequest){
        Map map = new HashMap();
        User user = (User) session.getAttribute(Const.CURRENT_USER.getName());
        if(user == null){
            map.put("success",false);
            map.put("msg","请登录管理员");
            return  map;
        }
        if(user.getRole() != Role.ADMIN.getCode()){
            map.put("success",false);
            map.put("msg","无权限");
            return  map;
        }
        String path = httpRequest.getSession().getServletContext().getRealPath("upload");
        String imgName =  iFileService.uploadImg(multipartFile,path);
        if(StringUtils.isBlank(imgName)){
            map.put("success",false);
            map.put("msg","图片上传失败");
            return  map;
        }
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix","") + imgName;
        map.put("success",true);
        map.put("msg","图片上传成功");
        map.put("file_path",url);
        return  map;
    }

}
