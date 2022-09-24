package com.demanganesia.explorepurworejo.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.demanganesia.explorepurworejo.EditProfil;
import com.demanganesia.explorepurworejo.InformasiAkun;
import com.demanganesia.explorepurworejo.MasukDanDaftar.LoginActivity;
import com.demanganesia.explorepurworejo.R;
import com.demanganesia.explorepurworejo.SyaratDanKetentuan;
import com.demanganesia.explorepurworejo.TentangAplikasi;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfilFragment extends Fragment {

    LinearLayout LLInformasiAkun, LLTentangAplikasi, LLSyaratDanKetentuan, LLKeluarAkun;
    TextView TVUsername, TVBio;
    ImageView IVFotoUserProfil;
    Button BtnEditProfil;

    String USERNAME_KEY_LOCAL = "usernamekeylocal";
    String username_key_local = "";
    String username_key_new_local = "";
    Context context;

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
        IVFotoUserProfil = view.findViewById(R.id.IV_foto_user_profil);
        BtnEditProfil = view.findViewById(R.id.Btn_edit_profil);
        context = getActivity();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new_local);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                TVUsername.setText(dataSnapshot.child("username").getValue().toString());
                TVBio.setText(dataSnapshot.child("bio").getValue().toString());
                Picasso.get().load(dataSnapshot.child("url_foto_profil").getValue().toString()).centerCrop().fit().into(IVFotoUserProfil);
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

        LLKeluarAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getActivity().getSharedPreferences("cekBox", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("cekIngatSaya", "false");
                editor.apply();
                Intent keLogin = new Intent(getActivity(), LoginActivity.class);
                startActivity(keLogin);
                getActivity().finish();
            }
        });
        return view;

    }

    public void getUsernameLocal() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(USERNAME_KEY_LOCAL, MODE_PRIVATE);
        username_key_new_local = sharedPreferences.getString(username_key_local, "");
    }
}