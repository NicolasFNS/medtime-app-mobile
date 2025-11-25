package com.example.crudroom.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crudroom.R;
import com.example.crudroom.db.IntakeHistory;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<IntakeHistory> historyList = new ArrayList<>();

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_history, parent, false);
        return new HistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        IntakeHistory history = historyList.get(position);
        holder.textViewDateTime.setText(history.getDataHora());
        holder.textViewStatus.setText(history.isTaken() ? "Tomado" : "Não tomado");
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public void setHistoryList(List<IntakeHistory> historyList) {
        this.historyList = historyList != null ? historyList : new ArrayList<>();
        notifyDataSetChanged();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDateTime;
        TextView textViewStatus;

        HistoryViewHolder(View view) {
            super(view);
            textViewDateTime = view.findViewById(R.id.textViewHistoryDateTime);
            textViewStatus = view.findViewById(R.id.textViewHistoryStatus);
        }
    }
}
