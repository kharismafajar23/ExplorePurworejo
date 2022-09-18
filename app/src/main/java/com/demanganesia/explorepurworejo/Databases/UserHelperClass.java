package com.demanganesia.explorepurworejo.Databases;

public class UserHelperClass {

    String namaLengkap, username, email, kataSandi, jenisKelamin, tanggalLahir, nomorTelefon;

    public UserHelperClass(String namaLengkap, String username, String email, String kataSandi, String jenisKelamin, String tanggalLahir, String nomorTelefon) {
        this.namaLengkap = namaLengkap;
        this.username = username;
        this.email = email;
        this.kataSandi = kataSandi;
        this.jenisKelamin = jenisKelamin;
        this.tanggalLahir = tanggalLahir;
        this.nomorTelefon = nomorTelefon;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
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

    public String getKataSandi() {
        return kataSandi;
    }

    public void setKataSandi(String kataSandi) {
        this.kataSandi = kataSandi;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getNomorTelefon() {
        return nomorTelefon;
    }

    public void setNomorTelefon(String nomorTelefon) {
        this.nomorTelefon = nomorTelefon;
    }
}
