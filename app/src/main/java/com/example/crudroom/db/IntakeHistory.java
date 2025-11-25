package com.example.crudroom.db;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "history",
    foreignKeys = @ForeignKey(
            entity = Medication.class,
            parentColumns = "id",
            childColumns = "medicationId",
            onDelete = ForeignKey.CASCADE
    )
)
public class IntakeHistory {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int medicationId;
    private String dataHora;
    private boolean taken;

    public IntakeHistory(){

    }

    @Ignore
    public IntakeHistory(int id, int medicationId, String dataHora, boolean taken){
        this.id = id;
        this.medicationId = medicationId;
        this.dataHora = dataHora;
        this.taken = taken;
    }

    public int getId(){
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getMedicationId(){
        return medicationId;
    }
    public void setMedicationId(int medicationId) {
        this.medicationId = medicationId;
    }

    public String getDataHora(){
        return dataHora;
    }
    public void setDataHora(String dataHora){
        this.dataHora = dataHora;
    }

    public boolean isTaken(){
        return taken;
    }

    public void setTaken(boolean taken){
        this.taken = taken;
    }

}
