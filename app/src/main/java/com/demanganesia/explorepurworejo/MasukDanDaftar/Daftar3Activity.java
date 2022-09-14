package com.demanganesia.explorepurworejo.MasukDanDaftar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.demanganesia.explorepurworejo.R;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

public class Daftar3Activity extends AppCompatActivity {

    TextInputLayout ETNomorTelefon;
    CountryCodePicker InputKodeNegara;
    String _namaLengkap, _username, _email, _kataSandi, _tanggalLahir, _jenisKelamin, _inputNomorTelefon, _nomorTelefon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar3);
        //menghilangkan status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ETNomorTelefon = findViewById(R.id.ETnomor_telefon);
        InputKodeNegara = findViewById(R.id.Inputkode_negara);
    }

    //validasi form nomor telefon
    private boolean validasiFormNomorTelepon() {
        String val = ETNomorTelefon.getEditText().getText().toString().trim();
        String cekSpasi = "\\A\\w{1,20}\\z";

        if (val.isEmpty()) {
            ETNomorTelefon.setError("Bagian ini tidak boleh kosong");
            return false;
        } else if(!val.matches(cekSpasi)) {
            ETNomorTelefon.setError("Spasi tidak diperbolehkan");
            return false;
        }
        else {
            ETNomorTelefon.setError(null);
            ETNomorTelefon.setErrorEnabled(false);
            return true;
        }
    }

    public void keMasukc(View view) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    //menuju verifikasi klik button daftar
    public void keVerifikasi(View view) {
        if (!validasiFormNomorTelepon()) {
            return;
        }

        _namaLengkap = getIntent().getStringExtra("namaLengkap");
        _username = getIntent().getStringExtra("username");
        _email = getIntent().getStringExtra("email");
        _kataSandi = getIntent().getStringExtra("kataSandi");
        _tanggalLahir = getIntent().getStringExtra("tanggalLahir");
        _jenisKelamin = getIntent().getStringExtra("jenisKelamin");

        _inputNomorTelefon = ETNomorTelefon.getEditText().getText().toString().trim();
        _nomorTelefon = "+"+InputKodeNegara.getSelectedCountryCode()+_inputNomorTelefon;

            Intent intent = new Intent(getApplicationContext(), VerifikasiDaftarActivity.class);

            intent.putExtra("namaLengkapIni", _namaLengkap);
            intent.putExtra("usernameIni", _username);
            intent.putExtra("emailIni", _email);
            intent.putExtra("kataSandiIni", _kataSandi);
            intent.putExtra("tanggalLahirIni", _tanggalLahir);
            intent.putExtra("jenisKelaminIni", _jenisKelamin);
            intent.putExtra("nomorTelefonIni", _nomorTelefon);

        startActivity(intent);
        }
    }
