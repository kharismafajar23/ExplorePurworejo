package com.demanganesia.explorepurworejo;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hbb20.CountryCodePicker;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class EditProfil extends AppCompatActivity {

    ImageView BtnKembali, BtnEditFotoProfil, IVFotoUserEP;
    TextInputLayout ETNamaLengkap, ETEmail, ETBio, ETNomortelefon;
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
    StorageReference storageReference;

    Uri photo_location;
    Integer photo_max = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil);
        //menghilangkan status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        BtnKembali = findViewById(R.id.kembali_EP);
        IVFotoUserEP = findViewById(R.id.IV_foto_user_EP);
        ETNamaLengkap = findViewById(R.id.ET_nama_lengkap_EP);
        ETEmail = findViewById(R.id.ET_email_EP);
        ETBio = findViewById(R.id.ET_bio_EP);
        ETNomortelefon = findViewById(R.id.ET_nomor_telefon_EP);
        InputKodeNegara = findViewById(R.id.Inputkode_negara_EP);
        radioGrup = findViewById(R.id.radio_grup_EP);
        BtnEditFotoProfil = findViewById(R.id.IV_edit_foto_profil_EP);
        BtnSimpan = findViewById(R.id.Btn_simpan_EP);
        tanggalLahir = findViewById(R.id.DPtanggal_lahir_EP);

        getUsernameLocal();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new_local);
        storageReference = FirebaseStorage.getInstance().getReference().child("Foto User").child(username_key_new_local);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ETNamaLengkap.getEditText().setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                ETEmail.getEditText().setText(dataSnapshot.child("email").getValue().toString());
                ETBio.getEditText().setText(dataSnapshot.child("bio").getValue().toString());
                Picasso.with(EditProfil.this).load(dataSnapshot.child("url_foto_profil").getValue().toString()).centerCrop().fit().into(IVFotoUserEP);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        BtnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validasiFormNamaLengkap() | !validasiFormEmail() | !validasiJenisKelamin() | !validasiUmur() | !validasiFormNomorTelepon()) {
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

                //validasi file foto
                if (photo_location != null){
                    StorageReference storageReference1 = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(photo_location));
                    storageReference1.putFile(photo_location)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String uri_photo = uri.toString();
                                            databaseReference.getRef().child("url_foto_profil").setValue(uri_photo);
                                        }
                                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            //pindah act
                                            //Intent keFragmentEditProfil = new Intent(EditProfil.this, MainActivity.class);
                                            //startActivity(keFragmentEditProfil);
                                        }
                                    });
                                }
                            }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        }
                    });
                }
                BtnSimpan.setText("Tunggu sebentar...");
                //pindah act
                Intent keMainAct = new Intent(EditProfil.this, MainActivity.class);
                startActivity(keMainAct);
                finish();
            }
        });

        BtnEditFotoProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPhoto();
            }
        });

        BtnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    String getFileExtension (Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void findPhoto(){
        Intent pic = new Intent();
        pic.setType("image/*");
        pic.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pic, photo_max);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == photo_max && resultCode == RESULT_OK && data != null && data.getData() != null) {
            photo_location = data.getData();
            Picasso.with(this).load(photo_location).centerCrop().fit().into(IVFotoUserEP);
        }
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