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
    
    // CREATE - Inserir nova medicação
    @Insert
    void insertMedication(Medication medication);
    
    // READ - Buscar todas as medicações
    @Query("SELECT * FROM medications ORDER BY nextDoseDate ASC")
    LiveData<List<Medication>> getAllMedications();
    
    // READ - Buscar medicações por nome (busca parcial)
    @Query("SELECT * FROM medications WHERE medicationName LIKE :searchQuery OR dosage LIKE :searchQuery ORDER BY nextDoseDate ASC")
    LiveData<List<Medication>> searchMedications(String searchQuery);
    
    // UPDATE - Atualizar medicação
    @Update
    void updateMedication(Medication medication);
    
    // DELETE - Deletar medicação
    @Delete
    void deleteMedication(Medication medication);
    
    // DELETE - Deletar todas as medicações
    @Query("DELETE FROM medications")
    void deleteAllMedications();
}

