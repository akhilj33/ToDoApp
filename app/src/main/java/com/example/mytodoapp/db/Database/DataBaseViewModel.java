package com.example.mytodoapp.db.Database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mytodoapp.MyActionListener;
import com.example.mytodoapp.db.helper.ItemRepositoryHelper;
import com.example.mytodoapp.pojo.ToDoListItem;
import com.example.mytodoapp.ui.fragment.SettingsFragment;
import com.example.mytodoapp.ui.helper.Utility;

import java.util.List;

public class DataBaseViewModel extends ViewModel {
    //    private ItemRepositoryHelper repository;
    private FireStoreDbHelper repository;
    private MutableLiveData<List<ToDoListItem>> allItems = new MutableLiveData<>();
    private MutableLiveData<List<ToDoListItem>> searchResults = new MutableLiveData<>();

    public DataBaseViewModel(/*@NonNull Application application*/) {
//        super( application );
        this.repository = new FireStoreDbHelper();

        if(Utility.SWITCH_DATE){

            FireStoreDbHelper.retrieveItemsOrderByDate( new MyActionListener() {
                @Override
                public void onSuccessClicked(Object object) {
                    allItems.postValue( ((List<ToDoListItem>) object) );
                }

                @Override
                public void onFailureClicked(Object object) {

                }
            } );

        }
        else if(Utility.SWITCH_PRIORITY){

            FireStoreDbHelper.retrieveItemsOrderByPriority( new MyActionListener() {
                @Override
                public void onSuccessClicked(Object object) {
                    allItems.postValue( ((List<ToDoListItem>) object) );
                }

                @Override
                public void onFailureClicked(Object object) {

                }
            } );
        }
        else if(Utility.SWITCH_TAG){
            FireStoreDbHelper.retrieveItemsOrderByTag( new MyActionListener() {
                @Override
                public void onSuccessClicked(Object object) {
                    allItems.postValue( ((List<ToDoListItem>) object) );
                }

                @Override
                public void onFailureClicked(Object object) {

                }
            } );
        }

        else{
            FireStoreDbHelper.retrieveItems( new MyActionListener() {
                @Override
                public void onSuccessClicked(Object object) {
                    allItems.postValue( ((List<ToDoListItem>) object) );
                }

                @Override
                public void onFailureClicked(Object object) {

                }
            } );
        }

//        searchResults = repository.getSearchResults();
    }


    MutableLiveData<List<ToDoListItem>> getSearchResults() {
        return searchResults;
    }

    public LiveData<List<ToDoListItem>> getAllItems() {
        return allItems;
    }

    public void insertItem(ToDoListItem listItem) {
        repository.insertItem( listItem );
    }

//    public void findItem(ToDoListItem listItem) {
//        repository.findItem( listItem );
//    }

    public void deleteItem(ToDoListItem listItem) {
        repository.deleteItem( listItem );
    }

//    public void updateItem(ToDoListItem listItem) {
//        repository.updateItem( listItem );
//    }

}