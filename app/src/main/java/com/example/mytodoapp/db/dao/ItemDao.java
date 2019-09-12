package com.example.mytodoapp.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mytodoapp.pojo.ToDoListItem;

import java.util.List;

@Dao
public interface ItemDao {

    @Insert
    void insertItem(ToDoListItem listItem);

    @Query("SELECT * FROM TableToDo WHERE ItemId = :id")
    List<ToDoListItem> findItem(String id);

    @Query("DELETE FROM TableToDo WHERE ItemId = :id")
    void deleteItem(String id);

    @Query("UPDATE TableToDo SET Title = :title, Description = :description, " +
            "DateTime = :dateTime, Modified_Date = :modifiedDate, Created_Date = :CreatedDate WHERE ItemId =:id")
    void  updateItem(String title, String description,
                                   String dateTime, String modifiedDate, String CreatedDate, String id);

    @Update
    void updateItem(ToDoListItem listItem);

    @Query("SELECT * FROM TableToDo")
    LiveData<List<ToDoListItem>> getAllItems();
}
