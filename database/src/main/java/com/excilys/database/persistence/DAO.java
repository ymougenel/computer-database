package com.excilys.database.persistence;

import com.excilys.database.entities.Entity;

import java.util.List;

public abstract class DAO<T extends Entity> {
  public abstract T find(long id);

  public abstract T find(String name);

  public abstract List<T> listAll();

  public abstract T create(T obj);

  public abstract T update(T obj);

  public abstract void delete(T obj);

  public abstract long count();

}