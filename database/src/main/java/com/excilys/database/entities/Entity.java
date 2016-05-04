package com.excilys.database.entities;

public interface Entity {

    public Long getId();

    public void setId(Long id);

    public String getName();

    public void setName(String name);

    @Override
    public String toString();

}
