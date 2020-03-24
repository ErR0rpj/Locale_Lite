package com.example.locale_lite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.ArrayList;
import java.util.List;

// Database_service is used in this.
public class Register_provider extends AppCompatActivity {

    private View mProgressView;
    private View mRegFormView;
    private TextView tvLoad;
    private Toolbar toolbar;
    private Spinner SPINgender;
    private Spinner SPINservice;
    Button BTNregisters;
    EditText ETbusiadd, ETcity;
    String name,email,business_address,gender,phone,service,city,is_provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_provider);

        mRegFormView = findViewById(R.id.reg_form);
        mProgressView = findViewById(R.id.login_progress);
        tvLoad = findViewById(R.id.tvLoad);
        BTNregisters= findViewById(R.id.BTNregisters);
        ETbusiadd= findViewById(R.id.ETbusiadd);
        ETcity= findViewById(R.id.ETcity);

        toolbar= findViewById(R.id.toolbar);
        // Below 4 lines to give back button and to back.
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        SPINgender= findViewById(R.id.SPINgender);
        List<Gender> genderList= new ArrayList<>();
        Gender ge0= new Gender("Select your Gender");
        genderList.add(ge0);
        Gender ge1= new Gender("Male");
        genderList.add(ge1);
        Gender ge2= new Gender("Female");
        genderList.add(ge2);
        Gender ge3= new Gender("Others");
        genderList.add(ge3);

        ArrayAdapter<Gender> adaptergender=new ArrayAdapter<Gender>(this,
                android.R.layout.simple_spinner_item,genderList);
        adaptergender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SPINgender.setAdapter(adaptergender);

        SPINservice= findViewById(R.id.SPINservice);
        List<Service> serviceList= new ArrayList<>();
        Service ser0= new Service("Select your Business");
        serviceList.add(ser0);
        Service ser1= new Service("Parlour");
        serviceList.add(ser1);
        Service ser2=new Service("Home Teacher");
        serviceList.add(ser2);
        Service ser3=new Service("Massager");
        serviceList.add(ser3);
        Service ser4=new Service("Vehicle repair");
        serviceList.add(ser4);
        Service ser5=new Service("Chef");
        serviceList.add(ser5);
        Service ser6=new Service("Delivery");
        serviceList.add(ser6);
        Service ser7=new Service("AC service");
        serviceList.add(ser7);
        Service ser8=new Service("Electrician");
        serviceList.add(ser8);
        Service ser9=new Service("Plumber");
        serviceList.add(ser9);
        Service ser10=new Service("Carpenter");
        serviceList.add(ser10);
        Service ser11=new Service("Cleaner");
        serviceList.add(ser11);

        ArrayAdapter<Service> adapterservice=new ArrayAdapter<Service>(this,
                android.R.layout.simple_spinner_item,serviceList);
        adaptergender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SPINservice.setAdapter(adapterservice);

        BTNregisters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Service service_class = (Service) SPINservice.getSelectedItem();
                service=service_class.service;
                Gender gender_class = (Gender) SPINgender.getSelectedItem();
                gender= gender_class.gender;

                if(service.equals("Select your Business") || gender.equals("Select your Gender")
                        || ETbusiadd.getText().toString().isEmpty()
                        || ETcity.getText().toString().isEmpty()){
                    Toast.makeText(Register_provider.this,"Please, Enter all the fields",
                            Toast.LENGTH_SHORT).show();
                }

                else if(((String)(ApplicationClass.user.getProperty("is_provider"))).equals("true"))
                {
                    Toast.makeText(Register_provider.this,
                            "User already Registered",Toast.LENGTH_SHORT).show();
                }

                else{
                    is_provider="true";
                    name=(String)(ApplicationClass.user.getProperty("name"));
                    email=ApplicationClass.user.getEmail();
                    phone=(String)(ApplicationClass.user.getProperty("phone"));
                    business_address= ETbusiadd.getText().toString();
                    city= ETcity.getText().toString();

                    Database_service database_service= new Database_service();
                    database_service.setBusiness_address(business_address);
                    database_service.setCity(city);
                    database_service.setEmail(email);
                    database_service.setName(name);
                    database_service.setPhone(phone);
                    database_service.setGender(gender);
                    database_service.setService(service);

                    ApplicationClass.user.setProperty("is_provider",is_provider);
                    showProgress(true);

                    Backendless.UserService.update(ApplicationClass.user,
                            new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {
                            is_provider="true";
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            showProgress(false);
                            Toast.makeText(Register_provider.this,
                                    "Error:"+fault.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });

                    // This is used to create new database in Backendless.
                    Backendless.Persistence.save(database_service,
                            new AsyncCallback<Database_service>() {
                        @Override
                        public void handleResponse(Database_service response) {
                            showProgress(false);
                            // Remove below line after creating home provider page.
                            Toast.makeText(Register_provider.this,
                                    "Welcome to Locale-Lite family",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            showProgress(false);
                            Toast.makeText(Register_provider.this,
                                    "Error:"+fault.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            mRegFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mRegFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
