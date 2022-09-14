package com.demanganesia.explorepurworejo.MasukDanDaftar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.demanganesia.explorepurworejo.MainActivity;
import com.demanganesia.explorepurworejo.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout _ETUsername, _ETKataSandi;
    Button btnMasuk;
    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //menghilangkan status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        _ETUsername = findViewById(R.id.ETusername);
        _ETKataSandi = findViewById(R.id.ETkata_sandi);
        btnMasuk = findViewById(R.id.BtnMasuk);

        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = _ETUsername.getEditText().getText().toString();
                String kataSandi = _ETKataSandi.getEditText().getText().toString();
                reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username);

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){

                            //cek password
                            String kataSandiDatabase = snapshot.child("kataSandi").getValue().toString();

                            //validasi password
                            if (kataSandi.equals(kataSandiDatabase)) {
                                //ke main act
                                Intent keMainAct = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(keMainAct);

                                //menyimpan username di local
                                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(username_key, _ETUsername.getEditText().getText().toString());
                                editor.apply();

                            } else {
                                Toast.makeText(getApplicationContext(), "Kata sandinya salah :(", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(),"Akun tidak terdaftar, daftar dulu ya :)", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }
    public void keDaftar(View view) {
        startActivity(new Intent(getApplicationContext(), DaftarActivity.class));
    }

    public void keLupaSandi(View view) {
        startActivity(new Intent(getApplicationContext(), LupaKataSandiActivity.class));
    }

    //Cek Koneksi Internet
    private boolean CekKoneksi(LoginActivity loginActivity) {

        ConnectivityManager connectivityManager = (ConnectivityManager) loginActivity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo koneksiWifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo koneksiDataSeluler = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if((koneksiWifi != null && koneksiWifi.isConnected()) || (koneksiDataSeluler != null && koneksiDataSeluler.isConnected())){
            return true;
        }
        else{
            return false;
        }
    }

    private void showCustomDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage("Pastikan tersambung ke internet")
                .setCancelable(false)
                .setPositiveButton("Sambungkan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }
                });

    }
}