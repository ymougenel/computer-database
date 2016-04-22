package com.excilys.database.entities;

import java.util.ArrayList;
import java.util.List;

public class Page<T extends Entity> {
	protected long size;
	protected List<T> entities;

	public Page() {
		entities = new ArrayList<T>();
	}

	public Page(List<T> e) {
		entities = new ArrayList<T>(e);
		size = entities.size();
	}

	public Page(List<T> e, int s) {
		entities = new ArrayList<T>(e);
		this.size = s;
	}

	public void addEntity(T entity) {
		entities.add(entity);
		size = entities.size();
	}

	public void printf() {
		for (T t : entities)
			System.out.println(t.toString());
	}
}
