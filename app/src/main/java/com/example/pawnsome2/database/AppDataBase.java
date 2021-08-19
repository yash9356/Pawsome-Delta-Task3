package com.example.pawnsome2.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FavDog.class},version = 1)
public abstract class AppDataBase extends RoomDatabase {


    public abstract FavDogDao favDogDao();

    private static AppDataBase INSTANCE;

    public static AppDataBase getDbInstance(){
        return INSTANCE;
    }
}
