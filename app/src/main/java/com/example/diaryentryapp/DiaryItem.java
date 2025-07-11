package com.example.diaryentryapp;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DiaryItem {
    @PrimaryKey
    @NonNull
    private String Date;
    private String Content;

    public DiaryItem(String Date, String Content){
        this.Date = Date;
        this.Content = Content;
    }

    public String getDate(){
        return this.Date;
    }

    public void setDate(String Date){
        this.Date = Date;
    }

    public String getContent(){
        return this.Content;
    }

    public void setContent(){
        this.Content = Content;
    }
}
