package com.demanganesia.explorepurworejo.Fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demanganesia.explorepurworejo.R;

public class JelajahViewHolder extends RecyclerView.ViewHolder {

    ImageView IVThumbnailWisata;
    TextView TVNamaWisata, TVKategoriWisata;
    View v;

    public JelajahViewHolder(@NonNull View itemView) {
        super(itemView);
        IVThumbnailWisata = itemView.findViewById(R.id.IV_thumbnail_wisata_jelajah);
        TVNamaWisata = itemView.findViewById(R.id.TV_nama_wisata_jelajah);
        TVKategoriWisata = itemView.findViewById(R.id.TV_kategori_wisata_jelajah);
        v = itemView;
    }
}
