package com.excilys.database.entities;

import java.util.ArrayList;
import java.util.List;

public class Page<T extends Entity> {
	private int maxSize;
	private List<T> entities;
	
	public Page(){
		entities = new ArrayList<>();
	}
	public int getPageSize() {
		return entities.size();
	}

}
