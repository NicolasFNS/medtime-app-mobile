package com.example.crudroom.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.crudroom.db.IntakeHistory;
import com.example.crudroom.db.IntakeHistoryDao;
import com.example.crudroom.db.MedicationDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HistoryRepository {
    private IntakeHistoryDao historyDao;
    private ExecutorService executor;

    public HistoryRepository(Application application) {
        MedicationDatabase database = MedicationDatabase.getInstance(application);
        historyDao = database.intakeHistoryDao();
        executor = Executors.newSingleThreadExecutor();
    }

    public void insertHistory(IntakeHistory history) {
        executor.execute(() -> historyDao.insertHistory(history));
    }

    public void updateHistory(IntakeHistory history) {
        executor.execute(() -> historyDao.updateHistory(history));
    }

    public void deleteHistory(IntakeHistory history) {
        executor.execute(() -> historyDao.deleteHistory(history));
    }

    public LiveData<List<IntakeHistory>> getAllHistory() {
        return historyDao.getHistory();
    }
}
