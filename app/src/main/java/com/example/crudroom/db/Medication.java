package com.example.crudroom.db;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

//é o arquivo que faz a estrutura do item da tabela

@Entity(tableName = "medications")
public class Medication {
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    private String medicationName;
    private String dosage;
    private String nextDoseDate;
    private String createdDate;
    
    public Medication() {
    }
    
    @Ignore
    public Medication(String medicationName, String dosage, String nextDoseDate, String createdDate) {
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.nextDoseDate = nextDoseDate;
        this.createdDate = createdDate;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getMedicationName() {
        return medicationName;
    }
    
    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }
    
    public String getDosage() {
        return dosage;
    }
    
    public void setDosage(String dosage) {
        this.dosage = dosage;
    }
    
    public String getNextDoseDate() {
        return nextDoseDate;
    }
    
    public void setNextDoseDate(String nextDoseDate) {
        this.nextDoseDate = nextDoseDate;
    }
    
    public String getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}

