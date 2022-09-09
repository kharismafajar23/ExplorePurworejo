package com.demanganesia.explorepurworejo.MasukDanDaftar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.demanganesia.explorepurworejo.R;

public class Daftar2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar2);
    }

    public void keDaftar3(View view) {
        startActivity(new Intent(getApplicationContext(), Daftar3Activity.class));
    }

    public void keMasukb(View view) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }
}