package com.example.cad_login_fb;

import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

// public class TelaTesteImg extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public class TelaTesteImg extends AppCompatActivity{

   // private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testes);

        // mostrar caixa de diálogo de classificação

        RateUsDialog rateUsDialog = new RateUsDialog(TelaTesteImg.this);
        rateUsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        rateUsDialog.setCancelable(false);
        rateUsDialog.show();

       /* Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home_teste()).commit();
            navigationView.setCheckedItem(R.id.na_home);
        }

        // Defina dinamicamente a cor do ícone do NavigationView com base no tema
        int iconColor;
        if (isDarkTheme()) {
            iconColor = ContextCompat.getColor(this, R.color.roxo);
        } else {
            iconColor = ContextCompat.getColor(this, R.color.verde);
        }

        ColorStateList iconColorStateList = ColorStateList.valueOf(iconColor);
        navigationView.setItemIconTintList(iconColorStateList);

        // Defina dinamicamente a cor do texto do NavigationView com base no tema
        int textColor;
        if (isDarkTheme()) {
            textColor = ContextCompat.getColor(this, R.color.cinza_escuro);
        } else {
            textColor = ContextCompat.getColor(this, R.color.cinza_claro);
        }

        ColorStateList textColorStateList = ColorStateList.valueOf(textColor);
        navigationView.setItemTextColor(textColorStateList);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.na_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home_teste()).commit();
                break;

            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new testefragmenttt()).commit();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private boolean isDarkTheme() {
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES; */
    }
}
