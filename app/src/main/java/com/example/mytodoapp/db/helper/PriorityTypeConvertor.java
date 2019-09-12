package com.example.mytodoapp.db.helper;

import androidx.room.TypeConverter;

import com.example.mytodoapp.pojo.ToDoPriority;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class PriorityTypeConvertor implements Serializable {

    @TypeConverter
    public String fromToDoPriorityList(List<ToDoPriority> todoPriorityList) {
        if (todoPriorityList == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<ToDoPriority>>() {
        }.getType();
        String json = gson.toJson(todoPriorityList, type);
        return json;
    }

    @TypeConverter
    public List<ToDoPriority> toToDoPriorityList(String todoPriorityString) {
        if (todoPriorityString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<ToDoPriority>>() {
        }.getType();
        List<ToDoPriority> itemPriorityList = gson.fromJson(todoPriorityString, type);
        return itemPriorityList;
    }
    
}
