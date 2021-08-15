package com.example.pawnsome2.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavDogDao {

    @Query("SELECT * FROM FavDog")
    List<FavDog> getAllFavDog();

    @Insert
    void insertFavDog(FavDog... favDogs);

    @Delete
    void delete(FavDog favDog);
}
