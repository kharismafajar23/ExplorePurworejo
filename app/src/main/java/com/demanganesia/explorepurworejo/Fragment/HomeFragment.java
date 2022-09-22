package com.demanganesia.explorepurworejo.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.demanganesia.explorepurworejo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class HomeFragment extends Fragment {

    TextView TVusername;
    ImageView IVFotoUserHome;

    String USERNAME_KEY_LOCAL = "usernamekeylocal";
    String username_key_local = "";
    String username_key_new_local = "";

    DatabaseReference databaseReference, databaseReference2;

    RecyclerView RVRekomendasiWisata;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        getUsernameLocal();

        TVusername = view.findViewById(R.id.TVusername_home);
        IVFotoUserHome = view.findViewById(R.id.IV_foto_user_home);
        RVRekomendasiWisata = view.findViewById(R.id.RV_rekomendasi_wisata);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new_local);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TVusername.setText(dataSnapshot.child("username").getValue().toString());
                Picasso.with(getContext()).load(dataSnapshot.child("url_foto_profil").getValue().toString()).centerCrop().fit().into(IVFotoUserHome);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        return view;
    }
    public void getUsernameLocal() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(USERNAME_KEY_LOCAL, MODE_PRIVATE);
        username_key_new_local = sharedPreferences.getString(username_key_local, "");
    }
}