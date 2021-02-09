package com.example.ansengas.Ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ansengas.R;

public class splashScreen extends AppCompatActivity {

    private static int splash_time_out = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(splashScreen.this, MainActivity.class);
                startActivity(homeIntent);
                finish();
            }
        }, splash_time_out);
    }
}