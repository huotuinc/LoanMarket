package com.huotu.loanmarket.service.repository.category;

import com.huotu.loanmarket.service.entity.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author hxh
 * @date 2017-10-24
 */
public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
