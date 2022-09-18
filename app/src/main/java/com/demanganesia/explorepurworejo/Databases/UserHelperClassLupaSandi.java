package com.demanganesia.explorepurworejo.Databases;

public class UserHelperClassLupaSandi {

    String username, nomorTelefon;

    UserHelperClassLupaSandi() {}

    public UserHelperClassLupaSandi(String username, String nomorTelefon) {
        this.username = username;
        this.nomorTelefon = nomorTelefon;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNomorTelefon() {
        return nomorTelefon;
    }

    public void setNomorTelefon(String nomorTelefon) {
        this.nomorTelefon = nomorTelefon;
    }
}
