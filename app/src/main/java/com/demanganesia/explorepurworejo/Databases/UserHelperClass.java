package com.demanganesia.explorepurworejo.Databases;

public class UserHelperClass {

    String namaLengkapIni, usernameIni, emailIni, kataSandiIni, jenisKelaminIni, tanggalLahirIni, nomorTelefonIni;

    public UserHelperClass(String username, String nomorTelefon) {}

    public UserHelperClass(String nama_lengkap, String username, String email, String kata_sandi, String jenis_kelamin, String tanggal_lahir, String nomor_telefon) {
        this.namaLengkapIni = nama_lengkap;
        this.usernameIni = username;
        this.emailIni = email;
        this.kataSandiIni = kata_sandi;
        this.jenisKelaminIni = jenis_kelamin;
        this.tanggalLahirIni = tanggal_lahir;
        this.nomorTelefonIni = nomor_telefon;
    }

    public String getNamaLengkap() {
        return namaLengkapIni;
    }

    public void setNamaLengkap(String nama_lengkap) {
        this.namaLengkapIni = nama_lengkap;
    }

    public String getUsername() {
        return usernameIni;
    }

    public void setUsername(String username) {
        this.usernameIni = username;
    }

    public String getEmail() {
        return emailIni;
    }

    public void setEmail(String email) {
        this.emailIni = email;
    }

    public String getKataSandi() {
        return kataSandiIni;
    }

    public void setKataSandi(String kata_sandi) {
        this.kataSandiIni = kata_sandi;
    }

    public String getJenisKelamin() {
        return jenisKelaminIni;
    }

    public void setJenisKelamin(String jenis_kelamin) {
        this.jenisKelaminIni = jenis_kelamin;
    }

    public String getTanggalLahir() {
        return tanggalLahirIni;
    }

    public void setTanggalLahir(String tanggal_lahir) {
        this.tanggalLahirIni = tanggal_lahir;
    }

    public String getNomorTelefon() {
        return nomorTelefonIni;
    }

    public void setNomorTelefon(String nomor_telefon) {
        this.nomorTelefonIni = nomor_telefon;
    }
}
