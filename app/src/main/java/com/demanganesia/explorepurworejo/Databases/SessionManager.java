package com.demanganesia.explorepurworejo.Databases;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    //variabel
    SharedPreferences usersSession;
    SharedPreferences.Editor editor;
    Context context;

    public static final String SESSION_USERSESSION = "userLoginSession";
    public static final String SESSION_INGATSAYA = "ingatSaya";
    private static final String IS_LOGIN = "IsLoggedIn";

    private static final String KEY_NAMALENGKAP = "namaLengkap";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String kEY_KATASANDI = "kataSandi";
    private static final String KEY_JENISKELAMIN = "jenisKelamin";
    private static final String KEY_TANGGALLAHIR = "tanggalLahir";
    private static final String KEY_NOMORTELEFON = "nomorTelefon";

    //variabel ingat saya
    private static final String IS_INGATSAYA = "IsIngatSaya";
    public static final String KEY_SESSIONUSERNAME = "username";
    public static final String KEY_SESSIONKATASANDI = "kataSandi";

    public SessionManager(Context _context, String sessionName){
        context = _context;
        usersSession = context.getSharedPreferences(sessionName, Context.MODE_PRIVATE);
        editor = usersSession.edit();
    }

    public void createLoginSession(String namaLengkap, String username, String email, String kataSandi, String jenisKelamin, String tanggalLahir, String nomorTelefon) {

        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_NAMALENGKAP, namaLengkap);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_EMAIL, email);
        editor.putString(kEY_KATASANDI, kataSandi);
        editor.putString(KEY_JENISKELAMIN, jenisKelamin);
        editor.putString(KEY_TANGGALLAHIR, tanggalLahir);
        editor.putString(KEY_NOMORTELEFON, nomorTelefon);

        editor.commit();
    }

    public HashMap<String, String> getUserDetailFromSession() {
        HashMap<String, String> userData = new HashMap<String, String>();

        userData.put(KEY_NAMALENGKAP, usersSession.getString(KEY_NAMALENGKAP, null));
        userData.put(KEY_USERNAME, usersSession.getString(KEY_USERNAME, null));
        userData.put(KEY_EMAIL, usersSession.getString(KEY_EMAIL, null));
        userData.put(kEY_KATASANDI, usersSession.getString(kEY_KATASANDI, null));
        userData.put(KEY_JENISKELAMIN, usersSession.getString(KEY_JENISKELAMIN, null));
        userData.put(KEY_TANGGALLAHIR, usersSession.getString(KEY_TANGGALLAHIR, null));
        userData.put(KEY_NOMORTELEFON, usersSession.getString(KEY_NOMORTELEFON, null));

        return userData;

    }

    public boolean cekLogin(){
        if (usersSession.getBoolean(IS_LOGIN, true)) {
            return true;
        }
        else
            return false;
    }

    public void logoutUserFromSession() {
        editor.clear();
        editor.commit();
    }

    //ingat saya
    public void createSesiIngatSaya(String username, String kataSandi) {

        editor.putBoolean(IS_INGATSAYA, true);

        editor.putString(KEY_SESSIONUSERNAME, username);
        editor.putString(KEY_SESSIONKATASANDI, kataSandi);

        editor.commit();
    }

    public HashMap<String, String> getIngatSayaDetail() {
        HashMap<String, String> userData = new HashMap<String, String>();

        userData.put(KEY_SESSIONUSERNAME, usersSession.getString(KEY_SESSIONUSERNAME, null));
        userData.put(KEY_SESSIONKATASANDI, usersSession.getString(KEY_SESSIONKATASANDI, null));

        return userData;

    }

    public boolean cekIngatSaya() {
        if (usersSession.getBoolean(IS_INGATSAYA, false)) {
            return true;
        } else
            return false;
    }
}
