package com.example.diaryentryapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DiaryItem item);

    @Query("SELECT * FROM DiaryItem ORDER BY date DESC")
    List<DiaryItem> getAll();

    @Query("SELECT * FROM DiaryItem WHERE date= :date LIMIT 1")
    DiaryItem getByDate(String date);
}
