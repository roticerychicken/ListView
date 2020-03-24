package com.example.listview;

import java.io.Serializable;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ListItem implements Serializable {
    private int id;
    private int list_id;
    private String name;
    private int nameValue;

    public ListItem() {
        this.id = id;
        this.list_id = list_id;
        this.name = name;
        this.nameValue = nameValue;
    }

    public int getId() {
        return id;
    }
    public int getListId() {
        return list_id;
    }
    public int getNameValue() {
        return nameValue;
    }

    public String getName() {
        return name;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setListId(int lid) {
        this.list_id = lid;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setNameValue(int num) {
        this.nameValue = num;
    }
}
