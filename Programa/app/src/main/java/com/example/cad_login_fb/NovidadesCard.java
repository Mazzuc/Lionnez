package com.example.cad_login_fb;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NovidadesCard extends LinearLayout {
    private TextView titleTextView;
    private TextView infoTextView;
    private ImageView novidadesImageView;
    private LinearLayout infoButton;

    public NovidadesCard(Context context, String title, String info, int imageResId) {
        super(context);
        init(context, title, info, imageResId);
    }

    private void init(Context context, String title, String info, int imageResId) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.card_novidades_layout, this, true);

        titleTextView = rootView.findViewById(R.id.txttitle);
        infoTextView = rootView.findViewById(R.id.txtinfo);
        novidadesImageView = rootView.findViewById(R.id.image_novidades);
        infoButton = rootView.findViewById(R.id.buttoncard1);

        titleTextView.setText(title);
        infoTextView.setText(info);
        novidadesImageView.setImageResource(imageResId);

        infoButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (title.equals("Primata Marrom")) {
                    Intent intent = new Intent(getContext(), HomeActivity.class);
                    getContext().startActivity(intent);
                } else if (title.equals("Primata Marrom")) {
                    Intent intent = new Intent(getContext(), HomeActivity.class);
                    getContext().startActivity(intent);
                }
            }
        });
    }
}

