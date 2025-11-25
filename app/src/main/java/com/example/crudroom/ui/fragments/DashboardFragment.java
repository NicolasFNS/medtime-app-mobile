package com.example.crudroom.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.crudroom.R;
import com.example.crudroom.db.Medication;
import com.example.crudroom.viewmodel.MedicationViewModel;

import java.util.List;

public class DashboardFragment extends Fragment {

    private static final String TAG = "DashboardFragment";
    private MedicationViewModel medicationViewModel;
    private TextView textViewNextMedication;
    private TextView textViewTotalMedications;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

            textViewNextMedication = view.findViewById(R.id.textViewNextMedication);
            textViewTotalMedications = view.findViewById(R.id.textViewTotalMedications);

            medicationViewModel = new ViewModelProvider(requireActivity()).get(MedicationViewModel.class);

            medicationViewModel.getAllMedications().observe(getViewLifecycleOwner(), this::updateDashboard);

            return view;
        } catch (Exception e) {
            Log.e(TAG, "Erro em onCreateView: ", e);
            return null;
        }
    }

    private void updateDashboard(List<Medication> medications) {
        try {
            if (medications == null || medications.isEmpty()) {
                if (textViewNextMedication != null) {
                    textViewNextMedication.setText("Nenhum medicamento cadastrado");
                }
                if (textViewTotalMedications != null) {
                    textViewTotalMedications.setText("Total: 0 medicamentos");
                }
            } else {
                Medication nextMed = medications.get(0);
                if (textViewNextMedication != null) {
                    textViewNextMedication.setText("Próximo: " + nextMed.getMedicationName());
                }
                if (textViewTotalMedications != null) {
                    textViewTotalMedications.setText("Total: " + medications.size() + " medicamento(s)");
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Erro ao atualizar dashboard: ", e);
        }
    }
}
