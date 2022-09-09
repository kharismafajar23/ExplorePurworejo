package com.demanganesia.explorepurworejo.MasukDanDaftar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.demanganesia.explorepurworejo.R;

public class LupaKataSandi2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_kata_sandi2);
        //menghilangkan status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void keVerifikasiLupaSandiViaSMS(View view) {
        startActivity(new Intent(getApplicationContext(), VerifikasiLupaSandiActivity.class));
    }

    public void keVerifikasiLupaSandiViaEmail(View view) {
        startActivity(new Intent(getApplicationContext(), VerifikasiLupaSandiActivity.class));
    }
}