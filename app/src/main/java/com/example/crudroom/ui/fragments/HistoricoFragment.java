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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crudroom.R;
import com.example.crudroom.db.IntakeHistory;
import com.example.crudroom.ui.adapters.HistoryAdapter;
import com.example.crudroom.viewmodel.HistoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class HistoricoFragment extends Fragment {

    private static final String TAG = "HistoricoFragment";
    private HistoryViewModel historyViewModel;
    private HistoryAdapter adapter;
    private RecyclerView recyclerView;
    private TextView emptyView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            View view = inflater.inflate(R.layout.fragment_historico, container, false);

            recyclerView = view.findViewById(R.id.history_recycler_view);
            emptyView = view.findViewById(R.id.history_empty_view);

            if (recyclerView == null) {
                Log.e(TAG, "RecyclerView não encontrado no layout");
                return view;
            }

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new HistoryAdapter();
            recyclerView.setAdapter(adapter);

            historyViewModel = new ViewModelProvider(requireActivity()).get(HistoryViewModel.class);
            historyViewModel.getAllHistory().observe(getViewLifecycleOwner(), this::updateUI);

            return view;
        } catch (Exception e) {
            Log.e(TAG, "Erro em onCreateView: ", e);
            return null;
        }
    }

    private void updateUI(List<IntakeHistory> historyList) {
        try {
            if (adapter != null) {
                adapter.setHistoryList(historyList != null ? historyList : new ArrayList<>());
            }

            if (historyList == null || historyList.isEmpty()) {
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
}
