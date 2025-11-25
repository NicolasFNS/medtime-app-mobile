package com.example.crudroom.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FamiliarDao {

    @Insert
    void insertFamiliar(Familiar familiar);

    @Query("SELECT * FROM familiars ORDER BY name ASC")
    LiveData<List<Familiar>> getAllFamiliars();

    @Update
    void updateFamiliar(Familiar familiar);

    @Delete
    void deleteFamiliar(Familiar familiar);

    @Query("DELETE FROM familiars")
    void deleteAllFamiliars();
}
