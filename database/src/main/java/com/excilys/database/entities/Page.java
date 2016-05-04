package com.excilys.database.entities;

import java.util.ArrayList;
import java.util.List;

public class Page<T extends Entity> {

    public enum CompanyTable {
        ID,NAME,INTRODUCED,DISCONTINUED,COMPANY_ID;
        public static CompanyTable getField(String field) {
            switch (field) {
                case "id":
                    return ID;
                case "name":
                    return NAME;
                case "introduced":
                    return INTRODUCED;
                case "discontinued":
                    return DISCONTINUED;
                case "company":
                    return COMPANY_ID;
                default :
                    return ID;
            }
        }
    }

    public enum Order {
        ASC,DESC;
    }

    private long maxSize;
    private int index;
    private CompanyTable field;
    private Order order;
    private String search = "";

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    protected List<T> entities;

    public List<T> getEntities() {
        return entities;
    }

    public Page() {
        entities = new ArrayList<T>();
    }

    public Page(List<T> e) {
        entities = new ArrayList<T>(e);
    }

    public Page(List<T> e, int s) {
        entities = new ArrayList<T>(e);
        this.maxSize = s;
    }

    public long getSize() {
        return entities.size();
    }

    public long getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(long maxSize) {
        this.maxSize = maxSize;
    }

    public void addEntity(T entity) {
        entities.add(entity);
        maxSize = entities.size();
    }

    public void printf() {
        for (T t : entities) {
            System.out.println(t.toString());
        }
    }

    public void setEntities(List<T> entities) {
        this.entities = entities;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public static void main(String[] strings) {
        System.out.println();
    }
}
