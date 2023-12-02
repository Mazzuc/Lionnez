package com.example.cad_login_fb;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

public class RateUsDialogFragment extends DialogFragment implements BaseFragment {

    private float userRate = 0;

    public RateUsDialogFragment() {
        // Construtor padrão é necessário para Fragments
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog, container, false);

        final AppCompatButton rateNowBtn = view.findViewById(R.id.rateNowBtn);
        final ImageView ratingImage = view.findViewById(R.id.ratingImage);
        final AppCompatButton laterBtn = view.findViewById(R.id.laterBtn);
        final RatingBar ratingBar = view.findViewById(R.id.ratingBar);

        rateNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lógica para ação do botão Rate Now

                // Show custom toast
                showCustomToast("Agradecemos pelo Feedback!");
            }
        });

        laterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lógica para ação do botão Later
                dismiss();
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating <= 1) {
                    ratingImage.setImageResource(R.drawable.one_star);
                } else if (rating <= 2) {
                    ratingImage.setImageResource(R.drawable.two_star);
                } else if (rating <= 3) {
                    ratingImage.setImageResource(R.drawable.three_star);
                } else if (rating <= 4) {
                    ratingImage.setImageResource(R.drawable.four_star);
                } else if (rating <= 5) {
                    ratingImage.setImageResource(R.drawable.five_star);
                }

                animateImage(ratingImage);

                userRate = rating;
            }
        });

        // Configurando o botão de fechar
        setupCloseButton(view);

        return view;
    }

    private void animateImage(ImageView ratingImage) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1f, 0, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(200);
        ratingImage.startAnimation(scaleAnimation);
    }

    private void setupCloseButton(View view) {
        Button btnClose = view.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lógica para ação do botão Close
                dismiss();
            }
        });
    }

    private void showCustomToast(String message) {
        // Inflate the custom toast layout
        View toastView = getLayoutInflater().inflate(R.layout.activity_custon_toast_correct, null);

        // Set the message in the TextView
        TextView textCarregarResultado = toastView.findViewById(R.id.TextCarregarResultado);
        textCarregarResultado.setText(message);

        // Create and show the toast
        Toast toast = new Toast(getActivity());
        toast.setDuration(Toast.LENGTH_LONG); // Adjust the duration as needed
        toast.setView(toastView);
        toast.show();
    }
}


/*
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;


public class RateUsDialogFragment extends DialogFragment implements BaseFragment {

    private float userRate = 0;

    public RateUsDialogFragment() {
        // Construtor padrão é necessário para Fragments
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog, container, false);

        final AppCompatButton rateNowBtn = view.findViewById(R.id.rateNowBtn);
        final ImageView ratingImage = view.findViewById(R.id.ratingImage);
        final AppCompatButton laterBtn = view.findViewById(R.id.laterBtn);
        final RatingBar ratingBar = view.findViewById(R.id.ratingBar);

        rateNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lógica para ação do botão Rate Now
            }
        });

        laterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lógica para ação do botão Later
                dismiss();
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating <= 1) {
                    ratingImage.setImageResource(R.drawable.one_star);
                } else if (rating <= 2) {
                    ratingImage.setImageResource(R.drawable.two_star);
                } else if (rating <= 3) {
                    ratingImage.setImageResource(R.drawable.three_star);
                } else if (rating <= 4) {
                    ratingImage.setImageResource(R.drawable.four_star);
                } else if (rating <= 5) {
                    ratingImage.setImageResource(R.drawable.five_star);
                }

                animateImage(ratingImage);

                userRate = rating;
            }
        });

        // Configurando o botão de fechar
        setupCloseButton(view);

        return view;
    }

    private void animateImage(ImageView ratingImage) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1f, 0, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(200);
        ratingImage.startAnimation(scaleAnimation);
    }

    private void setupCloseButton(View view) {
        Button btnClose = view.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lógica para ação do botão Close
                dismiss();
            }
        });
    }
}
*/