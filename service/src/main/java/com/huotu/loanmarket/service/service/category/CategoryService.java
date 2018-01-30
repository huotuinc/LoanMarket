package com.huotu.loanmarket.service.service.category;

import com.huotu.loanmarket.service.entity.category.Category;

import java.util.List;


public interface CategoryService {
    List<Category> findAll();

    /**
     * 查询
     *
     * @param categoryId
     * @return
     */
    Category findOne(int categoryId);

    /**
     * 保存
     *
     * @param category
     * @return
     */
    Category save(Category category);

    /**
     * 删除
     *
     * @param categoryId
     */
    void delete(int categoryId);
}
