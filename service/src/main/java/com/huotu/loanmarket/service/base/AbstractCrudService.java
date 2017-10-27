package com.huotu.loanmarket.service.base;

import java.io.Serializable;

/**
 * @author allan
 * @date 26/10/2017
 */
public abstract class AbstractCrudService<T, ID extends Serializable> implements CrudService<T, ID> {
    private JpaCrudRepository<T, ID> repository;

    protected AbstractCrudService(JpaCrudRepository<T, ID> repository) {
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
