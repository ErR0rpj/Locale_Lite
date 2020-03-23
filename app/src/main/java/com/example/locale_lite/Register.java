package com.example.locale_lite;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class Register extends AppCompatActivity {

    private View mProgressView;
    private View mLoginFormView;
    private TextView tvLoad;
    EditText ETemail,ETpassword,ETname,ETconfirm,ETphone;
    Button BTNregister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        tvLoad = findViewById(R.id.tvLoad);
        ETemail= findViewById(R.id.ETemail);
        ETpassword= findViewById(R.id.ETpassword);
        ETname= findViewById(R.id.ETname);
        ETphone= findViewById(R.id.ETphone);
        ETconfirm= findViewById(R.id.ETconfirm);
        BTNregister= findViewById(R.id.BTNregister);

        BTNregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ETphone.getText().toString().isEmpty() || ETemail.getText().toString().isEmpty() || ETname.getText().toString().isEmpty() || ETpassword.getText().toString().isEmpty()){
                    Toast.makeText(Register.this,
                            "Enter all the fields",Toast.LENGTH_SHORT).show();
                }
                else if(ETpassword.getText().toString().trim().equals(ETconfirm.getText().toString().trim())){
                    String name= ETname.getText().toString();
                    String email=ETemail.getText().toString().trim();
                    String phone= ETphone.getText().toString().trim();
                    String password=ETpassword.getText().toString().trim();
                    String is_provider="false";

                    BackendlessUser user= new BackendlessUser();
                    user.setEmail(email);
                    user.setPassword(password);
                    user.setProperty("name",name);
                    user.setProperty("phone",phone);
                    user.setProperty("is_provider",is_provider);

                    showProgress(true);
                    tvLoad.setText("Registering");

                    Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {
                            showProgress(false);
                            Toast.makeText(Register.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            Register.this.finish();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault){
                            Toast.makeText(Register.this,"Error:"+fault.getMessage(),Toast.LENGTH_LONG).show();
                            showProgress(false);
                        }
                    });
                }
                else{
                    Toast.makeText(Register.this,"Password mismatch",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
