package com.example.cad_login_fb;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class feedback_geral extends DialogFragment implements View.OnClickListener {

    public feedback_geral() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback_geral, container, false);

        Button btnOpenRateUs = view.findViewById(R.id.btnOpenRateUs);
        Button btnOpenFeedback = view.findViewById(R.id.btnOpenFeedback);

        btnOpenRateUs.setOnClickListener(this);
        btnOpenFeedback.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOpenRateUs:
                openDialogFragment(new RateUsDialogFragment());
                break;
            case R.id.btnOpenFeedback:
                openFragment(new Fragment_Feedback());
                break;
        }
    }

    private void openDialogFragment(DialogFragment fragment) {
        if (getFragmentManager() != null) {
            fragment.show(getFragmentManager(), fragment.getClass().getSimpleName());
        }
    }

    private void openFragment(Fragment fragment) {
        if (getFragmentManager() != null) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
