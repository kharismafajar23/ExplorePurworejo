package com.demanganesia.explorepurworejo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.demanganesia.explorepurworejo.Fragment.ProfilFragment;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.util.Calendar;

public class EditProfil extends AppCompatActivity {

    ImageView IVKembali, IVEditProfil;
    TextInputLayout ETNamaLengkap, ETUsername, ETEmail, ETBio, ETNomortelefon;
    CountryCodePicker InputKodeNegara;
    RadioGroup radioGrup;
    RadioButton genderDipilih;
    DatePicker tanggalLahir;
    Button BtnSimpan;
    String _jenisKelamin, _tanggalLahir, _inputNomorTelefon, _nomorTelefon;


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

        IVKembali = findViewById(R.id.kembali_EP);
        IVEditProfil = findViewById(R.id.IV_edit_profil);
        ETNamaLengkap = findViewById(R.id.ET_nama_lengkap_EP);
        ETUsername = findViewById(R.id.ET_username_EP);
        ETEmail = findViewById(R.id.ET_email_EP);
        ETBio = findViewById(R.id.ET_bio_EP);
        ETNomortelefon = findViewById(R.id.ET_nomor_telefon_EP);
        InputKodeNegara = findViewById(R.id.Inputkode_negara_EP);
        radioGrup = findViewById(R.id.radio_grup_EP);
        BtnSimpan = findViewById(R.id.Btn_simpan_EP);
        tanggalLahir = findViewById(R.id.DPtanggal_lahir_EP);

        getUsernameLocal();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new_local);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ETNamaLengkap.getEditText().setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                ETUsername.getEditText().setText(dataSnapshot.child("username").getValue().toString());
                ETEmail.getEditText().setText(dataSnapshot.child("email").getValue().toString());
                ETBio.getEditText().setText(dataSnapshot.child("bio").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        BtnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validasiFormNamaLengkap() | !validasiFormUsername() | !validasiFormEmail() | !validasiJenisKelamin() | !validasiUmur() | !validasiFormNomorTelepon()) {
                    return;
                }

                //jenis kelamin
                genderDipilih = findViewById(radioGrup.getCheckedRadioButtonId());
                _jenisKelamin = genderDipilih.getText().toString();

                //tanggal lahir
                int day = tanggalLahir.getDayOfMonth();
                int month = tanggalLahir.getMonth();
                int year = tanggalLahir.getYear();

                _tanggalLahir = day+"/"+month+"/"+year;

                //nomor telefon
                _inputNomorTelefon = ETNomortelefon.getEditText().getText().toString().trim();
                _nomorTelefon = "+"+InputKodeNegara.getSelectedCountryCode()+_inputNomorTelefon;

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("nama_lengkap").setValue(ETNamaLengkap.getEditText().getText().toString());
                        dataSnapshot.getRef().child("username").setValue(ETUsername.getEditText().getText().toString());
                        dataSnapshot.getRef().child("email").setValue(ETEmail.getEditText().getText().toString());
                        dataSnapshot.getRef().child("bio").setValue(ETBio.getEditText().getText().toString());
                        dataSnapshot.getRef().child("jenis_kelamin").setValue(_jenisKelamin);
                        dataSnapshot.getRef().child("tanggal_lahir").setValue(_tanggalLahir);
                        dataSnapshot.getRef().child("nomor_telefon").setValue(_nomorTelefon);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                //pindah act
                Intent keMain = new Intent(EditProfil.this, MainActivity.class);
                startActivity(keMain);
            }
        });

    }

    public void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY_LOCAL, MODE_PRIVATE);
        username_key_new_local = sharedPreferences.getString(username_key_local, "");
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

    //validasi form email
    private boolean validasiFormEmail() {
        String val = ETEmail.getEditText().getText().toString().trim();
        String cekEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            ETEmail.setError("Bagian ini tidak boleh kosong");
            return false;
        } else if(!val.matches(cekEmail)) {
            ETEmail.setError("Masukkan format email dengan benar");
            return false;
        }
        else {
            ETEmail.setError(null);
            ETEmail.setErrorEnabled(false);
            return true;
        }
    }

    //validasi jenis kelamin
    private boolean validasiJenisKelamin() {
        if (radioGrup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Pilih salah satu jenis kelamin", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    //validasi umur
    private boolean validasiUmur() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = tanggalLahir.getYear();
        int isAgeValid = currentYear - userAge;

        if(isAgeValid < 10){
            Toast.makeText(this, "Mohon maaf, kamu belum cukup umur", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }

    //validasi form nomor telefon
    private boolean validasiFormNomorTelepon() {
        String val = ETNomortelefon.getEditText().getText().toString().trim();
        String cekSpasi = "\\A\\w{1,20}\\z";

        if (val.isEmpty()) {
            ETNomortelefon.setError("Bagian ini tidak boleh kosong");
            return false;
        } else if(!val.matches(cekSpasi)) {
            ETNomortelefon.setError("Spasi tidak diperbolehkan");
            return false;
        }
        else {
            ETNomortelefon.setError(null);
            ETNomortelefon.setErrorEnabled(false);
            return true;
        }
    }
}