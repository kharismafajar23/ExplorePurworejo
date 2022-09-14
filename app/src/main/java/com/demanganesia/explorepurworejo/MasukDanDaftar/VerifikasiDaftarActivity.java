package com.demanganesia.explorepurworejo.MasukDanDaftar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.demanganesia.explorepurworejo.Databases.UserHelperClass;
import com.demanganesia.explorepurworejo.MainActivity;
import com.demanganesia.explorepurworejo.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class VerifikasiDaftarActivity extends AppCompatActivity {

    PinView kodePin;
    String codeBySystem;
    String namaLengkapIni, usernameIni, emailIni, kataSandiIni, jenisKelaminIni, tanggalLahirIni, nomorTelefon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifikasi_daftar);
        //menghilangkan status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        kodePin = findViewById(R.id.kode_pin);
        namaLengkapIni = getIntent().getStringExtra("namaLengkapIni");
        usernameIni = getIntent().getStringExtra("usernameIni");
        emailIni = getIntent().getStringExtra("emailIni");
        kataSandiIni = getIntent().getStringExtra("kataSandiIni");
        jenisKelaminIni = getIntent().getStringExtra("jenisKelaminIni");
        tanggalLahirIni = getIntent().getStringExtra("tanggalLahirIni");
        nomorTelefon = getIntent().getStringExtra("nomorTelefonIni");

        //signInWithPhoneAuthCredential(nomorTelefon);
        signInWithPhoneAuthCredential(nomorTelefon);
    }

    private void signInWithPhoneAuthCredential(String nomorTelefon) {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(nomorTelefon)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    codeBySystem = s;
                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();
                    if (code!=null){
                        kodePin.setText(code);
                        verifikasiKode(code);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(VerifikasiDaftarActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            };

    private void verifikasiKode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem,code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        kirimDataPenggunaBaru();
                    } else {
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(VerifikasiDaftarActivity.this, "Verifikasi gagal, silahkan coba lagi", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void kirimDataPenggunaBaru() {

        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("Users");

        UserHelperClass tambahPenggunaBaru = new UserHelperClass(namaLengkapIni, usernameIni, emailIni, kataSandiIni, jenisKelaminIni, tanggalLahirIni, nomorTelefon);
        reference.child(usernameIni).setValue(tambahPenggunaBaru);
    }

    public void btnVerifikasiKode(View view) {
        String code = kodePin.getText().toString();
        if (!code.isEmpty()) {
            verifikasiKode(code);
            return;
        } else {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    }
}