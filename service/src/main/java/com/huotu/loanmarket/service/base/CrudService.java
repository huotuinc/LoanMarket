package com.huotu.loanmarket.service.base;

import java.io.Serializable;

/**
 * 一个有标准增删改查方法的服务接口
 * 通过{@link AbstractCrudService<T,ID>}实现了标准的增删改查操作
 * <p>
 * 各个模块的service接口可继承于改接口
 * <p>
 * for example
 * public interface MyService extend CrudService<T,ID></>
 * <p>
 * 同时对于各个模块service的实现，可继承{@link AbstractCrudService<T,ID>}，同时实现相应模块service接口
 * <p>
 * for example:
 * public class MyServiceImpl extend {@link AbstractCrudService<T,ID>} implement MyService
 *
 * @param <T>  entity
 * @param <ID> entity id
 * @author allan
 * @date 26/10/2017
 */
public interface CrudService<T, ID extends Serializable> {
    /**
     * 通过id得到一个实体
     *
     * @param id
     * @return
     */
    T findOne(ID id);

    /**
     * 保存一个实例
     *
     * @param t
     * @return
     */
    T save(T t);

    /**
     * 删除一个实例
     *
     * @param id
     */
    void delete(ID id);
}
