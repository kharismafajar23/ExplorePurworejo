package com.demanganesia.explorepurworejo.Fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demanganesia.explorepurworejo.R;

public class MyViewHolder extends RecyclerView.ViewHolder {

    ImageView thumbnail_wisata;
    TextView nama_wisata;
    View v;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        thumbnail_wisata = itemView.findViewById(R.id.IV_thumbnail_wisata_rekomendasi);
        nama_wisata = itemView.findViewById(R.id.nama_wisata_rekomendasi);
        v = itemView;
    }
}
