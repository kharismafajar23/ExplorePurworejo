package com.demanganesia.explorepurworejo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.demanganesia.explorepurworejo.Fragment.HomeFragment;
import com.demanganesia.explorepurworejo.Fragment.JelajahFragment;
import com.demanganesia.explorepurworejo.Fragment.ProfilFragment;
import com.google.firebase.messaging.FirebaseMessaging;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity extends AppCompatActivity {

    private long backPress;
    private Toast backToast;

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

    protected void keluarAplikasi() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setMessage("Kamu mau keluar dari aplikasi ini")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);
                    }
                })
                .setNegativeButton("tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
    }
    @Override
    public void onBackPressed() {
        keluarAplikasi();
//        if (backPress + 2000 > System.currentTimeMillis()) {
//            backToast.cancel();
//            super.onBackPressed();
//            return;
//        }else{
//            backToast = Toast.makeText(getBaseContext(), "Tekan kembali dua kali untuk keluar", Toast.LENGTH_LONG);
//            backToast.show();
//        }
//        backPress = System.currentTimeMillis();
    }
}