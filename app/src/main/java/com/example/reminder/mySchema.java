package com.example.reminder;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "notes")
public class mySchema {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "text")
    private String text;

    @ColumnInfo(name = "state")
    private int state;

    @ColumnInfo(name = "date")
    private int date;

    @ColumnInfo(name = "priority")
    private int priority;

    /*
    state=0: top;

     */
    public int getId(){   return id;  }
    public void setId(int outID){id=outID;}
    public String getText(){  return text; }
    public void setText(String content){text=content;}
    public int getState(){  return state; }
    public void setState(int outState){ state=outState; }
    public int getDate(){return date;}
    public void setDate(int outDate){date=outDate;}
    public int getPriority(){
        return priority;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }
}
