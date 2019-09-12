package com.example.mytodoapp.db.helper;

import androidx.room.TypeConverter;

import com.example.mytodoapp.pojo.ToDoTag;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class TagTypeConvertor implements Serializable {

    @TypeConverter
    public String fromToDoTagList(List<ToDoTag> todoTagList) {
        if (todoTagList == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<ToDoTag>>() {
        }.getType();
        String json = gson.toJson(todoTagList, type);
        return json;
    }

    @TypeConverter
    public List<ToDoTag> toToDoTagList(String todoTagString) {
        if (todoTagString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<ToDoTag>>() {
        }.getType();
        List<ToDoTag> itemTagList = gson.fromJson(todoTagString, type);
        return itemTagList;
    }

}
