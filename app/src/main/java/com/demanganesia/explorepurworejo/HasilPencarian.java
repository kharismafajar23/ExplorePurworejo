package com.demanganesia.explorepurworejo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class HasilPencarian extends AppCompatActivity {

    ImageView BtnKembali;
    TextView TVHasilPencarian;
    RecyclerView RVHasilPencarian;
    String _kata_kunci;

    DatabaseReference databaseReferenceWisata;

    FirebaseRecyclerOptions<PencarianWisata> options;
    FirebaseRecyclerAdapter<PencarianWisata, PencarianViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_pencarian);
        //menghilangkan status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        BtnKembali = findViewById(R.id.IV_kembali_hasil_pencarian);
        TVHasilPencarian = findViewById(R.id.TVkata_kunci_pencarian);
        RVHasilPencarian = findViewById(R.id.RV_hasil_pencarian);
        RVHasilPencarian.setHasFixedSize(true);

        String _kata_kunci = getIntent().getStringExtra("_kata_kunci");
        databaseReferenceWisata = FirebaseDatabase.getInstance().getReference().child("Wisata");

        loadDataHasilPencarian(_kata_kunci);
        TVHasilPencarian.setText(_kata_kunci);

        BtnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void loadDataHasilPencarian(String _kata_kunci) {
        Query query = databaseReferenceWisata.orderByChild("nama_wisata").startAt(_kata_kunci).endAt(_kata_kunci+"\uf8ff");

        options = new FirebaseRecyclerOptions.Builder<PencarianWisata>().setQuery(query, PencarianWisata.class).build();
        adapter = new FirebaseRecyclerAdapter<PencarianWisata, PencarianViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull PencarianViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull PencarianWisata model) {
                holder.TVNamaWisataPencarian.setText(model.getNama_wisata());
                holder.TVAlamatPencarian.setText(model.getAlamat());
                Picasso.get().load(model.getUrl_thumbnail()).centerCrop().fit().into(holder.IVThumbnanilWisataPencarian);

                //berpindah jika di klik
                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent keDetailWisata = new Intent(HasilPencarian.this, DetailWisata.class);
                        keDetailWisata.putExtra("nama_wisata_key", getRef(position).getKey());
                        startActivity(keDetailWisata);
                    }
                });
            }

            @NonNull
            @Override
            public PencarianViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hasil_pencarian_item_view, parent, false);
                return new PencarianViewHolder(v);
            }
        };
        adapter.startListening();
        RVHasilPencarian.setAdapter(adapter);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        adapter.startListening();
        RVHasilPencarian.setAdapter(adapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.startListening();
        RVHasilPencarian.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
        RVHasilPencarian.setAdapter(adapter);
    }
}