package com.example.locale_lite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;

import java.util.Objects;

public class ProfileFragment extends Fragment {
    TextView TVprofile_pe_name,TVprofile_pe_phone;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_profile,container,false);
        TVprofile_pe_name=view.findViewById(R.id.TVprofile_pe_name);
        TVprofile_pe_phone=view.findViewById(R.id.TVprofile_pe_phone);
        return view;
    }
}
