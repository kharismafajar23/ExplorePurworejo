package com.demanganesia.explorepurworejo.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.demanganesia.explorepurworejo.DetailWisata;
import com.demanganesia.explorepurworejo.HasilKategoriWisata;
import com.demanganesia.explorepurworejo.HasilPencarian;
import com.demanganesia.explorepurworejo.InformasiAkun;
import com.demanganesia.explorepurworejo.R;
import com.demanganesia.explorepurworejo.RekomendasiWisata;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class HomeFragment extends Fragment {

    TextView TVusername;
    EditText ETCariWisata;
    ImageView IVFotoUserHome, BtnPantai, BtnGoa, BtnAirTerjun, BtnAlam, BtnOlahraga, BtnBuatan, BtnRohani, BtnSejarah, BtnSearch;
    String _kata_kunci;

    String USERNAME_KEY_LOCAL = "usernamekeylocal";
    String username_key_local = "";
    String username_key_new_local = "";

    DatabaseReference databaseReference, databaseReferenceWisata;

    RecyclerView RVRekomendasiWisata;
    FirebaseRecyclerOptions<RekomendasiWisata> options;
    FirebaseRecyclerAdapter<RekomendasiWisata, MyViewHolder> adapter;
    private Context globalContext = null;

    public HomeFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        getUsernameLocal();

        ETCariWisata = view.findViewById(R.id.ET_cari_wisata);
        TVusername = view.findViewById(R.id.TVusername_home);
        IVFotoUserHome = view.findViewById(R.id.IV_foto_user_home);
        RVRekomendasiWisata = view.findViewById(R.id.RV_rekomendasi_wisata_home);
        BtnPantai = view.findViewById(R.id.btn_pantai);
        BtnGoa = view.findViewById(R.id.IV_goa);
        BtnAirTerjun = view.findViewById(R.id.IV_air_terjun);
        BtnAlam = view.findViewById(R.id.IV_alam);
        BtnOlahraga = view.findViewById(R.id.IV_olahraga);
        BtnBuatan = view.findViewById(R.id.IV_buatan);
        BtnRohani = view.findViewById(R.id.IV_rohani);
        BtnSejarah = view.findViewById(R.id.IV_sejarah);
        BtnSearch = view.findViewById(R.id.Btn_cari_wisata);
        globalContext = this.getActivity();

        databaseReferenceWisata = FirebaseDatabase.getInstance().getReference().child("Rekomendasi Wisata");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new_local);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TVusername.setText(dataSnapshot.child("username").getValue().toString());
                Picasso.get().load(dataSnapshot.child("url_foto_profil").getValue().toString()).centerCrop().fit().into(IVFotoUserHome);

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        IVFotoUserHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent keInformasiAkun = new Intent(getActivity(), InformasiAkun.class);
                startActivity(keInformasiAkun);
            }
        });

        BtnPantai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent keKategoriPantai = new Intent(getActivity(), HasilKategoriWisata.class);
                keKategoriPantai.putExtra("_kategori", "Pantai");
                startActivity(keKategoriPantai);
            }
        });

        BtnGoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent keKategoriGoa = new Intent(getActivity(), HasilKategoriWisata.class);
                keKategoriGoa.putExtra("_kategori", "Goa");
                startActivity(keKategoriGoa);
            }
        });

        BtnAirTerjun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent keKategoriAirTerjun = new Intent(getActivity(), HasilKategoriWisata.class);
                keKategoriAirTerjun.putExtra("_kategori", "Air Terjun");
                startActivity(keKategoriAirTerjun);
            }
        });

        BtnAlam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent keKategoriAlam = new Intent(getActivity(), HasilKategoriWisata.class);
                keKategoriAlam.putExtra("_kategori", "Alam");
                startActivity(keKategoriAlam);
            }
        });

        BtnOlahraga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent keKategoriOlahraga = new Intent(getActivity(), HasilKategoriWisata.class);
                keKategoriOlahraga.putExtra("_kategori", "Olahraga");
                startActivity(keKategoriOlahraga);
            }
        });

        BtnBuatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent keKategoriBuatan = new Intent(getActivity(), HasilKategoriWisata.class);
                keKategoriBuatan.putExtra("_kategori", "Buatan");
                startActivity(keKategoriBuatan);
            }
        });

        BtnRohani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent keKategoriRohani = new Intent(getActivity(), HasilKategoriWisata.class);
                keKategoriRohani.putExtra("_kategori", "Rohani");
                startActivity(keKategoriRohani);
            }
        });

        BtnSejarah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent keKategoriSejarah = new Intent(getActivity(), HasilKategoriWisata.class);
                keKategoriSejarah.putExtra("_kategori", "Sejarah");
                startActivity(keKategoriSejarah);
            }
        });

        //cari wisata
        BtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validasiFormInput()){
                    return;
                }
                _kata_kunci = ETCariWisata.getText().toString();
                Intent kePencarian = new Intent(getActivity(), HasilPencarian.class);
                kePencarian.putExtra("_kata_kunci", _kata_kunci);
                startActivity(kePencarian);

            }
        });
        loadData();
        return view;
    }

    private boolean validasiFormInput() {
        String val = ETCariWisata.getText().toString().trim();

        if (val.isEmpty()) {
            Toast.makeText(globalContext, "Kata kunci tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            ETCariWisata.setError(null);
            return true;
        }
    }

    private void loadData() {

        options = new FirebaseRecyclerOptions.Builder<RekomendasiWisata>().setQuery(databaseReferenceWisata, RekomendasiWisata.class).build();
        adapter = new FirebaseRecyclerAdapter<RekomendasiWisata, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView")final int position, @NonNull RekomendasiWisata model) {
                Picasso.get().load(model.getUrl_thumbnail()).centerCrop().fit().into(holder.thumbnail_wisata);
                holder.nama_wisata.setText(model.getNama_wisata());
                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), DetailWisata.class);
                        intent.putExtra("nama_wisata_key", getRef(position).getKey());
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rekomendasi_wisata_item_view,parent,false);
                //View view = inflater.inflate(R.layout.fragment_home, container, false);
                return new MyViewHolder(view);
            }
        };
        adapter.startListening();
        RVRekomendasiWisata.setAdapter(adapter);
    }

    public void getUsernameLocal() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(USERNAME_KEY_LOCAL, MODE_PRIVATE);
        username_key_new_local = sharedPreferences.getString(username_key_local, "");
    }
}