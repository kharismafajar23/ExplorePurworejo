package com.demanganesia.explorepurworejo.MasukDanDaftar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.demanganesia.explorepurworejo.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class LupaKataSandiActivity extends AppCompatActivity {

    CountryCodePicker kodeNegara;
    TextInputLayout ETusername, EtnomorTelefon;
    String _username, _inputNomorTelefon, _nomorTelefon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_kata_sandi);
        //menghilangkan status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        kodeNegara = findViewById(R.id.input_kode_negara);
        ETusername = findViewById(R.id.ETusername);
        EtnomorTelefon = findViewById(R.id.ETnomor_telefon);

    }

    public void keVerifikasiLupaSandi(View view) {

        //get data
        _username = ETusername.getEditText().getText().toString().trim();
        _inputNomorTelefon = EtnomorTelefon.getEditText().getText().toString().trim();
        _nomorTelefon = "+"+kodeNegara.getSelectedCountryCode()+_inputNomorTelefon;

        //cek user di database
        Query cekUser = FirebaseDatabase.getInstance().getReference("Users").child(_username);
        cekUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    //ambil nomor telefon dari database
                    String nomorTelefonDariDatabase = dataSnapshot.child("nomor_telefon").getValue().toString();

                    //validasi nomor telefon
                    if (_nomorTelefon.equals(nomorTelefonDariDatabase)) {
                        //ke verifikasi
                        EtnomorTelefon.setError(null);
                        EtnomorTelefon.setErrorEnabled(false);

                        Intent intent = new Intent(getApplicationContext(), VerifikasiLupaSandiActivity.class);
                        intent.putExtra("username", _username);
                        intent.putExtra("nomorTelefon", _nomorTelefon);
                        intent.putExtra("whatTodo", "updateData");
                        startActivity(intent);
                        finish();

                        //nomor telefon tidak ada
                    } else {
                        ETusername.setError(null);
                        ETusername.setErrorEnabled(false);
                        EtnomorTelefon.setError("Nomor telefon tidak terdaftar");
                        EtnomorTelefon.requestFocus();
                    }

                    //username tidak ada
                } else {
                    ETusername.setError("Username tidak terdaftar");
                    ETusername.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}