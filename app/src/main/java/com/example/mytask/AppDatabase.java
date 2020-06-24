package com.example.mytask;



import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {Productfile.class}, version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();




}