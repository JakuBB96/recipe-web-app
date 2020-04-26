package com.barancewicz.recipewebapp.services;

import java.util.List;

public interface CrudService<T> {
    List<?> listAll();
    T getById(Long id);
    T saveOrUpdate(T domainObject);
    void delete(T object);
    void deleteById(Long id);
}
