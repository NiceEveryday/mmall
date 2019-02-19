package com.mmall.service.impl;

import com.mmall.common.ResponseContent;
import com.mmall.dao.CategoryMapper;
import com.mmall.pojo.Category;
import com.mmall.service.ICaregoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service("iCaregoryService")
public class ICategoryServiceImpl implements ICaregoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ResponseContent<String> addCategory(String categoryName,int parentId){
        if(StringUtils.isBlank(categoryName)){
            return ResponseContent.createByErrorWithMsg("添加品类名称为空");
        }
        int countRowParentId = categoryMapper.checkParentId(parentId);
        if(countRowParentId <= 0){
            return ResponseContent.createByErrorWithMsg("添加品类的父品类不存在");
        }

        int countName = categoryMapper.selectByNameAndParentId(categoryName,parentId);
        if(countName > 0){
            return ResponseContent.createByErrorWithMsg("添加品类已存在");
        }
        Category category = new Category();
        category.setParentId(parentId);
        category.setName(categoryName);
        category.setStatus(true);
        int countRow = categoryMapper.insert(category);
        if(countRow > 0){
            return ResponseContent.createBySuccessWithMsg("添加品类成功");
        }
        return ResponseContent.createByErrorWithMsg("内部错误请重试");
    }

    @Override
    public ResponseContent<String> updateCategory(String categoryName,int categoryId){
        if(StringUtils.isBlank(categoryName)){
            return ResponseContent.createByErrorWithMsg("添加品类名称为空");
        }
        Category categoryDB = categoryMapper.selectByPrimaryKey(categoryId);
        if(categoryDB == null){
            return ResponseContent.createByErrorWithMsg("修改的品类不存在");
        }
        categoryDB.setName(categoryName);

        int countRow = categoryMapper.updateByPrimaryKeySelective(categoryDB);
        if(countRow > 0){
            return ResponseContent.createBySuccessWithMsg("修改品类成功");
        }
        return ResponseContent.createByErrorWithMsg("内部错误请重试");
    }

    @Override
    public ResponseContent<List<Category>> getParallelChildrenCategory(int parentId){
        List<Category> categoryList = categoryMapper.getParallelChildrenCategory(parentId);
        if(CollectionUtils.isEmpty(categoryList)){
             return ResponseContent.createByErrorWithMsg("该品类下没有子品类");
        }
        return ResponseContent.createBySuccessWithData(categoryList);
    }
    public ResponseContent<List<Category>> getAllChildrenCategory(int parentId){
        Set<Category> categorySet = new HashSet<>();
        this.getAllCatagory(categorySet,parentId);
        List<Category> categoryList = new ArrayList<>();
        for(Category c : categorySet){
               categoryList.add(c);
        }
        return ResponseContent.createBySuccessWithData(categoryList);
    }

    private Set<Category> getAllCatagory(Set<Category> categories,int parentId){
        if(categories == null){
            categories = new HashSet<>();
        }
        Category category = categoryMapper.selectByPrimaryKey(parentId);
        if(category != null){
            categories.add(category);
        }
        List<Category> categoryList = categoryMapper.getParallelChildrenCategory(parentId);
        for(Category c : categoryList){
            getAllCatagory(categories,c.getId());
        }
        return  categories;
    }

}
