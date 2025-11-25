package com.example.crudroom.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.crudroom.db.Familiar;
import com.example.crudroom.repository.FamiliarRepository;

import java.util.List;

public class FamiliarViewModel extends AndroidViewModel {
    private FamiliarRepository repository;

    public FamiliarViewModel(@NonNull Application application) {
        super(application);
        repository = new FamiliarRepository(application);
    }

    public void insertFamiliar(String name, String phone) {
        Familiar familiar = new Familiar(name, phone);
        repository.insertFamiliar(familiar);
    }

    public void updateFamiliar(Familiar familiar) {
        repository.updateFamiliar(familiar);
    }

    public void deleteFamiliar(Familiar familiar) {
        repository.deleteFamiliar(familiar);
    }

    public LiveData<List<Familiar>> getAllFamiliars() {
        return repository.getAllFamiliars();
    }
}
