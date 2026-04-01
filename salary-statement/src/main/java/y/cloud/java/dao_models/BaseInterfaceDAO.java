package y.cloud.java.dao_models;

import java.util.List;

interface BaseInterfaceDAO<T, R, PK> {
    T findById(PK id);
    List<T> findAll();
    PK insert(R entity);
    void update(R entity);
    default void delete(PK id) {}
}