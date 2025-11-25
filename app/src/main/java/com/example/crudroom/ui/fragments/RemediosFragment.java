package com.example.crudroom.ui.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crudroom.R;
import com.example.crudroom.db.Medication;
import com.example.crudroom.ui.adapters.MedicationAdapter;
import com.example.crudroom.viewmodel.MedicationViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class RemediosFragment extends Fragment implements MedicationAdapter.OnMedicationClickListener {

    private static final String TAG = "RemediosFragment";
    private MedicationViewModel medicationViewModel;
    private MedicationAdapter adapter;
    private RecyclerView recyclerView;
    private TextView emptyView;
    private TextInputEditText editTextSearch;
    private LiveData<List<Medication>> currentLiveData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            View view = inflater.inflate(R.layout.fragment_remedios, container, false);

            recyclerView = view.findViewById(R.id.recyclerView);
            emptyView = view.findViewById(R.id.empty_view);
            editTextSearch = view.findViewById(R.id.editTextSearch);

            if (recyclerView == null) {
                Log.e(TAG, "RecyclerView não encontrado no layout");
                return view;
            }

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new MedicationAdapter();
            adapter.setOnMedicationClickListener(this);
            recyclerView.setAdapter(adapter);

            medicationViewModel = new ViewModelProvider(requireActivity()).get(MedicationViewModel.class);

            FloatingActionButton fab = view.findViewById(R.id.fab);
            if (fab != null) {
                fab.setOnClickListener(v -> showAddEditMedicationDialog(null));
            }

            if (editTextSearch != null) {
                editTextSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        loadMedications();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {}
                });
            }

            loadMedications();
            return view;
        } catch (Exception e) {
            Log.e(TAG, "Erro em onCreateView: ", e);
            return null;
        }
    }

    private void loadMedications() {
        try {
            String searchQuery = "";
            if (editTextSearch != null && editTextSearch.getText() != null) {
                searchQuery = editTextSearch.getText().toString().trim();
            }

            if (currentLiveData != null) {
                currentLiveData.removeObservers(getViewLifecycleOwner());
            }

            if (!TextUtils.isEmpty(searchQuery)) {
                currentLiveData = medicationViewModel.searchMedications(searchQuery);
            } else {
                currentLiveData = medicationViewModel.getAllMedications();
            }

            currentLiveData.observe(getViewLifecycleOwner(), this::updateUI);
        } catch (Exception e) {
            Log.e(TAG, "Erro ao carregar medicações: ", e);
        }
    }

    private void updateUI(List<Medication> medications) {
        try {
            if (adapter != null) {
                adapter.setMedications(medications != null ? medications : new ArrayList<>());
            }

            if (medications == null || medications.isEmpty()) {
                if (recyclerView != null) recyclerView.setVisibility(View.GONE);
                if (emptyView != null) emptyView.setVisibility(View.VISIBLE);
            } else {
                if (recyclerView != null) recyclerView.setVisibility(View.VISIBLE);
                if (emptyView != null) emptyView.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            Log.e(TAG, "Erro ao atualizar UI: ", e);
        }
    }

    private void showAddEditMedicationDialog(Medication medication) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_add_edit_medication, null);

            TextInputEditText editTextMedicationName = dialogView.findViewById(R.id.editTextMedicationName);
            TextInputEditText editTextDosage = dialogView.findViewById(R.id.editTextDosage);
            TextInputEditText editTextType = dialogView.findViewById(R.id.editTextType);
            TextInputEditText editTextRecurrence = dialogView.findViewById(R.id.editTextRecurrence);
            TextInputEditText editTextStartDate = dialogView.findViewById(R.id.editTextStartDate);
            TextInputEditText editTextEndDate = dialogView.findViewById(R.id.editTextEndDate);
            TextView textViewDialogTitle = dialogView.findViewById(R.id.textViewDialogTitle);
            MaterialButton buttonDelete = dialogView.findViewById(R.id.buttonDelete);
            MaterialButton buttonCancel = dialogView.findViewById(R.id.buttonCancel);
            MaterialButton buttonSave = dialogView.findViewById(R.id.buttonSave);

            if (medication != null) {
                textViewDialogTitle.setText("Editar Medicação");
                editTextMedicationName.setText(medication.getMedicationName());
                editTextDosage.setText(medication.getDosage());
                editTextType.setText(medication.getType());
                editTextRecurrence.setText(medication.getRecurrence());
                editTextStartDate.setText(medication.getStartDate());
                editTextEndDate.setText(medication.getEndDate());
                buttonDelete.setVisibility(View.VISIBLE);
            } else {
                textViewDialogTitle.setText("Adicionar Medicação");
                buttonDelete.setVisibility(View.GONE);
            }

            builder.setView(dialogView);
            AlertDialog dialog = builder.create();

            buttonCancel.setOnClickListener(v -> dialog.dismiss());

            buttonDelete.setOnClickListener(v -> {
                dialog.dismiss();
                showDeleteConfirmationDialog(medication);
            });

            buttonSave.setOnClickListener(v -> {
                String medicationName = editTextMedicationName.getText().toString().trim();
                String dosage = editTextDosage.getText().toString().trim();
                String type = editTextType.getText().toString().trim();
                String recurrence = editTextRecurrence.getText().toString().trim();
                String startDate = editTextStartDate.getText().toString().trim();
                String endDate = editTextEndDate.getText().toString().trim();

                if (TextUtils.isEmpty(medicationName)) {
                    editTextMedicationName.setError("Nome da medicação é obrigatório");
                    return;
                }

                if (TextUtils.isEmpty(startDate)) {
                    editTextStartDate.setError("Data de início é obrigatória");
                    return;
                }

                if (medication == null) {
                    medicationViewModel.insertMedication(medicationName, dosage, type, recurrence, startDate, endDate);
                    Toast.makeText(getContext(), "Medicação adicionada!", Toast.LENGTH_SHORT).show();
                } else {
                    medication.setMedicationName(medicationName);
                    medication.setDosage(dosage);
                    medication.setType(type);
                    medication.setRecurrence(recurrence);
                    medication.setStartDate(startDate);
                    medication.setEndDate(endDate);
                    medicationViewModel.updateMedication(medication);
                    Toast.makeText(getContext(), "Medicação atualizada!", Toast.LENGTH_SHORT).show();
                }

                dialog.dismiss();
            });

            dialog.show();
        } catch (Exception e) {
            Log.e(TAG, "Erro ao mostrar dialog: ", e);
        }
    }

    private void showDeleteConfirmationDialog(Medication medication) {
        try {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Confirmar Exclusão")
                    .setMessage("Tem certeza que deseja excluir esta medicação?")
                    .setPositiveButton("Excluir", (dialog, which) -> {
                        medicationViewModel.deleteMedication(medication);
                        Toast.makeText(getContext(), "Medicação excluída!", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        } catch (Exception e) {
            Log.e(TAG, "Erro ao mostrar confirmação: ", e);
        }
    }

    @Override
    public void onMedicationClick(Medication medication) {
        showAddEditMedicationDialog(medication);
    }

    @Override
    public void onMedicationLongClick(Medication medication) {
        showDeleteConfirmationDialog(medication);
    }
}
