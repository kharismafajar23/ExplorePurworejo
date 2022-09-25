package com.demanganesia.explorepurworejo.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.demanganesia.explorepurworejo.DetailWisata;
import com.demanganesia.explorepurworejo.JelajahWisata;
import com.demanganesia.explorepurworejo.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class JelajahFragment extends Fragment {
    
    EditText ETCariJelajah;
    RecyclerView RVJelajahWisata;
    
    DatabaseReference databaseReferenceWisata;

    FirebaseRecyclerOptions<JelajahWisata> options;
    FirebaseRecyclerAdapter<JelajahWisata, JelajahViewHolder> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_jelajah, container, false);
        
        ETCariJelajah = view.findViewById(R.id.ET_cari_jelajah);
        RVJelajahWisata = view.findViewById(R.id.RV_jelajah);
        
        databaseReferenceWisata = FirebaseDatabase.getInstance().getReference().child("Wisata");

        loadDataJelajah();
        return view;
    }

    private void loadDataJelajah() {
        options = new FirebaseRecyclerOptions.Builder<JelajahWisata>().setQuery(databaseReferenceWisata, JelajahWisata.class).build();
        adapter = new FirebaseRecyclerAdapter<JelajahWisata, JelajahViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull JelajahViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull JelajahWisata model) {
                Picasso.get().load(model.getUrl_thumbnail()).centerCrop().fit().into(holder.IVThumbnailWisata);
                holder.TVNamaWisata.setText(model.getNama_wisata());
                holder.TVKategoriWisata.setText(model.getKategori());
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
            public JelajahViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_jelajah, parent, false);
                return new JelajahViewHolder(view);
            }
        };
        adapter.startListening();
        RVJelajahWisata.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}