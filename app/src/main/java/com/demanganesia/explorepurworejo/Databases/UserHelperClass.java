package com.demanganesia.explorepurworejo.Databases;

public class UserHelperClass {

    String nama_lengkap, username, email, kata_sandi, jenis_kelamin, tanggal_lahir, nomor_telefon, bio, url_foto_profil;

    public UserHelperClass(String nama_lengkap, String username, String email, String kata_sandi, String jenis_kelamin, String tanggal_lahir, String nomor_telefon, String bio, String url_foto_profil) {
        this.nama_lengkap = nama_lengkap;
        this.username = username;
        this.email = email;
        this.kata_sandi = kata_sandi;
        this.jenis_kelamin = jenis_kelamin;
        this.tanggal_lahir = tanggal_lahir;
        this.nomor_telefon = nomor_telefon;
        this.bio = bio;
        this.url_foto_profil = url_foto_profil;
        this.bio = " ";
        this.url_foto_profil = " ";
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public void setNama_lengkap(String nama_lengkap) {
        this.nama_lengkap = nama_lengkap;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKata_sandi() {
        return kata_sandi;
    }

    public void setKata_sandi(String kata_sandi) {
        this.kata_sandi = kata_sandi;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getTanggal_lahir() {
        return tanggal_lahir;
    }

    public void setTanggal_lahir(String tanggal_lahir) {
        this.tanggal_lahir = tanggal_lahir;
    }

    public String getNomor_telefon() {
        return nomor_telefon;
    }

    public void setNomor_telefon(String nomor_telefon) {
        this.nomor_telefon = nomor_telefon;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getUrl_foto_profil() {
        return url_foto_profil;
    }

    public void setUrl_foto_profil(String url_foto_profil) {
        this.url_foto_profil = url_foto_profil;
    }
}
