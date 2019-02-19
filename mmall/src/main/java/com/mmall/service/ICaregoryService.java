package com.mmall.service;

import com.mmall.common.ResponseContent;
import com.mmall.pojo.Category;

import java.util.List;

public interface ICaregoryService {
    ResponseContent<String> addCategory(String categoryName, int parentId);
    ResponseContent<String> updateCategory(String categoryName, int categoryid);
    ResponseContent<List<Category>> getParallelChildrenCategory(int parentId);
    ResponseContent<List<Category>> getAllChildrenCategory(int parentId);
}
