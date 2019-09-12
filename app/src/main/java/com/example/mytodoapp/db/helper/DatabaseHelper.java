package com.example.mytodoapp.db.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mytodoapp.pojo.ToDoListItem;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "TodoManager";
    private static final String TABLE_TODO = "TodoList";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_DATETIME = "datentime";
    private static final String KEY_MODIFIEDDATE = "modifieddate";
    private static final String KEY_CREATEDDATE = "createddate";
    private static final String KEY_TAG = "tag";
    private static final String KEY_PRIORITY = "priority";

    public DatabaseHelper(Context context) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TODO_TABLE = "CREATE TABLE " + TABLE_TODO + "("+KEY_ID+ " INTEGER PRIMARY KEY,"
                                    +KEY_TITLE+ " TEXT," +KEY_DESCRIPTION+ " TEXT," +KEY_DATETIME+ " TEXT,"
                                    +KEY_MODIFIEDDATE+ " TEXT," +KEY_CREATEDDATE+ " TEXT," +KEY_TAG+ " TEXT,"
                                    +KEY_PRIORITY+ " TEXT" + ")";
        db.execSQL( CREATE_TODO_TABLE );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
        onCreate(db);

    }

    public void addToDoItem(ToDoListItem mylist) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put( KEY_ID, mylist.getItemId() );
        values.put( KEY_TITLE, mylist.getTitle() );
        values.put( KEY_DESCRIPTION, mylist.getDescription() );
        values.put( KEY_DATETIME, mylist.getDateTime() );
        values.put( KEY_MODIFIEDDATE, mylist.getModifiedDate() );
        values.put( KEY_CREATEDDATE, mylist.getCreatedDate() );

        db.insert( TABLE_TODO, null,values );
        db.close();
    }

    public ToDoListItem getToDoItem (int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_TODO, new String[] { KEY_ID,
                                KEY_TITLE, KEY_DESCRIPTION, KEY_DATETIME, KEY_MODIFIEDDATE,
                                KEY_CREATEDDATE, KEY_TAG, KEY_PRIORITY },
                       KEY_ID + "=?", new String[] { String.valueOf(id) }, null,
                            null, null, null);

        if(cursor!=null)
            cursor.moveToFirst();

        ToDoListItem mylist = new ToDoListItem(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)
        , null, null);
        return mylist;
    }

    public List<ToDoListItem> getAllToDoListItems() {

        List<ToDoListItem> todolist = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_TODO;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery( selectQuery, null );

        if (cursor.moveToFirst()) {
            do {
                ToDoListItem listItem = new ToDoListItem();
                listItem.setItemId(Integer.parseInt(cursor.getString( 0 )) );
                listItem.setTitle( cursor.getString( 1 ) );
                listItem.setDescription( cursor.getString( 2 ) );
                listItem.setDateTime( cursor.getString( 3 ) );
                listItem.setModifiedDate( cursor.getString( 4 ) );
                listItem.setCreatedDate( cursor.getString( 5 ) );
                listItem.setTag( null );
                listItem.setPriority( null );

                todolist.add( listItem );
            } while (cursor.moveToNext());
        }
        return todolist;
    }

    public int updateTodoListItem(ToDoListItem mylist){
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put( KEY_ID, mylist.getItemId() );
        values.put( KEY_TITLE, mylist.getTitle() );
        values.put( KEY_DESCRIPTION, mylist.getDescription() );
        values.put( KEY_DATETIME, mylist.getDateTime() );
        values.put( KEY_MODIFIEDDATE, mylist.getModifiedDate() );
        values.put( KEY_CREATEDDATE, mylist.getCreatedDate() );

        return db.update(TABLE_TODO, values, KEY_ID + " = ?",
                new String[] { String.valueOf(mylist.getItemId()) });
    }

    public void deleteTodoListItem(ToDoListItem mylist) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TODO, KEY_ID + " = ?",
                new String[] { String.valueOf(mylist.getItemId()) });
        db.close();
    }

    public int getItemsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_TODO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

}
