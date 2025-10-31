package com.example.crudroom.backend;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.crudroom.db.Medication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MedicationViewModel extends AndroidViewModel {
    private MedicationRepository repository;
    
    public MedicationViewModel(@NonNull Application application) {
        super(application);
        repository = new MedicationRepository(application);
    }
    
    public void insertMedication(String medicationName, String dosage, String nextDoseDate) {
        String currentDate = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());
        Medication medication = new Medication(medicationName, dosage, nextDoseDate, currentDate);
        repository.insertMedication(medication);
    }
    
    public void updateMedication(Medication medication) {
        repository.updateMedication(medication);
    }
    
    public void deleteMedication(Medication medication) {
        repository.deleteMedication(medication);
    }
    
    public LiveData<List<Medication>> getAllMedications() {
        return repository.getAllMedications();
    }
    
    public LiveData<List<Medication>> searchMedications(String searchQuery) {
        return repository.searchMedications(searchQuery);
    }
}

