package com.huotu.loanmarket.service.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 *
 * @author allan
 * @date 26/10/2017
 */
@NoRepositoryBean
public interface JpaCrudRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
}
