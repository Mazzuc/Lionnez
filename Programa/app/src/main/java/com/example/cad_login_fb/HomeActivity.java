package com.example.cad_login_fb;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    //Menu
    private int selectedTab = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Menu -- LinearLayout
        final LinearLayout homeLayout = findViewById(R.id.homelayout);
        final LinearLayout pesquisaLayout = findViewById(R.id.pesquisalayout);
        final LinearLayout scannerLayout = findViewById(R.id.scannerlayout);
        final LinearLayout mapaLayout = findViewById(R.id.mapalayout);
        final LinearLayout albumLayout = findViewById(R.id.albumlayout);

        //Menu -- ImageView
        final ImageView homeImage = findViewById(R.id.homeImage);
        final ImageView pesquisaImage = findViewById(R.id.pesquisaImage);
        final ImageView scannerImage = findViewById(R.id.scannerImage);
        final ImageView mapaImage = findViewById(R.id.mapaImage);
        final ImageView albumImage = findViewById(R.id.albumImage);

        //Menu -- TextView
        final TextView homeTxt = findViewById(R.id.homeTxt);
        final TextView pesquisaTxt = findViewById(R.id.pesquisaTxt);
        final TextView scannerTxt = findViewById(R.id.scannerTxt);
        final TextView mapaTxt = findViewById(R.id.mapaTxt);
        final TextView albumTxt = findViewById(R.id.albumTxt);

        //set home
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainer, HomeFragment.class, null)
                .commit();
        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedTab != 1){
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, HomeFragment.class, null)
                            .commit();

                    pesquisaTxt.setVisibility(View.GONE);
                    scannerTxt.setVisibility(View.GONE);
                    mapaTxt.setVisibility(View.GONE);
                    albumTxt.setVisibility(View.GONE);

                    pesquisaImage.setImageResource(R.drawable.icon_search);
                    scannerImage.setImageResource(R.drawable.icon_scanner);
                    mapaImage.setImageResource(R.drawable.icon_mapa);
                    albumImage.setImageResource(R.drawable.icon_book);

                    pesquisaLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    scannerLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    mapaLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    albumLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    homeTxt.setVisibility(View.VISIBLE);
                    homeImage.setImageResource(R.drawable.icon_selected_home);
                    homeLayout.setBackgroundResource(R.drawable.round_back_icon);

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    homeLayout.startAnimation(scaleAnimation);

                    selectedTab = 1;
                }
            }
        });

        pesquisaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(selectedTab != 2){

                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, PesquisaFragment.class, null)
                            .commit();

                    homeTxt.setVisibility(View.GONE);
                    scannerTxt.setVisibility(View.GONE);
                    mapaTxt.setVisibility(View.GONE);
                    albumTxt.setVisibility(View.GONE);

                    homeImage.setImageResource(R.drawable.icon_home);
                    scannerImage.setImageResource(R.drawable.icon_scanner);
                    mapaImage.setImageResource(R.drawable.icon_mapa);
                    albumImage.setImageResource(R.drawable.icon_book);

                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    scannerLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    mapaLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    albumLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    pesquisaTxt.setVisibility(View.VISIBLE);
                    pesquisaImage.setImageResource(R.drawable.icon_selected_search);
                    pesquisaLayout.setBackgroundResource(R.drawable.round_back_icon);

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    pesquisaLayout.startAnimation(scaleAnimation);

                    selectedTab = 2;
                }
            }
        });

        scannerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(selectedTab != 3){
                    homeTxt.setVisibility(View.GONE);
                    pesquisaTxt.setVisibility(View.GONE);
                    mapaTxt.setVisibility(View.GONE);
                    albumTxt.setVisibility(View.GONE);

                    homeImage.setImageResource(R.drawable.icon_home);
                    pesquisaImage.setImageResource(R.drawable.icon_search);
                    mapaImage.setImageResource(R.drawable.icon_mapa);
                    albumImage.setImageResource(R.drawable.icon_book);

                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    pesquisaLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    mapaLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    albumLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    scannerTxt.setVisibility(View.VISIBLE);
                    scannerImage.setImageResource(R.drawable.icon_selected_scanner);
                    scannerLayout.setBackgroundResource(R.drawable.round_back_icon);

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    scannerLayout.startAnimation(scaleAnimation);

                    selectedTab = 3;
                }
            }
        });

        mapaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedTab != 4){
                    homeTxt.setVisibility(View.GONE);
                    pesquisaTxt.setVisibility(View.GONE);
                    scannerTxt.setVisibility(View.GONE);
                    albumTxt.setVisibility(View.GONE);

                    homeImage.setImageResource(R.drawable.icon_home);
                    pesquisaImage.setImageResource(R.drawable.icon_search);
                    scannerImage.setImageResource(R.drawable.icon_scanner);
                    albumImage.setImageResource(R.drawable.icon_book);

                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    pesquisaLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    scannerLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    albumLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    mapaTxt.setVisibility(View.VISIBLE);
                    mapaImage.setImageResource(R.drawable.icon_selected_mapa);
                    mapaLayout.setBackgroundResource(R.drawable.round_back_icon);

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    mapaLayout.startAnimation(scaleAnimation);

                    selectedTab = 4;
                }
            }
        });

        albumLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedTab != 5){
                    homeTxt.setVisibility(View.GONE);
                    pesquisaTxt.setVisibility(View.GONE);
                    scannerTxt.setVisibility(View.GONE);
                    mapaTxt.setVisibility(View.GONE);

                    homeImage.setImageResource(R.drawable.icon_home);
                    pesquisaImage.setImageResource(R.drawable.icon_search);
                    scannerImage.setImageResource(R.drawable.icon_scanner);
                    mapaImage.setImageResource(R.drawable.icon_mapa);

                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    pesquisaLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    scannerLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    mapaLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    albumTxt.setVisibility(View.VISIBLE);
                    albumImage.setImageResource(R.drawable.icon_selected_book);
                    albumLayout.setBackgroundResource(R.drawable.round_back_icon);

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    albumLayout.startAnimation(scaleAnimation);

                    selectedTab = 5;
                }
            }
        });
    }
}