package com.example.crudroom.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.crudroom.db.IntakeHistory;
import com.example.crudroom.repository.HistoryRepository;

import java.util.List;

public class HistoryViewModel extends AndroidViewModel {
    private HistoryRepository repository;

    public HistoryViewModel(@NonNull Application application) {
        super(application);
        repository = new HistoryRepository(application);
    }

    public void insertHistory(IntakeHistory history) {
        repository.insertHistory(history);
    }

    public void updateHistory(IntakeHistory history) {
        repository.updateHistory(history);
    }

    public void deleteHistory(IntakeHistory history) {
        repository.deleteHistory(history);
    }

    public LiveData<List<IntakeHistory>> getAllHistory() {
        return repository.getAllHistory();
    }
}
