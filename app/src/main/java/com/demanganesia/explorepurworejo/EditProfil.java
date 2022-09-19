package com.demanganesia.explorepurworejo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.hbb20.CountryCodePicker;

public class EditProfil extends AppCompatActivity {

    ImageView IVKembali, IVEditProfil;
    TextInputLayout ETNamaLengkap, ETUsername, ETEmail, ETBio, ETNomortelefon;
    CountryCodePicker InputKodeNegara;
    RadioGroup radioGrup;
    RadioButton genderDipilih;
    DatePicker tanggalLahir;


    String USERNAME_KEY_LOCAL = "usernamekeylocal";
    String username_key_local = "";
    String username_key_new_local = "";

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil);
        //menghilangkan status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getUsernameLocal();

        IVKembali = findViewById(R.id.kembali_EP);
        IVEditProfil = findViewById(R.id.IV_edit_profil);
        ETNamaLengkap = findViewById(R.id.ET_nama_lengkap_EP);
        ETUsername = findViewById(R.id.ET_username_EP);
        ETEmail = findViewById(R.id.ET_email_EP);
        ETBio = findViewById(R.id.ET_bio_EP);
        ETNomortelefon = findViewById(R.id.ET_nomor_telefon_EP);
        InputKodeNegara = findViewById(R.id.Inputkode_negara_EP);
        radioGrup = findViewById(R.id.radio_grup_EP);

    }

    public void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY_LOCAL, MODE_PRIVATE);
        username_key_new_local = sharedPreferences.getString(username_key_local, "");
    }
}