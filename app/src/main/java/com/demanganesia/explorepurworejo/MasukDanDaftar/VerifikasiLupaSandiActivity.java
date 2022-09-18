package com.demanganesia.explorepurworejo.MasukDanDaftar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.demanganesia.explorepurworejo.Databases.UserHelperClassLupaSandi;
import com.demanganesia.explorepurworejo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class VerifikasiLupaSandiActivity extends AppCompatActivity {

    PinView kodePin;
    String codeBySystem;
    String username, nomorTelefon, whatTodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifikasi_lupa_sandi);
        //menghilangkan status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        kodePin = findViewById(R.id.OTP_lupa_sandi);
        username = getIntent().getStringExtra("username");
        nomorTelefon = getIntent().getStringExtra("nomorTelefon");
        whatTodo = getIntent().getStringExtra("whatTodo");

        signInWithPhoneAuthCredential(nomorTelefon);

    }

    private void signInWithPhoneAuthCredential(String nomorTelefon) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth)
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
                    Toast.makeText(VerifikasiLupaSandiActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            };

    private void verifikasiKode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem,code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (whatTodo.equals("updateData")) {
                                updateDataUserLama();
                            } else {
                                kirimDataPenggunaBaru();
                            }
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(VerifikasiLupaSandiActivity.this, "Verifikasi gagal, silahkan coba lagi", Toast.LENGTH_SHORT).show();
                            }
                        }
                        //startActivity(new Intent(getApplicationContext(), KonfirmasiSandiBaruActivity.class));
                    }
                });
    }

    private void updateDataUserLama() {

        Intent intent = new Intent(getApplicationContext(), KonfirmasiSandiBaruActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("nomorTelefon", nomorTelefon);
        startActivity(intent);
        finish();

    }

    private void kirimDataPenggunaBaru() {

        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("Users");

        UserHelperClassLupaSandi tambahPenggunaBaru = new UserHelperClassLupaSandi(username, nomorTelefon);
        reference.child(username).setValue(tambahPenggunaBaru);
    }

    public void keKonfirmasiSandiBaru(View view) {
        String code = kodePin.getText().toString();
        if (!code.isEmpty()) {
            verifikasiKode(code);
            return;
        }
    }
}