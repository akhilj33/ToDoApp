package com.example.mytodoapp.db.Database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mytodoapp.pojo.ToDoTag;
import com.example.mytodoapp.TagListener;

import java.util.List;

public class TagViewModel extends ViewModel {

    private FireStoreDbHelper repository;
    private MutableLiveData<List<ToDoTag>> allTags = new MutableLiveData<>();
    private MutableLiveData<List<ToDoTag>> searchResults = new MutableLiveData<>();

    public TagViewModel(){
            this.repository= new FireStoreDbHelper();

            FireStoreDbHelper.retrieveTags(new TagListener() {
                @Override
                public void onSuccessClicked(Object object) {
                    allTags.postValue( ((List<ToDoTag>) object) );
                }

                @Override
                public void onFailureClicked(Object object) {

                }
            } );
    }

    MutableLiveData<List<ToDoTag>> getSearchResults() {
        return searchResults;
    }

    public LiveData<List<ToDoTag>> retrieveTags() {
        return allTags;
    }

    public void addTag(ToDoTag tagItem) {
        repository.addTag( tagItem );
    }

//    public void findItem(ToDoTag listItem) {
//        repository.findItem( listItem );
//    }

    public void deleteItem(ToDoTag listItem) {
        repository.deleteTag( listItem );
    }

    public void updateTag(ToDoTag listItem) {
        repository.updateTags( listItem );
    }

}
