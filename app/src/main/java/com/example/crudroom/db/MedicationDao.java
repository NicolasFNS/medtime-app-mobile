package com.example.crudroom.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MedicationDao {

    @Insert
    void insertMedication(Medication medication);
    
    //buscar todas
    @Query("SELECT * FROM medications ORDER BY startDate ASC")
    LiveData<List<Medication>> getAllMedications();
    
    //busca parcial
    @Query("SELECT * FROM medications WHERE medicationName LIKE :searchQuery OR type LIKE :searchQuery OR dosage LIKE :searchQuery ORDER BY startDate ASC")
    LiveData<List<Medication>> searchMedications(String searchQuery);

    @Update
    void updateMedication(Medication medication);

    @Delete
    void deleteMedication(Medication medication);

    //deleta todas
    @Query("DELETE FROM medications")
    void deleteAllMedications();
}

