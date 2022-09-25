package com.demanganesia.explorepurworejo;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.demanganesia.explorepurworejo.HelperClasses.NoInternetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SyaratDanKetentuan extends AppCompatActivity {

    ImageView BtnKembali;
    TextView TVSyaratDanKetentuan;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syarat_dan_ketentuan);
        //menghilangkan status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //cek koneksi internet
        if (!cekInternet()) {

            //show dialog
            NoInternetDialog noInternetDialog = new NoInternetDialog(SyaratDanKetentuan.this);
            noInternetDialog.setCancelable(false);
            noInternetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
            noInternetDialog.show();
        }

        TVSyaratDanKetentuan = findViewById(R.id.TV_syarat_dan_ketentuan);
        BtnKembali = findViewById(R.id.btn_kembali_SDK);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Aplikasi");
        databaseReference.child("syarat_dan_ketentuan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //ambil data
                String _syarat_dan_ketentuan = dataSnapshot.child("deskripsi_syarat_dan_ketentuan").getValue().toString();

                //merubah data teks
                TVSyaratDanKetentuan.setText(_syarat_dan_ketentuan);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        BtnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private boolean cekInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}