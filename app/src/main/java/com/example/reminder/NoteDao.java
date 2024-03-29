package com.example.reminder;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("select * from notes where state = 1 order by priority desc")
    List<mySchema>  getNotes();

    @Query("select * from notes where state=0")
    List<mySchema> getExtraNotes();
    @Delete
    int deleteNote(mySchema note);

    @Insert
    long InsertNote(mySchema note);

    @Update
    int  update(mySchema note);

}
