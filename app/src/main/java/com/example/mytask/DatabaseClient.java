package com.example.mytask;


import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {
 
    private Context mCtx;
    private static DatabaseClient mInstance;
    
    //our app database object
    private AppDatabase appDatabase;

    private AppDatabase ChatDatabase;
 
    private DatabaseClient(Context mCtx) {
        this.mCtx = mCtx;
        
        //creating the app database with Room database builder
        //MyToDos is the name of the database
        appDatabase = Room.databaseBuilder(mCtx, AppDatabase.class, "Mycarts").fallbackToDestructiveMigration().build();

//        ChatDatabase = Room.databaseBuilder(mCtx, AppDatabase.class, "ChatDatabase").build();
    }

    public static synchronized DatabaseClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DatabaseClient(mCtx);
        }
        return mInstance;
    }
 
    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
}