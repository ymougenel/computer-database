package com.excilys.database.persistence;

import java.sql.SQLException;

import com.excilys.database.entities.Entity;

public abstract class DAO<T extends Entity> { //localDate getTimeStamp -> to local date time
	public abstract T find(long id) throws SQLException;
	public abstract T find(String name) throws SQLException;
	public abstract T create(T obj) throws SQLException;
	public abstract T update(T obj) throws SQLException ;
	public abstract void delete(T obj) throws SQLException;
	
	
}
