package br.tcc.glic.data.repositories;

import java.util.List;

/**
 * Created by André on 02/02/2016.
 */
public interface Repository<T> {
    T getById(long id);
    List<T> toList();
    void delete(T entity);
    void save(T entity);
    List<T> find(String query, String... params);
    List<T> find(String query, String[] params, String groupBy, String orderBy, String limit);
}
