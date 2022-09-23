package com.demanganesia.explorepurworejo;

public class RekomendasiWisata {

    private String nama_wisata, url_thumbnail;

    public RekomendasiWisata(String nama_wisata, String url_thumbnail) {
        this.nama_wisata = nama_wisata;
        this.url_thumbnail = url_thumbnail;
    }

    public RekomendasiWisata() {
    }

    public String getNama_wisata() {
        return nama_wisata;
    }

    public void setNama_wisata(String nama_wisata) {
        this.nama_wisata = nama_wisata;
    }

    public String getUrl_thumbnail() {
        return url_thumbnail;
    }

    public void setUrl_thumbnail(String url_thumbnail) {
        this.url_thumbnail = url_thumbnail;
    }
}
