package com.demanganesia.explorepurworejo.MasukDanDaftar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.demanganesia.explorepurworejo.R;
import com.google.android.material.textfield.TextInputLayout;

public class Daftar3Activity extends AppCompatActivity {

    TextInputLayout ETNomorTelefon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar3);
        //menghilangkan status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ETNomorTelefon = findViewById(R.id.ETnomor_telefon);

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
        } else {
            startActivity(new Intent(getApplicationContext(), VerifikasiDaftarActivity.class));
        }
    }
}