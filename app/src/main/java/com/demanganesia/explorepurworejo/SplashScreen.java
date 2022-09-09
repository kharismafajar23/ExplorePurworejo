package com.demanganesia.explorepurworejo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.demanganesia.explorepurworejo.MasukDanDaftar.LoginActivity;

public class SplashScreen extends AppCompatActivity {

    private int waktu_loading = 2500;

    SharedPreferences onBoardingScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //menghilangkan status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //final Handler handler = new Handler();
        //handler.postDelayed(new Runnable() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                onBoardingScreen = getSharedPreferences("onBoardingScreen", MODE_PRIVATE);
                boolean isFirstTime = onBoardingScreen.getBoolean("firstTime", true);

                if(isFirstTime){

                    SharedPreferences.Editor editor = onBoardingScreen.edit();
                    editor.putBoolean("firstTime", false);
                    editor.commit();

                    Intent intent = new Intent(getApplicationContext(), OnBoardActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Intent keMain = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(keMain);
                    finish();
                }
            }
        }, waktu_loading);
    }
}