package com.example.mytodoapp.pojo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

@Entity(tableName = "tabletag")
public class ToDoTag {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "label")
    private String label;

    @ColumnInfo(name = "color")
    private String color;

    @ColumnInfo(name = "isUserCreated")
    private Boolean isUserCreated=true;

    public ToDoTag() {
    }

    public ToDoTag(String id, String label, String color, Boolean isUserCreated) {
        this.id = id;
        this.label = label;
        this.color = color;
        this.isUserCreated=isUserCreated;
    }

    public Boolean isUserCreated() {
        return isUserCreated;
    }

    public void setUserCreated(Boolean userCreated) {
        isUserCreated = userCreated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("label", label);
        result.put("color", color);
        result.put("isUserCreated", isUserCreated);
        return result;
    }
}
