package com.example.mytodoapp.networking;

import com.example.mytodoapp.pojo.ToDoListItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetAllItemsAPI {

    @GET("getAllTodoItems")
    Call<List<ToDoListItem>> getToDoListItem();

}
