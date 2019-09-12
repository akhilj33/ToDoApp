package com.example.mytodoapp.db.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.mytodoapp.db.dao.ItemDao;
import com.example.mytodoapp.db.helper.PriorityTypeConvertor;
import com.example.mytodoapp.db.helper.TagTypeConvertor;
import com.example.mytodoapp.pojo.ToDoListItem;
import com.example.mytodoapp.pojo.ToDoTag;

@Database(entities = {ToDoListItem.class, ToDoTag.class}, version = 1, exportSchema = false)
@TypeConverters({TagTypeConvertor.class, PriorityTypeConvertor.class})
public abstract class ItemRoomDatabase extends RoomDatabase {

    private static ItemRoomDatabase INSTANCE;

    public static ItemRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ItemRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder( context.getApplicationContext(),
                            ItemRoomDatabase.class, "ToDoDatabase" ).build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract ItemDao itemDao();

}
