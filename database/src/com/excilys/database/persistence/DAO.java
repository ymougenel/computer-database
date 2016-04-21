package com.excilys.database.persistence;

import java.sql.SQLException;
import java.util.List;

import com.excilys.database.entities.Entity;

public abstract class DAO<T extends Entity> {
	public abstract T find(long id) throws SQLException;
	public abstract T find(String name) throws SQLException;
	public abstract List<T> listAll() throws SQLException; 
	public abstract int create(T obj) throws SQLException;
	public abstract int update(T obj) throws SQLException ;
	public abstract void delete(T obj) throws SQLException;
	
	
}
