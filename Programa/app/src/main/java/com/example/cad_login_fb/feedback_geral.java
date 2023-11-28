package com.example.cad_login_fb;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
public class feedback_geral extends Fragment {

    public feedback_geral() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback_geral, container, false);

        Button btnOpenFragment1 = view.findViewById(R.id.btnOpenFragment1);
        Button btnOpenFragment2 = view.findViewById(R.id.btnOpenFragment2);

        if (btnOpenFragment1 != null) {
            btnOpenFragment1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openFragment(new RateUsDialogFragment());
                }
            });
        }

        if (btnOpenFragment2 != null) {
            btnOpenFragment2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openFragment(new Fragment_Feedback());
                }
            });
        }

        return view;
    }

    public void openFragment(Fragment fragment) {
        if (fragment instanceof BaseFragment) {
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
