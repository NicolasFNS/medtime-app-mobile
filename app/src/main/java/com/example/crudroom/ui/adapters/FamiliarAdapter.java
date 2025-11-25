package com.example.crudroom.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crudroom.R;
import com.example.crudroom.db.Familiar;

import java.util.ArrayList;
import java.util.List;

public class FamiliarAdapter extends RecyclerView.Adapter<FamiliarAdapter.FamiliarViewHolder> {

    private List<Familiar> familiars = new ArrayList<>();
    private OnFamiliarClickListener listener;

    public interface OnFamiliarClickListener {
        void onFamiliarClick(Familiar familiar);
        void onFamiliarLongClick(Familiar familiar);
    }

    public void setOnFamiliarClickListener(OnFamiliarClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public FamiliarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_familiar, parent, false);
        return new FamiliarViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FamiliarViewHolder holder, int position) {
        Familiar familiar = familiars.get(position);
        holder.familiarName.setText(familiar.getName());
        holder.familiarPhone.setText(familiar.getPhone());

        holder.itemView.setOnClickListener(v -> {
            int pos = holder.getBindingAdapterPosition();
            if (listener != null && pos != RecyclerView.NO_POSITION) {
                listener.onFamiliarClick(familiars.get(pos));
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            int pos = holder.getBindingAdapterPosition();
            if (listener != null && pos != RecyclerView.NO_POSITION) {
                listener.onFamiliarLongClick(familiars.get(pos));
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return familiars.size();
    }

    public void setFamiliars(List<Familiar> familiars) {
        this.familiars = familiars != null ? familiars : new ArrayList<>();
        notifyDataSetChanged();
    }

    public static class FamiliarViewHolder extends RecyclerView.ViewHolder {
        TextView familiarName;
        TextView familiarPhone;

        FamiliarViewHolder(View view) {
            super(view);
            familiarName = view.findViewById(R.id.familiar_name);
            familiarPhone = view.findViewById(R.id.familiar_phone);
        }
    }
}
