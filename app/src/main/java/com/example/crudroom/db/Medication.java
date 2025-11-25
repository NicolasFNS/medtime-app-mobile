package com.example.crudroom.db;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

//é o arquivo que faz a estrutura do item da tabela

@Entity(tableName = "medications")
public class Medication {
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    private String medicationName;
    private String dosage;
    private String type;
    private String recurrence;
    private String startDate;
    private String endDate;
    private String createdDate;
    
    public Medication() {

    }
    
    @Ignore
    public Medication(String medicationName, String dosage, String type, String recurrence, String startDate, String endDate, String createdDate) {
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.type = type;
        this.recurrence = recurrence;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdDate = createdDate;
    }

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
    
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    
    public String getRecurrence() {
        return recurrence;
    }
    public void setRecurrence(String recurrence) {
        this.recurrence = recurrence;
    }
    
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    
    public String getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}

