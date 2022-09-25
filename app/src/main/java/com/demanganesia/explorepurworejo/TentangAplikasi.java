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

public class TentangAplikasi extends AppCompatActivity {

    TextView TVTentangAplikasi;
    ImageView BtnKembali;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentang_aplikasi);
        //menghilangkan status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        TVTentangAplikasi = findViewById(R.id.TV_tentang_aplikasi);
        BtnKembali = findViewById(R.id.btn_kembali_TA);

        //cek koneksi internet
        if (!cekInternet()) {

            //show dialog
            NoInternetDialog noInternetDialog = new NoInternetDialog(TentangAplikasi.this);
            noInternetDialog.setCancelable(false);
            noInternetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
            noInternetDialog.show();
        }

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Aplikasi");
        databaseReference.child("tentang_aplikasi").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //ambil data
                String _tentang_aplikasi = dataSnapshot.child("deskripsi_tentang_aplikasi").getValue().toString();

                //merubah data teks
                TVTentangAplikasi.setText(_tentang_aplikasi);
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