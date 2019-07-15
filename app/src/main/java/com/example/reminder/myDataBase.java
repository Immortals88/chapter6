package com.example.reminder;


import android.content.Context;

import androidx.room.Database;

import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {mySchema.class},version = 1,exportSchema = false)
public abstract class myDataBase extends RoomDatabase {
    private static final String DB_NAME = "Database.db";
    private static volatile myDataBase instance;
    public abstract NoteDao  noteDao();
    static synchronized myDataBase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }
    private static myDataBase create(final Context context) {
        return Room.databaseBuilder(
                context,
                myDataBase.class,
                DB_NAME).build();
    }
}
