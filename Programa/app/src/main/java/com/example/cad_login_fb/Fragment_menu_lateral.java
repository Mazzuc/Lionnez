package com.example.cad_login_fb;

import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import com.google.android.material.navigation.NavigationView;

public class Fragment_menu_lateral extends Fragment implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_lateral, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);

        drawerLayout = view.findViewById(R.id.drawer_layout);
        NavigationView navigationView = view.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(requireActivity(), drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home_teste()).commit();
            navigationView.setCheckedItem(R.id.na_home);
        }

        // Defina dinamicamente a cor do ícone do NavigationView com base no tema
        int iconColor;
        if (isDarkTheme()) {
            iconColor = ContextCompat.getColor(requireContext(), R.color.roxo);
        } else {
            iconColor = ContextCompat.getColor(requireContext(), R.color.verde);
        }

        ColorStateList iconColorStateList = ColorStateList.valueOf(iconColor);
        navigationView.setItemIconTintList(iconColorStateList);

        // Defina dinamicamente a cor do texto do NavigationView com base no tema
        int textColor;
        if (isDarkTheme()) {
            textColor = ContextCompat.getColor(requireContext(), R.color.cinza_escuro);
        } else {
            textColor = ContextCompat.getColor(requireContext(), R.color.cinza_claro);
        }

        ColorStateList textColorStateList = ColorStateList.valueOf(textColor);
        navigationView.setItemTextColor(textColorStateList);

        return view;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.na_home:
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home_teste()).commit();
                break;

            case R.id.nav_home:
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Feedback()).commit();
                break;

            case R.id.nav_algo1:
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AlbumFragment()).commit();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                onBackPressed();
            }
        });
    }

    private void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            requireActivity().onBackPressed();
        }
    }

    private boolean isDarkTheme() {
        int currentNightMode = requireContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES;
    }
}
