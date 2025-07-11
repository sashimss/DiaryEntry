package com.example.diaryentryapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {DiaryItem.class}, version = 1)
public abstract class DiaryDatabase extends RoomDatabase {
    private static DiaryDatabase INSTANCE;
    public abstract ItemDao itemDao();

    public static synchronized DiaryDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    DiaryDatabase.class, "diary-db")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

}
