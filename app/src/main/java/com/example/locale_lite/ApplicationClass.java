package com.example.locale_lite;

import android.app.Application;

import com.backendless.Backendless;

public class ApplicationClass extends Application {
    public static final String APPLICATION_ID="303AAD20-CDF8-B27D-FFCE-087E926A3C00";
    public static final String API_KEY="ECE74612-A522-4D0B-A7A5-4DCBE4471AB8";
    public static final String SERVER_URL="https://api.backendless.com";

    @Override
    public void onCreate(){
        super.onCreate();
        Backendless.setUrl(SERVER_URL);
        Backendless.initApp( getApplicationContext(),
                APPLICATION_ID,
                API_KEY );
    }
}
