package com.xhy.reload.news.model;

import org.litepal.crud.LitePalSupport;

public class Column extends LitePalSupport{

    private int id;
    private String columnName;
    private int columnId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public int getColumnId() {
        return columnId;
    }

    public void setColumnId(int columnId) {
        this.columnId = columnId;
    }
}
