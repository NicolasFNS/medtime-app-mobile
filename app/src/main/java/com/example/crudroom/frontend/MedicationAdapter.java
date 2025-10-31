package com.example.crudroom.frontend;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crudroom.R;
import com.example.crudroom.db.Medication;

import java.util.ArrayList;
import java.util.List;

public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.MedicationViewHolder> {

    private List<Medication> medications = new ArrayList<>();
    private OnMedicationClickListener listener;

    // Interface de callback para cliques curtos e longos
    public interface OnMedicationClickListener {
        void onMedicationClick(Medication medication);
        void onMedicationLongClick(Medication medication);
    }

    public void setOnMedicationClickListener(OnMedicationClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MedicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medication_item, parent, false);
        return new MedicationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicationViewHolder holder, int position) {
        Medication currentMedication = medications.get(position);

        holder.textViewMedicationName.setText(currentMedication.getMedicationName());
        holder.textViewDosage.setText(currentMedication.getDosage());
        holder.textViewNextDoseDate.setText(currentMedication.getNextDoseDate());

        // Clique curto
        holder.itemView.setOnClickListener(v -> {
            int pos = holder.getBindingAdapterPosition();
            if (listener != null && pos != RecyclerView.NO_POSITION) {
                listener.onMedicationClick(medications.get(pos));
            }
        });

        // Clique longo
        holder.itemView.setOnLongClickListener(v -> {
            int pos = holder.getBindingAdapterPosition();
            if (listener != null && pos != RecyclerView.NO_POSITION) {
                listener.onMedicationLongClick(medications.get(pos));
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return medications.size();
    }

    // Atualiza a lista exibida
    public void setMedications(List<Medication> medications) {
        this.medications = medications;
        notifyItemRangeChanged(0, medications.size());
    }

    // Classe interna pública e estática (padrão correto)
    public static class MedicationViewHolder extends RecyclerView.ViewHolder {
        final TextView textViewMedicationName;
        final TextView textViewDosage;
        final TextView textViewNextDoseDate;

        public MedicationViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMedicationName = itemView.findViewById(R.id.textViewMedicationName);
            textViewDosage = itemView.findViewById(R.id.textViewDosage);
            textViewNextDoseDate = itemView.findViewById(R.id.textViewNextDoseDate);
        }
    }
}
