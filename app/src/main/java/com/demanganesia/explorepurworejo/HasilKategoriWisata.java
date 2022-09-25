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

public class HasilKategoriWisata extends AppCompatActivity {

    String _kategori;
    TextView TVKategori2;
    ImageView BtnKembali;
    RecyclerView RVHasilKategoriWisata;

    FirebaseRecyclerOptions<KategoriWisata> options;
    FirebaseRecyclerAdapter<KategoriWisata, KategoriViewHolder> adapter;

    DatabaseReference databaseReferenceWisata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_kategori_wisata);
        //menghilangkan status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        RVHasilKategoriWisata = findViewById(R.id.RV_hasil_kategori_wisata);
        BtnKembali = findViewById(R.id.IV_kembali_kategori);
        TVKategori2 = findViewById(R.id.TV_kategori_2);
        _kategori = getIntent().getStringExtra("_kategori");
        databaseReferenceWisata = FirebaseDatabase.getInstance().getReference().child("Wisata");

        loadDataHasilKategori(_kategori);
        TVKategori2.setText(_kategori);

        BtnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void loadDataHasilKategori(String data) {
        Query query = databaseReferenceWisata.orderByChild("kategori").startAt(data).endAt(data+"\uf8ff");

        options = new FirebaseRecyclerOptions.Builder<KategoriWisata>().setQuery(query, KategoriWisata.class).build();
        adapter = new FirebaseRecyclerAdapter<KategoriWisata, KategoriViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull KategoriViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull KategoriWisata model) {
                holder.TVNamaWisataKategori.setText(model.getNama_wisata());
                holder.TVAlamatWisata.setText(model.getAlamat());
                Picasso.get().load(model.getUrl_thumbnail()).centerCrop().fit().into(holder.IVThumbnailWisataKategori);

                //berpindah act jika di klik
                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent keDetailWisata = new Intent(HasilKategoriWisata.this, DetailWisata.class);
                        keDetailWisata.putExtra("nama_wisata_key", getRef(position).getKey());
                        startActivity(keDetailWisata);
                    }
                });
            }

            @NonNull
            @Override
            public KategoriViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hasil_pencarian_kategori_item_view, parent, false);
                return new KategoriViewHolder(v);
            }
        };
        adapter.startListening();
        RVHasilKategoriWisata.setAdapter(adapter);
    }
}