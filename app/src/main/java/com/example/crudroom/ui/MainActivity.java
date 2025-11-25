package com.example.crudroom.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.crudroom.R;
import com.example.crudroom.ui.fragments.DashboardFragment;
import com.example.crudroom.ui.fragments.FamiliarFragment;
import com.example.crudroom.ui.fragments.HistoricoFragment;
import com.example.crudroom.ui.fragments.RemediosFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main);

            BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
            if (bottomNav != null) {
                bottomNav.setOnNavigationItemSelectedListener(navListener);
                bottomNav.setItemIconTintList(null);
            } else {
                Log.e(TAG, "BottomNavigationView não encontrado!");
            }

            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new DashboardFragment())
                        .commit();
            }
        } catch (Exception e) {
            Log.e(TAG, "Erro em onCreate: ", e);
            finish();
        }
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        Fragment selectedFragment = null;
        int itemId = item.getItemId();

        if (itemId == R.id.navigation_dashboard) {
            selectedFragment = new DashboardFragment();
        } else if (itemId == R.id.navigation_remedios) {
            selectedFragment = new RemediosFragment();
        } else if (itemId == R.id.navigation_historico) {
            selectedFragment = new HistoricoFragment();
        } else if (itemId == R.id.navigation_familiar) {
            selectedFragment = new FamiliarFragment();
        }

        if (selectedFragment != null) {
            try {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            } catch (Exception e) {
                Log.e(TAG, "Erro ao trocar fragment: ", e);
            }
        }
        return true;
    };
}
