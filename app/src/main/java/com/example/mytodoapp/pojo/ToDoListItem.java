package com.example.mytodoapp.pojo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.database.Exclude;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Entity(tableName = "TableToDo")
public class ToDoListItem {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "ItemId")
    private int itemId;

    @ColumnInfo(name = "Title")
    private String title;

    @ColumnInfo(name = "Description")
    private String description;

    @ColumnInfo(name = "DateTime")
    private String dateTime;

    @ColumnInfo(name = "Created_Date")
    @SerializedName("dateCreated")
    private String CreatedDate;

    @ColumnInfo(name = "Modified_Date")
    @SerializedName("dateModified")
    private String modifiedDate;

    @ColumnInfo(name = "Tags")
    private List<ToDoTag> tag;

    @ColumnInfo(name = "Priority")
        private List<ToDoPriority> priority;

    @ColumnInfo(name = "Is_Done")
    private Boolean isDone=false;

    public ToDoListItem() {
    }

    public ToDoListItem(int itemId, String title, String description, String dateTime, String modifiedDate, String createdDate, List<ToDoTag> tag, List<ToDoPriority> priority) {
        this.itemId = itemId;
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
        this.modifiedDate = modifiedDate;
        CreatedDate = createdDate;
        this.tag = tag;
        this.priority = priority;
    }


    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public List<ToDoTag> getTag() {
        return tag;
    }

    public void setTag(List<ToDoTag> tag) {
        this.tag = tag;
    }

    public List<ToDoPriority> getPriority() {
        return priority;
    }

    public void setPriority(List<ToDoPriority> priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "ToDoListItem{" +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }



    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("itemid", itemId);
        result.put("description", description);
        result.put("title", title);
        result.put("datetime", dateTime);
        result.put("createddate", CreatedDate);
        result.put("modifieddate", modifiedDate);
        result.put("tag",tag);
        result.put("priority", priority);
        result.put("isDone", isDone);
        return result;
    }


}