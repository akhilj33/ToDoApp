package com.example.mytodoapp.pojo;

import java.util.ArrayList;

public class ToDoResponseObject {
    private ArrayList<ToDoListItem> items;

    public ArrayList<ToDoListItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<ToDoListItem> items) {
        this.items = items;
    }
}
