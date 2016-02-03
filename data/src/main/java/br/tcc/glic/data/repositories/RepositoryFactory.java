package br.tcc.glic.data.repositories;

import br.tcc.glic.data.entities.Entity;

/**
 * Created by André on 02/02/2016.
 */
public final class RepositoryFactory {
    public static <T extends Entity>Repository<T> get(Class<T> entityClass){
        return new RepositoryBase<>(entityClass);
    }
}
