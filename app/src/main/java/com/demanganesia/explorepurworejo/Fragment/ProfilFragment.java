package com.demanganesia.explorepurworejo.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.demanganesia.explorepurworejo.EditProfil;
import com.demanganesia.explorepurworejo.InformasiAkun;
import com.demanganesia.explorepurworejo.R;
import com.demanganesia.explorepurworejo.SyaratDanKetentuan;
import com.demanganesia.explorepurworejo.TentangAplikasi;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfilFragment extends Fragment {

    LinearLayout LLInformasiAkun, LLTentangAplikasi, LLSyaratDanKetentuan, LLKeluarAkun;
    TextView TVUsername, TVBio;
    Button BtnEditProfil;

    String USERNAME_KEY_LOCAL = "usernamekeylocal";
    String username_key_local = "";
    String username_key_new_local = "";

    DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profil, container, false);

        getUsernameLocal();

        LLInformasiAkun = view.findViewById(R.id.LLinformasi_akun);
        LLTentangAplikasi = view.findViewById(R.id.LL_tentang_aplikasi);
        LLSyaratDanKetentuan = view.findViewById(R.id.LLsyarat_dan_ketentuan);
        LLKeluarAkun = view.findViewById(R.id.LLkeluar_akun);
        TVUsername = view.findViewById(R.id.TV_username_profil);
        TVBio = view.findViewById(R.id.TV_bio_profil);
        BtnEditProfil = view.findViewById(R.id.Btn_edit_profil);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new_local);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                TVUsername.setText(dataSnapshot.child("username").getValue().toString());
                TVBio.setText(dataSnapshot.child("bio").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        LLInformasiAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent keInformasiAkun = new Intent(getActivity(), InformasiAkun.class);
                startActivity(keInformasiAkun);
            }
        });

        LLTentangAplikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent keTentangAplikasi = new Intent(getActivity(), TentangAplikasi.class);
                startActivity(keTentangAplikasi);
            }
        });

        LLSyaratDanKetentuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent keSyaratDanKetentuan = new Intent(getActivity(), SyaratDanKetentuan.class);
                startActivity(keSyaratDanKetentuan);
            }
        });

        BtnEditProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent keEditProfil = new Intent(getActivity(), EditProfil.class);
                startActivity(keEditProfil);
            }
        });
        return view;

    }

    public void getUsernameLocal() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(USERNAME_KEY_LOCAL, MODE_PRIVATE);
        username_key_new_local = sharedPreferences.getString(username_key_local, "");
    }
}