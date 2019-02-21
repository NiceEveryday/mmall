package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mmall.common.ResponseContent;
import com.mmall.common.ReturnCode;
import com.mmall.dao.CategoryMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Category;
import com.mmall.pojo.Product;
import com.mmall.service.IProductService;
import com.mmall.util.DateUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.ProductDetailVO;
import com.mmall.vo.ProductListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Service("iProductService")
public class IProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    public ResponseContent<String> saveOrUpdateProduct(Product product){
          if(product == null){
              return ResponseContent.createByErrorWithMsg(ReturnCode.PARAM_ERROR.getDesc());
          }
          if(product.getId() == null){
              int rowCount = productMapper.insert(product);
              if(rowCount > 0){
                  return ResponseContent.createBySuccessWithMsg("添加品类成功");
              }
              return ResponseContent.createByErrorWithMsg("添加品类失败，服务器内部错误请重试");
          }else{
              Product productDB = productMapper.selectByPrimaryKey(product.getId());
              if(productDB == null){
                  return ResponseContent.createByErrorWithMsg("更新的品类不存在");
              }
              int rowCount = productMapper.updateByPrimaryKeySelective(product);
              if(rowCount > 0){
                  return ResponseContent.createBySuccessWithMsg("更新品类成功");
              }
              return ResponseContent.createByErrorWithMsg("更新品类失败，服务器内部错误请重试");
          }
    }

    public ResponseContent<String> updateProductStatus(int productId,int status){
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product == null){
            return ResponseContent.createByErrorWithMsg("商品不存在");
        }
        Product productUpdate = new Product();
        productUpdate.setId(product.getId());
        productUpdate.setStatus(status);
        int rowCount = productMapper.updateByPrimaryKeySelective(productUpdate);
        if(rowCount > 0){
            return ResponseContent.createBySuccessWithMsg("更新商品状态成功");
        }
        return ResponseContent.createByErrorWithMsg("更新商品状态失败，服务器内部错误请重试");
    }

    public ResponseContent<ProductDetailVO> detail(Integer productId) {
        if (productId == null) {
              return  ResponseContent.createByErrorWithCM(ReturnCode.PARAM_ERROR.getCode(),"参数不存在");
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product == null){
            return ResponseContent.createByErrorWithMsg("商品不存在");
        }
        return ResponseContent.createBySuccessWithData(this.productToProductDetailVO(product));
    }

    public ResponseContent<PageInfo> list(Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Product> productList = productMapper.list();
        List<ProductListVO> productListVO = new ArrayList<>();
        for(Product product : productList){
            productListVO.add(productToProductListVO(product));
        }
        PageInfo pageInfo = new PageInfo(productListVO);
        return ResponseContent.createBySuccessWithData(pageInfo);
    }

    private ProductDetailVO productToProductDetailVO(Product product){
       if (product == null){
           return null;
       }
       ProductDetailVO productDetailVO = new ProductDetailVO();
       productDetailVO.setId(product.getId());
       productDetailVO.setCategoryId(product.getCategoryId());
       productDetailVO.setName(product.getName());
       productDetailVO.setSubtitle(product.getSubtitle());
       productDetailVO.setMainImage(product.getMainImage());
       productDetailVO.setSubImages(product.getSubImages());
       productDetailVO.setDetail(product.getDetail());
       productDetailVO.setPrice(product.getPrice());
       productDetailVO.setStock(product.getStock());
       productDetailVO.setStatus(product.getStatus());
       productDetailVO.setCreateTime(DateUtil.dateToStr(product.getCreateTime()));
       productDetailVO.setUpdateTime(DateUtil.dateToStr(product.getUpdateTime()));
       productDetailVO.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if(category == null){
            productDetailVO.setParentCategoryId(0);
        }else{
            productDetailVO.setParentCategoryId(category.getParentId());
        }
       return productDetailVO;
    }

    private ProductListVO productToProductListVO(Product product){
        if (product == null){
            return null;
        }
        ProductListVO productListVO = new ProductListVO();
        productListVO.setId(product.getId());
        productListVO.setName(product.getName());
        productListVO.setSubtitle(product.getSubtitle());
        productListVO.setMainImage(product.getMainImage());
        productListVO.setPrice(product.getPrice());
        productListVO.setStatus(product.getStatus());
        productListVO.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        return productListVO;
    }

    public  ResponseContent<PageInfo>  search(String productName,Integer productId,int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Product> productList = productMapper.selectByProductIdAndProductName(productId,productName);
        List<ProductListVO> productListVO = new ArrayList<>();
        for(Product product : productList){
            productListVO.add(productToProductListVO(product));
        }
        PageInfo pageInfo = new PageInfo(productListVO);
        return ResponseContent.createBySuccessWithData(pageInfo);
    }
}
