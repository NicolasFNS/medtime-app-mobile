package com.example.crudroom.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface IntakeHistoryDao {

    @Insert
    void insertHistory(IntakeHistory intakeHistory);

    //buscar todas
    @Query("SELECT * FROM history ORDER BY dataHora ASC")
    LiveData<List<IntakeHistory>> getHistory();

    //busca parcial
    @Query("SELECT * FROM history WHERE dataHora LIKE :searchQuery OR taken LIKE :searchQuery OR id LIKE :searchQuery ORDER BY dataHora ASC")
    LiveData<List<IntakeHistory>> searchHistory(String searchQuery);

    @Update
    void updateHistory(IntakeHistory intakeHistory);

    @Delete
    void deleteHistory(IntakeHistory intakeHistory);

    //deleta todas
    @Query("DELETE FROM history")
    void deleteAllHistory();

}
