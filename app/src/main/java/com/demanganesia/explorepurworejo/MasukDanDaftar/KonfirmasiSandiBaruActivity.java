package com.demanganesia.explorepurworejo.MasukDanDaftar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.demanganesia.explorepurworejo.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class KonfirmasiSandiBaruActivity extends AppCompatActivity {

    TextInputLayout ETkataSandiBaru, ETkonfirmasiKataSandiBaru;
    String _username, _nomorTelefon, _kataSandiBaru;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi_sandi_baru);
        //menghilangkan status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ETkataSandiBaru = findViewById(R.id.ETkata_sandi_baru);
        ETkonfirmasiKataSandiBaru = findViewById(R.id.ETkonfirmasi_kata_sandi_baru);
        _username = getIntent().getStringExtra("username");
        _nomorTelefon = getIntent().getStringExtra("nomorTelefon");

    }

    public void keSuksesupdateSandi(View view) {

        //get data dari input
        _kataSandiBaru = ETkataSandiBaru.getEditText().getText().toString().trim();

        //update kata sandi di database
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(_username).child("kataSandi").setValue(_kataSandiBaru);

        startActivity(new Intent(getApplicationContext(), SuksesUpdateSandiActivity.class));
        finish();
    }
}