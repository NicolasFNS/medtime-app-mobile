package com.example.crudroom.frontend;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.text.TextWatcher;
import android.text.Editable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crudroom.R;
import com.example.crudroom.db.Medication;
import com.example.crudroom.backend.MedicationViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
//faz o processo antes das execuções

public class MainActivity extends AppCompatActivity implements MedicationAdapter.OnMedicationClickListener {

    private MedicationViewModel medicationViewModel;
    private MedicationAdapter medicationAdapter;
    private RecyclerView recyclerViewMedications;
    private TextView textViewEmpty;
    private TextInputEditText editTextSearch;
    private FloatingActionButton fabAddMedication;

    private LiveData<List<Medication>> currentLiveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupRecyclerView();
        setupViewModel();
        setupClickListeners();
        loadMedications();
    }

    private void initializeViews() {
        recyclerViewMedications = findViewById(R.id.recyclerViewMedications);
        textViewEmpty = findViewById(R.id.textViewEmpty);
        editTextSearch = findViewById(R.id.editTextSearch);
        fabAddMedication = findViewById(R.id.fabAddMedication);
    }

    private void setupRecyclerView() {
        medicationAdapter = new MedicationAdapter();
        medicationAdapter.setOnMedicationClickListener(this);
        recyclerViewMedications.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMedications.setAdapter(medicationAdapter);
    }

    private void setupViewModel() {
        medicationViewModel = new ViewModelProvider(this).get(MedicationViewModel.class);
    }

    private void setupClickListeners() {
        fabAddMedication.setOnClickListener(v -> showAddEditMedicationDialog(null));

        /*
        //codigo usado para pesquisar quando a barra sai do foco
        editTextSearch.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                loadMedications();
            }
        });

        editTextSearch.setOnEditorActionListener((v, actionId, event) -> {
                // Limpa o foco para evitar que a pesquisa não seja ativada
                v.clearFocus();
                return true;
        });
        */

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // não precisa fazer nada aqui apenas é parte de estrutura comum
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loadMedications();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // mesma coisa do before
            }
        });

    }

    private void loadMedications() {
        String searchQuery = editTextSearch.getText().toString().trim();

        if (currentLiveData != null) {
            currentLiveData.removeObservers(this);
        }

        if (!TextUtils.isEmpty(searchQuery)) {
            currentLiveData = medicationViewModel.searchMedications(searchQuery);
        } else {
            currentLiveData = medicationViewModel.getAllMedications();
        }

        currentLiveData.observe(this, medications -> updateUI(medications));
    }

    private void updateUI(List<Medication> medications) {
        medicationAdapter.setMedications(medications);

        if (medications.isEmpty()) {
            textViewEmpty.setVisibility(View.VISIBLE);
            recyclerViewMedications.setVisibility(View.GONE);
        } else {
            textViewEmpty.setVisibility(View.GONE);
            recyclerViewMedications.setVisibility(View.VISIBLE);
        }
    }

    private void showAddEditMedicationDialog(Medication medication) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_edit_medication, null);

        TextInputEditText editTextMedicationName = dialogView.findViewById(R.id.editTextMedicationName);
        TextInputEditText editTextDosage = dialogView.findViewById(R.id.editTextDosage);
        TextInputEditText editTextNextDoseDate = dialogView.findViewById(R.id.editTextNextDoseDate);
        TextView textViewDialogTitle = dialogView.findViewById(R.id.textViewDialogTitle);
        MaterialButton buttonDelete = dialogView.findViewById(R.id.buttonDelete);
        MaterialButton buttonCancel = dialogView.findViewById(R.id.buttonCancel);
        MaterialButton buttonSave = dialogView.findViewById(R.id.buttonSave);

        if (medication != null) {
            textViewDialogTitle.setText("Editar Medicação");
            editTextMedicationName.setText(medication.getMedicationName());
            editTextDosage.setText(medication.getDosage());
            editTextNextDoseDate.setText(medication.getNextDoseDate());
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
            String nextDoseDate = editTextNextDoseDate.getText().toString().trim();

            if (TextUtils.isEmpty(medicationName)) {
                editTextMedicationName.setError("Nome da medicação é obrigatório");
                return;
            }

            if (TextUtils.isEmpty(nextDoseDate)) {
                editTextNextDoseDate.setError("Data da próxima dose é obrigatória");
                return;
            }

            if (medication == null) {
                medicationViewModel.insertMedication(medicationName, dosage, nextDoseDate);
                Toast.makeText(MainActivity.this, "Medicação adicionada!", Toast.LENGTH_SHORT).show();
            } else {
                medication.setMedicationName(medicationName);
                medication.setDosage(dosage);
                medication.setNextDoseDate(nextDoseDate);
                medicationViewModel.updateMedication(medication);
                Toast.makeText(MainActivity.this, "Medicação atualizada!", Toast.LENGTH_SHORT).show();
            }

            dialog.dismiss();
        });

        dialog.show();
    }

    private void showDeleteConfirmationDialog(Medication medication) {
        new AlertDialog.Builder(this)
                .setTitle("Confirmar Exclusão")
                .setMessage("Tem certeza que deseja excluir esta medicação?")
                .setPositiveButton("Excluir", (dialog, which) -> {
                    medicationViewModel.deleteMedication(medication);
                    Toast.makeText(MainActivity.this, "Medicação excluída!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", null)
                .show();
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
