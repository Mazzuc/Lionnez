package com.example.cad_login_fb;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View page, float position) {
        page.setCameraDistance(20000);

        if (position < -1) {  // Páginas à esquerda da tela
            page.setAlpha(0);
        } else if (position <= 0) {  // [-1, 0]
            page.setAlpha(1);
            page.setPivotX(page.getWidth());
            page.setRotationY(90 * Math.abs(position));
        } else if (position <= 1) {  // (0, 1]
            page.setAlpha(1);
            page.setPivotX(0);
            page.setRotationY(-90 * Math.abs(position));
        } else {  // Páginas à direita da tela
            page.setAlpha(0);
        }
    }
}