package com.example.crudroom.ui.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crudroom.R;
import com.example.crudroom.db.Familiar;
import com.example.crudroom.ui.adapters.FamiliarAdapter;
import com.example.crudroom.viewmodel.FamiliarViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class FamiliarFragment extends Fragment implements FamiliarAdapter.OnFamiliarClickListener {

    private static final String TAG = "FamiliarFragment";
    private FamiliarViewModel familiarViewModel;
    private FamiliarAdapter adapter;
    private RecyclerView recyclerView;
    private TextView emptyView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            View view = inflater.inflate(R.layout.fragment_familiar, container, false);

            recyclerView = view.findViewById(R.id.familiar_recycler_view);
            emptyView = view.findViewById(R.id.familiar_empty_view);

            if (recyclerView == null) {
                Log.e(TAG, "RecyclerView não encontrado no layout");
                return view;
            }

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new FamiliarAdapter();
            adapter.setOnFamiliarClickListener(this);
            recyclerView.setAdapter(adapter);

            familiarViewModel = new ViewModelProvider(requireActivity()).get(FamiliarViewModel.class);

            FloatingActionButton fab = view.findViewById(R.id.fab_add_familiar);
            if (fab != null) {
                fab.setOnClickListener(v -> showAddEditFamiliarDialog(null));
            }

            familiarViewModel.getAllFamiliars().observe(getViewLifecycleOwner(), this::updateUI);

            return view;
        } catch (Exception e) {
            Log.e(TAG, "Erro em onCreateView: ", e);
            return null;
        }
    }

    private void updateUI(List<Familiar> familiars) {
        try {
            if (adapter != null) {
                adapter.setFamiliars(familiars != null ? familiars : new ArrayList<>());
            }

            if (familiars == null || familiars.isEmpty()) {
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

    private void showAddEditFamiliarDialog(Familiar familiar) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_add_edit_familiar, null);

            TextInputEditText editTextName = dialogView.findViewById(R.id.editTextFamiliarName);
            TextInputEditText editTextPhone = dialogView.findViewById(R.id.editTextFamiliarPhone);
            TextView textViewDialogTitle = dialogView.findViewById(R.id.textViewFamiliarDialogTitle);
            MaterialButton buttonDelete = dialogView.findViewById(R.id.buttonDeleteFamiliar);
            MaterialButton buttonCancel = dialogView.findViewById(R.id.buttonCancelFamiliar);
            MaterialButton buttonSave = dialogView.findViewById(R.id.buttonSaveFamiliar);

            if (familiar != null) {
                textViewDialogTitle.setText("Editar Familiar");
                editTextName.setText(familiar.getName());
                editTextPhone.setText(familiar.getPhone());
                buttonDelete.setVisibility(View.VISIBLE);
            } else {
                textViewDialogTitle.setText("Adicionar Familiar");
                buttonDelete.setVisibility(View.GONE);
            }

            builder.setView(dialogView);
            AlertDialog dialog = builder.create();

            buttonCancel.setOnClickListener(v -> dialog.dismiss());

            buttonDelete.setOnClickListener(v -> {
                dialog.dismiss();
                showDeleteConfirmationDialog(familiar);
            });

            buttonSave.setOnClickListener(v -> {
                String name = editTextName.getText().toString().trim();
                String phone = editTextPhone.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    editTextName.setError("Nome é obrigatório");
                    return;
                }

                if (TextUtils.isEmpty(phone)) {
                    editTextPhone.setError("Telefone é obrigatório");
                    return;
                }

                if (familiar == null) {
                    familiarViewModel.insertFamiliar(name, phone);
                    Toast.makeText(getContext(), "Familiar adicionado!", Toast.LENGTH_SHORT).show();
                } else {
                    familiar.setName(name);
                    familiar.setPhone(phone);
                    familiarViewModel.updateFamiliar(familiar);
                    Toast.makeText(getContext(), "Familiar atualizado!", Toast.LENGTH_SHORT).show();
                }

                dialog.dismiss();
            });

            dialog.show();
        } catch (Exception e) {
            Log.e(TAG, "Erro ao mostrar dialog: ", e);
        }
    }

    private void showDeleteConfirmationDialog(Familiar familiar) {
        try {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Confirmar Exclusão")
                    .setMessage("Tem certeza que deseja excluir este familiar?")
                    .setPositiveButton("Excluir", (dialog, which) -> {
                        familiarViewModel.deleteFamiliar(familiar);
                        Toast.makeText(getContext(), "Familiar excluído!", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        } catch (Exception e) {
            Log.e(TAG, "Erro ao mostrar confirmação: ", e);
        }
    }

    @Override
    public void onFamiliarClick(Familiar familiar) {
        showAddEditFamiliarDialog(familiar);
    }

    @Override
    public void onFamiliarLongClick(Familiar familiar) {
        showDeleteConfirmationDialog(familiar);
    }
}
