package com.demanganesia.explorepurworejo;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.squareup.picasso.Picasso;

public class InformasiAkun extends AppCompatActivity {

    ImageView Btn_kembali, IVFotoUserProfilIA;
    TextView TVUsername1, TVBio, TVNamaLengkap, TVUsername2, TVEmail, TVNomorTelefon, TVJenisKelamin, TVTanggalLahir;
    String USERNAME_KEY_LOCAL = "usernamekeylocal";
    String username_key_local = "";
    String username_key_new_local = "";

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informasi_akun);
        //menghilangkan status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getUsernameLocal();

        TVUsername1 = findViewById(R.id.TVusername_IA_1);
        TVBio = findViewById(R.id.TVbio_IA);
        TVNamaLengkap = findViewById(R.id.TVnama_lengkap_IA);
        TVUsername2 = findViewById(R.id.TVusername_IA_2);
        TVEmail = findViewById(R.id.TVEmail_IA);
        TVNomorTelefon = findViewById(R.id.TVnomor_telefon_IA);
        TVJenisKelamin = findViewById(R.id.TVjenis_kelamin_IA);
        TVTanggalLahir = findViewById(R.id.TVtanggal_lahir_IA);
        Btn_kembali = findViewById(R.id.IV_kembali_IA);
        IVFotoUserProfilIA = findViewById(R.id.IV_foto_user_profil_IA);

        //cek koneksi internet
        if (!cekInternet()) {

            //show dialog
            NoInternetDialog noInternetDialog = new NoInternetDialog(InformasiAkun.this);
            noInternetDialog.setCancelable(false);
            noInternetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
            noInternetDialog.show();
        }

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new_local);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                TVUsername1.setText(dataSnapshot.child("username").getValue().toString());
                TVBio.setText(dataSnapshot.child("bio").getValue().toString());
                TVNamaLengkap.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                TVUsername2.setText(dataSnapshot.child("username").getValue().toString());
                TVEmail.setText(dataSnapshot.child("email").getValue().toString());
                TVNomorTelefon.setText(dataSnapshot.child("nomor_telefon").getValue().toString());
                TVJenisKelamin.setText(dataSnapshot.child("jenis_kelamin").getValue().toString());
                TVTanggalLahir.setText(dataSnapshot.child("tanggal_lahir").getValue().toString());
                Picasso.get().load(dataSnapshot.child("url_foto_profil").getValue().toString()).centerCrop().fit().into(IVFotoUserProfilIA);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Btn_kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    public void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY_LOCAL, MODE_PRIVATE);
        username_key_new_local = sharedPreferences.getString(username_key_local, "");
    }

    private boolean cekInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}