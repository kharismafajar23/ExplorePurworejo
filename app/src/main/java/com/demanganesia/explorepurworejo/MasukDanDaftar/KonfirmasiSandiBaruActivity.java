package com.demanganesia.explorepurworejo.MasukDanDaftar;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.demanganesia.explorepurworejo.HelperClasses.NoInternetDialog;
import com.demanganesia.explorepurworejo.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class KonfirmasiSandiBaruActivity extends AppCompatActivity {

    TextInputLayout ETkataSandiBaru, ETkonfirmasiKataSandiBaru;
    String _username, _nomorTelefon, _kataSandiBaru, _konfirmasiKataSandiBaru;

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

        //cek koneksi internet
        if (!cekInternet()) {

            //show dialog
            NoInternetDialog noInternetDialog = new NoInternetDialog(KonfirmasiSandiBaruActivity.this);
            noInternetDialog.setCancelable(false);
            noInternetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
            noInternetDialog.show();
        }

        //validasi form input
        if (!validasiSandiBaru() | !validasiKonfirmasiSandiBaru()){
            return;
        }

        //get data dari input
        _kataSandiBaru = ETkataSandiBaru.getEditText().getText().toString().trim();
        _konfirmasiKataSandiBaru = ETkonfirmasiKataSandiBaru.getEditText().getText().toString().trim();

        //update kata sandi di database
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(_username).child("kata_sandi").setValue(_kataSandiBaru);

        startActivity(new Intent(getApplicationContext(), SuksesUpdateSandiActivity.class));
        finish();
    }

    //validasi form kata sandi baru
    private boolean validasiSandiBaru() {
        String val = ETkataSandiBaru.getEditText().getText().toString().trim();
        String cekKataSandi = "^"+
                "(?=.*[a-zA-Z])"+   //semua karakter diperbolehkan
                "(?=\\S+$)"+        //tidak boleh ada spasi
                ".{4,}"+            //harus berisi minimal 4 karakter
                "$";

        if (val.isEmpty()) {
            ETkataSandiBaru.setError("Bagian ini tidak boleh kosong");
            return false;
        } else if(!val.matches(cekKataSandi)) {
            ETkataSandiBaru.setError("Tidak boleh ada spasi dan minimal berisi 4 karakter");
            return false;
        }
        else {
            ETkataSandiBaru.setError(null);
            ETkataSandiBaru.setErrorEnabled(false);
            return true;
        }
    }

    //validasi form konfirmasi kata sandi
    private boolean validasiKonfirmasiSandiBaru() {
        String val = ETkonfirmasiKataSandiBaru.getEditText().getText().toString().trim();
        String kataSandiBaru = ETkataSandiBaru.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            ETkonfirmasiKataSandiBaru.setError("Bagian ini tidak boleh kosong");
            return false;
        } else if (!val.matches(kataSandiBaru)) {
            ETkonfirmasiKataSandiBaru.setError("Kata sandi yang dimasukkan tidak sama");
            return false;
        }
        else {
            ETkonfirmasiKataSandiBaru.setError(null);
            ETkonfirmasiKataSandiBaru.setErrorEnabled(false);
            return true;
        }
    }

    private boolean cekInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}