package com.example.pawnsome2.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FavDog {

    @ColumnInfo(name = "Breed_Id")
    public int breedid;

    @ColumnInfo(name="Breed_Name")
    public String breedname;

    @ColumnInfo(name="Breed_Image_Url")
    public String breedurl;
}
