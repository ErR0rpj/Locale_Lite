package com.example.locale_lite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class Register_provider extends AppCompatActivity {

    private Toolbar toolbar;
    private Spinner SPINgender;
    private Spinner SPINservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_provider);

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
    }
}
