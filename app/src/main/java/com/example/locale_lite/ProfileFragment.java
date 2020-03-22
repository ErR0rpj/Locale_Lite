package com.example.locale_lite;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.Objects;

public class ProfileFragment extends Fragment {
    TextView TVprofile_pe_name,TVprofile_pe_phone,TVlogout,TVprovide_service;
    private View mProgressView;
    private View mLoginFormView;
    private TextView tvLoad;
    Toolbar toolbar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_profile,container,false);
        TVprofile_pe_name=view.findViewById(R.id.TVprofile_pe_name);
        TVprofile_pe_phone=view.findViewById(R.id.TVprofile_pe_phone);
        mLoginFormView=view.findViewById(R.id.login_form);
        mProgressView=view.findViewById(R.id.login_progress);
        tvLoad=view.findViewById(R.id.tvLoad);
        TVlogout=view.findViewById(R.id.TVlogout);
        TVprovide_service=view.findViewById(R.id.TVprovide_service);

        BackendlessUser user = Backendless.UserService.CurrentUser();
        String email = (String)(user.getProperty("name"));
        TVprofile_pe_name.setText(email);
        String phone= (String)(user.getProperty("phone"));
        TVprofile_pe_phone.setText(phone);

        TVprovide_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),com.example.locale_lite.Register_provider.class);
                startActivity(intent);
            }
        });

        TVlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //showProgress(true);
                //tvLoad.setText("Logging Out");

                Backendless.UserService.logout(new AsyncCallback<Void>() {
                    @Override
                    public void handleResponse(Void response) {
                        Toast.makeText(getActivity(),"Logged out",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(),com.example.locale_lite.Loginmain.class));
                        getActivity().finish();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(getActivity(),"Error:"+fault.getMessage(),Toast.LENGTH_LONG).show();
                        //showProgress(false);
                    }
                });
            }
        });
        return view;
    }

    //this is not working now.
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2){
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            mLoginFormView.setVisibility(show? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                        }
                    });
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            tvLoad.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
