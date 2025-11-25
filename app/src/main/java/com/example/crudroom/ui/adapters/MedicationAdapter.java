package com.example.crudroom.ui.adapters;

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
        holder.textViewDosage.setText("Dosagem: " + (currentMedication.getDosage() != null ? currentMedication.getDosage() : "N/A"));
        holder.textViewType.setText("Tipo: " + (currentMedication.getType() != null ? currentMedication.getType() : "N/A"));
        holder.textViewRecurrence.setText("Recorrência: " + (currentMedication.getRecurrence() != null ? currentMedication.getRecurrence() : "N/A"));
        holder.textViewStartDate.setText("Início: " + (currentMedication.getStartDate() != null ? currentMedication.getStartDate() : "N/A"));
        holder.textViewEndDate.setText("Fim: " + (currentMedication.getEndDate() != null ? currentMedication.getEndDate() : "N/A"));

        holder.itemView.setOnClickListener(v -> {
            int pos = holder.getBindingAdapterPosition();
            if (listener != null && pos != RecyclerView.NO_POSITION) {
                listener.onMedicationClick(medications.get(pos));
            }
        });

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

    public void setMedications(List<Medication> medications) {
        this.medications = medications != null ? medications : new ArrayList<>();
        notifyDataSetChanged();
    }

    public static class MedicationViewHolder extends RecyclerView.ViewHolder {
        final TextView textViewMedicationName;
        final TextView textViewDosage;
        final TextView textViewType;
        final TextView textViewRecurrence;
        final TextView textViewStartDate;
        final TextView textViewEndDate;

        public MedicationViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMedicationName = itemView.findViewById(R.id.textViewMedicationName);
            textViewDosage = itemView.findViewById(R.id.textViewDosage);
            textViewType = itemView.findViewById(R.id.textViewType);
            textViewRecurrence = itemView.findViewById(R.id.textViewRecurrence);
            textViewStartDate = itemView.findViewById(R.id.textViewStartDate);
            textViewEndDate = itemView.findViewById(R.id.textViewEndDate);
        }
    }
}
