package com.example.cad_login_fb;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

public class testefragmenttt extends Fragment {

    private LinearLayout llContent;
    private boolean isContentVisible = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflando o layout do fragmento
        View view = inflater.inflate(R.layout.fragment_testefragmenttt, container, false);

        Button btnToggle = view.findViewById(R.id.btnToggle);
        llContent = view.findViewById(R.id.llContent);

        btnToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleContent();
            }
        });

        return view;
    }

    private void toggleContent() {
        if (isContentVisible) {
            slideOut(llContent);
        } else {
            slideIn(llContent);
        }
        isContentVisible = !isContentVisible;
    }

    private void slideIn(final View view) {
        view.setVisibility(View.VISIBLE);
        Animation animate = new TranslateAnimation(-view.getWidth(), 0, 0, 0);
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    private void slideOut(final View view) {
        Animation animate = new TranslateAnimation(0, -view.getWidth(), 0, 0);
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.INVISIBLE);
    }
}
