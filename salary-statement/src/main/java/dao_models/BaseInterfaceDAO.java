package dao_models;

import java.util.List;

interface BaseInterfaceDAO<T, R, PK> {
    T findById(PK id);
    List<T> findAll();
    void insert(R entity);
    void update(R entity);
    default void delete(PK id) {}
}