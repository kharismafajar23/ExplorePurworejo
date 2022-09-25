package com.demanganesia.explorepurworejo;

public class JelajahWisata {

    private String url_thumbnail, nama_wisata, kategori;

    public JelajahWisata(String url_thumbnail, String nama_wisata, String kategori) {
        this.url_thumbnail = url_thumbnail;
        this.nama_wisata = nama_wisata;
        this.kategori = kategori;
    }

    public JelajahWisata() {
    }

    public String getUrl_thumbnail() {
        return url_thumbnail;
    }

    public void setUrl_thumbnail(String url_thumbnail) {
        this.url_thumbnail = url_thumbnail;
    }

    public String getNama_wisata() {
        return nama_wisata;
    }

    public void setNama_wisata(String nama_wisata) {
        this.nama_wisata = nama_wisata;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }
}
