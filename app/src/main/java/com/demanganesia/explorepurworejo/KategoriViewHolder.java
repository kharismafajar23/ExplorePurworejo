package com.demanganesia.explorepurworejo;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class KategoriViewHolder extends RecyclerView.ViewHolder {

    ImageView IVThumbnailWisataKategori;
    TextView TVNamaWisataKategori, TVAlamatWisata;
    View v;

    public KategoriViewHolder(@NonNull View itemView) {
        super(itemView);
        IVThumbnailWisataKategori = itemView.findViewById(R.id.IV_thumbnail_wisata_kategori);
        TVNamaWisataKategori = itemView.findViewById(R.id.TV_nama_wisata_kategori);
        TVAlamatWisata = itemView.findViewById(R.id.TV_alamat_wisata_kategori);
        v = itemView;
    }
}
