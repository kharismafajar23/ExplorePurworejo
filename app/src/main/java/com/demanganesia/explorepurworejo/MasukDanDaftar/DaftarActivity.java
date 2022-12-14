package com.demanganesia.explorepurworejo.MasukDanDaftar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.demanganesia.explorepurworejo.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DaftarActivity extends AppCompatActivity {

    TextInputLayout ETNamaLengkap, ETusername, ETemail, ETKataSandi;
    Button BtnDaftar;
    String _namaLengkap, _username, _email, _kataSandi;
    DatabaseReference database_reference_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        //menghilangkan status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ETNamaLengkap = findViewById(R.id.ETnama_lengkap);
        ETusername = findViewById(R.id.ETusername);
        ETemail = findViewById(R.id.ETemail);
        ETKataSandi = findViewById(R.id.ETkata_sandi);
        BtnDaftar = findViewById(R.id.btnDaftar);

    }
    //klik button daftar
    public void keDaftar2(View view) {

        if (!validasiFormNamaLengkap() | !validasiFormUsername() | !validasiFormEmail() | !validasiFormKataSandi()){
            return;
        } else {
            //mengambil username yang sudah ada
            database_reference_username = FirebaseDatabase.getInstance().getReference().child("Users").child(ETusername.getEditText().getText().toString());
            database_reference_username.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Toast.makeText(DaftarActivity.this, "Username sudah digunakan", Toast.LENGTH_SHORT).show();
                    } else {
                        _namaLengkap = ETNamaLengkap.getEditText().getText().toString().trim();
                        _username = ETusername.getEditText().getText().toString().trim();
                        _email = ETemail.getEditText().getText().toString().trim();
                        _kataSandi = ETKataSandi.getEditText().getText().toString().trim();

                        Intent intent = new Intent(DaftarActivity.this, Daftar2Activity.class);

                        intent.putExtra("namaLengkap", _namaLengkap);
                        intent.putExtra("username", _username);
                        intent.putExtra("email",_email);
                        intent.putExtra("kataSandi", _kataSandi);

                        startActivity(intent);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }

    //validasi form nama lengkap
    private boolean validasiFormNamaLengkap() {
        String val = ETNamaLengkap.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            ETNamaLengkap.setError("Bagian ini tidak boleh kosong");
            return false;
        } else {
            ETNamaLengkap.setError(null);
            ETNamaLengkap.setErrorEnabled(false);
            return true;
        }
    }

    //validasi form username
    private boolean validasiFormUsername() {
        String val = ETusername.getEditText().getText().toString().trim();
        String cekSpasi = "\\A\\w{1,20}\\z";

        if (val.isEmpty()) {
            ETusername.setError("Bagian ini tidak boleh kosong");
            return false;
        } else if(val.length()>20){
            ETusername.setError("Username terlalu panjang");
            return false;
        } else if(!val.matches(cekSpasi)) {
            ETusername.setError("Spasi tidak diperbolehkan");
            return false;
        }
        else {
            ETusername.setError(null);
            ETusername.setErrorEnabled(false);
            return true;
        }
    }

    //validasi form email
    private boolean validasiFormEmail() {
        String val = ETemail.getEditText().getText().toString().trim();
        String cekEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            ETemail.setError("Bagian ini tidak boleh kosong");
            return false;
        } else if(!val.matches(cekEmail)) {
            ETemail.setError("Masukkan format email dengan benar");
            return false;
        }
        else {
            ETemail.setError(null);
            ETemail.setErrorEnabled(false);
            return true;
        }
    }

    //validasi form kata sandi
    private boolean validasiFormKataSandi() {
        String val = ETKataSandi.getEditText().getText().toString().trim();
        String cekKataSandi = "^"+
                "(?=.*[a-zA-Z])"+   //semua karakter diperbolehkan
                "(?=\\S+$)"+        //tidak boleh ada spasi
                ".{4,}"+            //harus berisi minimal 4 karakter
                "$";

        if (val.isEmpty()) {
            ETKataSandi.setError("Bagian ini tidak boleh kosong");
            return false;
        } else if(!val.matches(cekKataSandi)) {
            ETKataSandi.setError("Tidak boleh ada spasi dan minimal berisi 4 karakter");
            return false;
        }
        else {
            ETKataSandi.setError(null);
            ETKataSandi.setErrorEnabled(false);
            return true;
        }
    }

    //klik ke masuk
    public void keMasuk(View view) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }
}