package com.demanganesia.explorepurworejo.MasukDanDaftar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.demanganesia.explorepurworejo.HelperClasses.NoInternetDialog;
import com.demanganesia.explorepurworejo.MainActivity;
import com.demanganesia.explorepurworejo.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout ETUsername, ETKataSandi;
    TextInputEditText ETUsername2, ETKataSandi2;
    Button btnMasuk;
    DatabaseReference reference;
    CheckBox cekBoxIngatsaya;

    String USERNAME_KEY_LOCAL = "usernamekeylocal";
    String username_key_local = "";
    String username_key_local2 = "";
    String username_key_new_local = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //menghilangkan status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ETUsername = findViewById(R.id.ETusername);
        ETKataSandi = findViewById(R.id.ETkata_sandi);
        btnMasuk = findViewById(R.id.BtnMasuk);
        cekBoxIngatsaya = findViewById(R.id.ingat_saya);
        ETUsername2 = findViewById(R.id.ETusername2);
        ETKataSandi2 = findViewById(R.id.ETkata_sandi2);

        //langsung ke main act jika sudah login
        SharedPreferences preferences = getSharedPreferences("cekBox", MODE_PRIVATE);
        String cekBox = preferences.getString("cekIngatSaya", "");

        if(cekBox.equals("true")){
            Intent langsungKeMain = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(langsungKeMain);
        } else if (cekBox.equals("false")) {
        }

        //mengingat username dan kata sandi
        SharedPreferences sharedPreferences=getSharedPreferences("dataIngatSaya",MODE_PRIVATE);
        String username = sharedPreferences.getString("username","");
        String kataSandi = sharedPreferences.getString("kataSandi","");

        ETUsername2.setText(username);
        ETKataSandi2.setText(kataSandi);

        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //cek koneksi internet
                if (!cekInternet()) {

                    //show dialog
                    NoInternetDialog noInternetDialog = new NoInternetDialog(LoginActivity.this);
                    noInternetDialog.setCancelable(false);
                    noInternetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
                    noInternetDialog.show();
                }

                String _username = ETUsername.getEditText().getText().toString();
                String _kataSandi = ETKataSandi.getEditText().getText().toString();
                
                if (cekBoxIngatsaya.isChecked()) {
                    kirimDataKeLokal(_username, _kataSandi);
                }

                reference = FirebaseDatabase.getInstance().getReference().child("Users").child(_username);

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {

                            //ambil password dari database
                            String sandiDariDatabase = dataSnapshot.child("kata_sandi").getValue().toString();

                            //validasi password
                            if (_kataSandi.equals(sandiDariDatabase)) {

                                //menyimpan username ke lokal
                                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY_LOCAL, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(username_key_local, ETUsername.getEditText().getText().toString());
                                editor.apply();

                                //ke main act
                                Intent keMain = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(keMain);

                            //password salah
                            } else {
                                Toast.makeText(LoginActivity.this, "Kata sandi salah :(", Toast.LENGTH_SHORT).show();
                                //ubah teks button
                                btnMasuk.setText("Mulai sekarang");
                            }

                        //username tidak ada
                        } else {
                            Toast.makeText(LoginActivity.this, "Username tidak ada, daftar dulu ya :)", Toast.LENGTH_SHORT).show();
                            //ubah teks button
                            btnMasuk.setText("Mulai sekarang");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(LoginActivity.this, "Database Error", Toast.LENGTH_SHORT).show();
                    }
                });

                //ubah teks button
                btnMasuk.setText("Tunggu sebentar...");
            }
        });

        cekBoxIngatsaya.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    SharedPreferences preferences = getSharedPreferences("cekBox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("cekIngatSaya", "true");
                    editor.apply();
                } else if (!buttonView.isChecked()) {
                    SharedPreferences preferences = getSharedPreferences("cekBox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("cekIngatSaya", "false");
                    editor.apply();
                }
            }
        });

    }

    private void kirimDataKeLokal(String username, String kataSandi) {
        SharedPreferences.Editor editor=getSharedPreferences("dataIngatSaya",MODE_PRIVATE).edit();
        editor.putString("username", username);
        editor.putString("kataSandi", kataSandi);
        editor.apply();
    }

    public void keDaftar(View view) {
        startActivity(new Intent(getApplicationContext(), DaftarActivity.class));
    }

    public void keLupaSandi(View view) {
        startActivity(new Intent(getApplicationContext(), LupaKataSandiActivity.class));
    }

    private boolean cekInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    protected void keluarAplikasi() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setMessage("Kamu mau keluar dari aplikasi ini")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                })
                .setNegativeButton("tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }
    @Override
    public void onBackPressed() {
        keluarAplikasi();
    }

}