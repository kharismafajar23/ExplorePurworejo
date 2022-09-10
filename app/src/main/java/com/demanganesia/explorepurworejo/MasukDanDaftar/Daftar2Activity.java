package com.demanganesia.explorepurworejo.MasukDanDaftar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.demanganesia.explorepurworejo.R;

import java.util.Calendar;

public class Daftar2Activity extends AppCompatActivity {

    RadioGroup radioGrup;
    RadioButton genderDipilih;
    DatePicker tanggalLahir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar2);
        //menghilangkan status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        radioGrup = findViewById(R.id.radio_grup);
        tanggalLahir = findViewById(R.id.DPtanggal_lahir);
    }

    public void keDaftar3(View view) {

        if(!validasiJenisKelamin() | !validasiUmur()) {
            return;
        }

        genderDipilih = findViewById(radioGrup.getCheckedRadioButtonId());
        String _gender = genderDipilih.getText().toString();

        int day = tanggalLahir.getDayOfMonth();
        int month = tanggalLahir.getMonth();
        int year = tanggalLahir.getYear();

        String _date = day+"/"+month+"/"+year;

        startActivity(new Intent(getApplicationContext(), Daftar3Activity.class));
    }

    public void keMasukb(View view) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
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
}