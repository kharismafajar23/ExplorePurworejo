package com.demanganesia.explorepurworejo.MasukDanDaftar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.demanganesia.explorepurworejo.R;

public class Daftar3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar3);
    }

    public void keMasukc(View view) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    public void keVerifikasi(View view) {
        startActivity(new Intent(getApplicationContext(), VerifikasiActivity.class));
    }
}