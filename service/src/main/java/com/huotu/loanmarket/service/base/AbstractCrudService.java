package com.huotu.loanmarket.service.base;

import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

/**
 * @author allan
 * @date 26/10/2017
 */
public abstract class AbstractCrudService<T, ID extends Serializable> implements CrudService<T, ID> {
    private JpaRepository<T, ID> repository;

    protected AbstractCrudService(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    public T findOne(ID id) {
        return repository.findOne(id);
    }

    @Override
    public T save(T t) {
        return repository.save(t);
    }

    @Override
    public void delete(ID id) {
        repository.delete(id);
    }
}
