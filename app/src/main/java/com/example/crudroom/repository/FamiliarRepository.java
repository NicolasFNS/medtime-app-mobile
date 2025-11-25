package com.example.crudroom.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.crudroom.db.Familiar;
import com.example.crudroom.db.FamiliarDao;
import com.example.crudroom.db.MedicationDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FamiliarRepository {
    private FamiliarDao familiarDao;
    private ExecutorService executor;

    public FamiliarRepository(Application application) {
        MedicationDatabase database = MedicationDatabase.getInstance(application);
        familiarDao = database.familiarDao();
        executor = Executors.newSingleThreadExecutor();
    }

    public void insertFamiliar(Familiar familiar) {
        executor.execute(() -> familiarDao.insertFamiliar(familiar));
    }

    public void updateFamiliar(Familiar familiar) {
        executor.execute(() -> familiarDao.updateFamiliar(familiar));
    }

    public void deleteFamiliar(Familiar familiar) {
        executor.execute(() -> familiarDao.deleteFamiliar(familiar));
    }

    public LiveData<List<Familiar>> getAllFamiliars() {
        return familiarDao.getAllFamiliars();
    }
}
