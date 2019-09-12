package com.example.mytodoapp.db.helper;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mytodoapp.db.Database.ItemRoomDatabase;
import com.example.mytodoapp.db.dao.ItemDao;
import com.example.mytodoapp.pojo.ToDoListItem;

import java.util.List;

public class ItemRepositoryHelper {

    private MutableLiveData<List<ToDoListItem>> searchResults =
            new MutableLiveData<>();

    private ItemDao itemDao;
    private LiveData<List<ToDoListItem>> allItems;

    public ItemRepositoryHelper(Application application) {
        ItemRoomDatabase db;
        db = ItemRoomDatabase.getDatabase( application );
        itemDao = db.itemDao();
        allItems = itemDao.getAllItems();
    }

    public void insertItem(ToDoListItem listItem) {
        InsertAsyncTask task = new InsertAsyncTask( itemDao );
        task.execute( listItem );
    }

    public void deleteItem(ToDoListItem listItem) {
        DeleteAsyncTask task = new DeleteAsyncTask( itemDao );
        task.execute( listItem );
    }

    public void findItem(ToDoListItem listItem) {
        QueryAsyncTask task = new QueryAsyncTask( itemDao );
        task.helper = this;
        task.execute( listItem );
    }

    public void updateItem(ToDoListItem listItem){
        DeleteAsyncTask task = new DeleteAsyncTask( itemDao );
        task.execute( listItem );
    }

    private void asyncFinished(List<ToDoListItem> results) {
        searchResults.postValue( results );
    }

    public LiveData<List<ToDoListItem>> getAllItems() {
        return allItems;
    }

    public MutableLiveData<List<ToDoListItem>> getSearchResults() {
        return searchResults;
    }

    private static class QueryAsyncTask extends AsyncTask<ToDoListItem, Void, List<ToDoListItem>> {
        private ItemDao asyncTaskDao;
        private ItemRepositoryHelper helper = null;

        public QueryAsyncTask(ItemDao asyncTaskDao) {
            this.asyncTaskDao = asyncTaskDao;
        }

        @Override
        protected void onPostExecute(List<ToDoListItem> toDoListItems) {
            super.onPostExecute( toDoListItems );
            helper.asyncFinished( toDoListItems );
        }

        @Override
        protected List<ToDoListItem> doInBackground(ToDoListItem... toDoListItems) {
            return asyncTaskDao.findItem( String.valueOf(toDoListItems[0].getItemId()) );
        }
    }

    private static class InsertAsyncTask extends AsyncTask<ToDoListItem, Void, Void> {

        private ItemDao asyncTaskDao;

        InsertAsyncTask(ItemDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(ToDoListItem... toDoListItems) {
            asyncTaskDao.insertItem( toDoListItems[0] );
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<ToDoListItem, Void, Void> {

        private ItemDao asyncTaskDao;

        DeleteAsyncTask(ItemDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(ToDoListItem... toDoListItems) {
            asyncTaskDao.deleteItem( String.valueOf(toDoListItems[0].getItemId()) );
            return null;
        }
    }


    private static class UpdateAsyncTask extends AsyncTask<ToDoListItem, Void, Void>{
        private ItemDao asyncTaskDao;

        UpdateAsyncTask(ItemDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(ToDoListItem... toDoListItems) {
            asyncTaskDao.updateItem(toDoListItems[0]);
            return null;
        }
    }


}
