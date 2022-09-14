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

    //meunju verifikasi klik button daftar
    public void keVerifikasi(View view) {
        if (!validasiFormNomorTelepon()) {
            return;
        }

        String _namaLengkap = getIntent().getStringExtra("namaLengkap");
        String _username = getIntent().getStringExtra("username");
        String _email = getIntent().getStringExtra("email");
        String _kataSandi = getIntent().getStringExtra("kataSandi");
        String _tanggalLahir = getIntent().getStringExtra("tanggalLahir");
        String _jenisKelamin = getIntent().getStringExtra("jenisKelamin");

        String _inputNomorTelefon = ETNomorTelefon.getEditText().getText().toString().trim();
        String _nomorTelefon = "+"+InputKodeNegara.getSelectedCountryCode()+_inputNomorTelefon;

            Intent intent = new Intent(getApplicationContext(), VerifikasiDaftarActivity.class);

            intent.putExtra("namaLengkap", _namaLengkap);
            intent.putExtra("username", _username);
            intent.putExtra("email", _email);
            intent.putExtra("kataSandi", _kataSandi);
            intent.putExtra("tanggalLahir", _tanggalLahir);
            intent.putExtra("jenisKelamin", _jenisKelamin);
            intent.putExtra("nomorTelefonIni", _nomorTelefon);

        startActivity(intent);
        }
    }
