package com.excilys.database.persistence;

import java.util.List;

import com.excilys.database.entities.Entity;

public interface DAO<T extends Entity> {

    public T find(long id);

    public T find(String name);

    public List<T> listAll();

    public T create(T obj);

    public T update(T obj);

    public void delete(T obj);

    public long count();

}
