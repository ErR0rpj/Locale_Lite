package com.example.locale_lite;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
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
import com.backendless.async.callback.Fault;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.local.UserIdStorageFactory;

public class Loginmain extends AppCompatActivity {

    private View mProgressView;
    private View mLoginFormView;
    private TextView tvLoad;
    EditText ETemail,ETpassword;
    Button BTNlogin;
    TextView TVregister, TVforgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginmain);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        tvLoad = findViewById(R.id.tvLoad);
        ETemail= findViewById(R.id.ETemail);
        ETpassword= findViewById(R.id.ETpassword);
        BTNlogin= findViewById(R.id.BTNlogin);
        TVforgot= findViewById(R.id.TVforgot);
        TVregister= findViewById(R.id.TVregister);

        TVforgot.setVisibility(View.GONE);

        BTNlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ETemail.getText().toString().isEmpty() || ETpassword.getText().toString().isEmpty()){
                    Toast.makeText(Loginmain.this,"Enter all the fields",Toast.LENGTH_SHORT).show();
                }

                else{
                    String email=ETemail.getText().toString().trim();
                    String password=ETpassword.getText().toString().trim();

                    showProgress(true);
                    tvLoad.setText("Logging in");

                    Backendless.UserService.login(email, password, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {
                            Toast.makeText(Loginmain.this,"Login Successfully",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Loginmain.this,com.example.locale_lite.MainActivity.class));
                            Loginmain.this.finish();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            TVforgot.setVisibility(View.VISIBLE);
                            Toast.makeText(Loginmain.this,"Error:"+fault.getMessage(),Toast.LENGTH_LONG).show();
                            showProgress(false);
                        }
                    },true);
                }
            }
        });

        TVregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Loginmain.this,com.example.locale_lite.Register.class);
                startActivity(intent);
            }
        });

        TVforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ETemail.getText().toString().isEmpty()){
                    Toast.makeText(Loginmain.this,"Enter Email to reset password",Toast.LENGTH_LONG).show();
                }
                else{
                    String email=ETemail.getText().toString().trim();
                    showProgress(true);
                    Backendless.UserService.restorePassword(email, new AsyncCallback<Void>() {
                        @Override
                        public void handleResponse(Void response) {
                            showProgress(false);
                            Toast.makeText(Loginmain.this,"Instruction to reset password sent to Email address",Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            showProgress(false);
                            Toast.makeText(Loginmain.this,"Error:"+fault.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        showProgress(true);

        Backendless.UserService.isValidLogin(new AsyncCallback<Boolean>() {
            @Override
            public void handleResponse(Boolean response) {
                if(response){
                    String userObjectId= UserIdStorageFactory.instance().getStorage().get();
                    Backendless.Data.of(BackendlessUser.class).findById(userObjectId, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {
                            startActivity(new Intent(Loginmain.this,com.example.locale_lite.MainActivity.class));
                            Loginmain.this.finish();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            showProgress(false);
                            Toast.makeText(Loginmain.this, "Error:"+fault.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                else{
                    showProgress(false);
                }
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                showProgress(false);
                Toast.makeText(Loginmain.this,"Error:"+fault.getMessage(),Toast.LENGTH_LONG).show();
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
