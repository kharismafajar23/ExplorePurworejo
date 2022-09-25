package com.demanganesia.explorepurworejo;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PencarianViewHolder extends RecyclerView.ViewHolder {

    ImageView IVThumbnanilWisataPencarian;
    TextView TVNamaWisataPencarian, TVAlamatPencarian;
    View v;

    public PencarianViewHolder(@NonNull View itemView) {
        super(itemView);
        IVThumbnanilWisataPencarian = itemView.findViewById(R.id.IV_thumbnail_wisata_hasil_pencarian);
        TVNamaWisataPencarian = itemView.findViewById(R.id.TV_nama_wisata_hasil_pencarian);
        TVAlamatPencarian = itemView.findViewById(R.id.TV_alamat_wisata_hasil_pencarian);
        v = itemView;
    }
}
