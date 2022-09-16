package com.demanganesia.explorepurworejo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.demanganesia.explorepurworejo.Fragment.HomeFragment;
import com.demanganesia.explorepurworejo.Fragment.JelajahFragment;
import com.demanganesia.explorepurworejo.Fragment.ProfilFragment;
import com.demanganesia.explorepurworejo.MasukDanDaftar.LoginActivity;
import com.google.firebase.messaging.FirebaseMessaging;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity extends AppCompatActivity {

    SmoothBottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //menghilangkan status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        replace(new HomeFragment());
        bottomBar = findViewById(R.id.bottomBar);
        bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                switch (i) {
                    case 0:
                        replace(new HomeFragment());
                        break;

                    case 1:
                        replace(new JelajahFragment());
                        break;

                    case 2:
                        replace(new ProfilFragment());
                        break;
                }
                return false;
            }
        });

        //menerima notifikasi
        FirebaseMessaging.getInstance().subscribeToTopic("all");

    }

    private void replace(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame,fragment);
        transaction.commit();
    }

    public void keTentangAplikasi(View view) {
        startActivity(new Intent(getApplicationContext(), TentangAplikasi.class));
    }

    public void keSyaratDanKetentuan(View view) {
        startActivity(new Intent(getApplicationContext(), SyaratDanKetentuan.class));
    }

    public void keInformasiAkun(View view) {
        startActivity(new Intent(getApplicationContext(), InformasiAkun.class));
    }

    public void keEditProfil(View view) {
    }

    public void keluarAkun(View view) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }
}