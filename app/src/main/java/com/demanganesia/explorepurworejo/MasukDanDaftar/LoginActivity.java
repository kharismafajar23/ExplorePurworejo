package com.demanganesia.explorepurworejo.MasukDanDaftar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.demanganesia.explorepurworejo.MainActivity;
import com.demanganesia.explorepurworejo.R;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout ETUsername, ETKataSandi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //menghilangkan status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ETUsername = findViewById(R.id.ETusername);
        ETKataSandi = findViewById(R.id.ETkata_sandi);

    }
    public void keDaftar(View view) {
        startActivity(new Intent(getApplicationContext(), DaftarActivity.class));
    }

    public void keLupaSandi(View view) {
        startActivity(new Intent(getApplicationContext(), LupaKataSandiActivity.class));
    }

    public void login(View view) {

        if (!validasiFormUsername() | !validasiFormKataSandi()){
            return;
        } else {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
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

    //validasi form username
    private boolean validasiFormUsername() {
        String val = ETUsername.getEditText().getText().toString().trim();
        String cekSpasi = "\\A\\w{1,20}\\z";

        if (val.isEmpty()) {
            ETUsername.setError("Bagian ini tidak boleh kosong");
            return false;
        } else if(val.length()>20){
            ETUsername.setError("Username terlalu panjang");
            return false;
        } else if(!val.matches(cekSpasi)) {
            ETUsername.setError("Spasi tidak diperbolehkan");
            return false;
        }
        else {
            ETUsername.setError(null);
            ETUsername.setErrorEnabled(false);
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
}