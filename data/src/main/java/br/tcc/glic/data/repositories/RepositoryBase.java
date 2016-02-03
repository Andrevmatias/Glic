package br.tcc.glic.data.repositories;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by André on 02/02/2016.
 */
public class RepositoryBase<T> implements Repository<T> {

    private final Class<T> baseClass;

    RepositoryBase(Class<T> baseClass) {
        this.baseClass = baseClass;
    }

    @Override
    public T getById(long id) {
        return SugarRecord.findById(baseClass, id);
    }

    @Override
    public List<T> toList() {
        return SugarRecord.listAll(baseClass);
    }

    @Override
    public void delete(T entity) {
        SugarRecord.delete(entity);
    }

    @Override
    public void save(T entity) {
        SugarRecord.save(entity);
    }

    @Override
    public List<T> find(String query, String... params) {
        return SugarRecord.find(baseClass, query, params);
    }

    @Override
    public List<T> find(String query, String[] params, String groupBy, String orderBy, String limit) {
        return SugarRecord.find(baseClass, query, params, groupBy, orderBy, limit);
    }
}
