package com.demanganesia.explorepurworejo;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DetailWisata extends AppCompatActivity implements LocationListener, View.OnClickListener {

    TextView TVKategori, TVNamaWisata, TVLokasi, TVJamOperasional, TVDeksripsiWisata, TVHargaTiket;
    Button BtnTampilkanRute;
    ImageView BtnKembaliDet;
    ImageSlider ImageSliderWisata;

    DatabaseReference databaseReference;

    double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_wisata);
        //menghilangkan status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        TVKategori = findViewById(R.id.TV_kategori_wisata);
        TVNamaWisata = findViewById(R.id.TV_nama_wisata);
        TVLokasi = findViewById(R.id.TV_lokasi_wisata);
        TVJamOperasional = findViewById(R.id.TV_jam_operasional);
        TVDeksripsiWisata = findViewById(R.id.TV_deksripsi_wisata);
        TVHargaTiket = findViewById(R.id.TV_harga_tiket);
        BtnTampilkanRute = findViewById(R.id.Btn_tampilkan_rute);
        ImageSliderWisata = findViewById(R.id.image_slider_wisata);
        BtnKembaliDet = findViewById(R.id.IV_kembali_detail_wisata);

        CekGPS();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Wisata");
        String nama_wisata = getIntent().getStringExtra("nama_wisata_key");

        databaseReference.child(nama_wisata).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    //ambil data dari database
                    String _kategori = dataSnapshot.child("kategori").getValue().toString();
                    String _nama_wisata = dataSnapshot.child("nama_wisata").getValue().toString();
                    String _lokasi = dataSnapshot.child("alamat").getValue().toString();
                    String _jam_operasional = dataSnapshot.child("jam_operasional").getValue().toString();
                    String _deskripsi_wisata = dataSnapshot.child("deskripsi").getValue().toString();
                    String _harga_tiket = dataSnapshot.child("harga_tiket").getValue().toString();
                    String _url_image_1 = dataSnapshot.child("url_image_1").getValue().toString();
                    String _url_image_2 = dataSnapshot.child("url_image_2").getValue().toString();
                    String _url_image_3 = dataSnapshot.child("url_image_3").getValue().toString();

                    //menerapkan data dari database
                    TVKategori.setText(_kategori);
                    TVNamaWisata.setText(_nama_wisata);
                    TVLokasi.setText(_lokasi);
                    TVJamOperasional.setText(_jam_operasional);
                    TVDeksripsiWisata.setText(_deskripsi_wisata);
                    TVHargaTiket.setText(_harga_tiket);

                    //image
                    ArrayList<SlideModel> slideModels = new ArrayList<>();
                    slideModels.add(new SlideModel(_url_image_1, ScaleTypes.CENTER_CROP));
                    slideModels.add(new SlideModel(_url_image_2, ScaleTypes.CENTER_CROP));
                    slideModels.add(new SlideModel(_url_image_3, ScaleTypes.CENTER_CROP));

                    ImageSliderWisata.setImageList(slideModels, ScaleTypes.CENTER_CROP);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        BtnTampilkanRute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               databaseReference.child(nama_wisata).addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       String _latitude_wisata = dataSnapshot.child("latitude").getValue().toString();
                       String _longitude_wisata = dataSnapshot.child("longitude").getValue().toString();

                       Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" + latitude + ","+ longitude + "&daddr="+ _latitude_wisata+ "," + _longitude_wisata));
                       startActivity(intent);
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });
            }
        });

        BtnKembaliDet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void CekGPS() {
        try {

            /* Pengecekan GPS hidup / tidak */
            LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Lokasi perlu diaktifkan");
                builder.setMessage("Aktifkan lokasi untuk melanjutkan ?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int witch) {
                        Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(i);
                    }
                });

                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int witch) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        } catch (Exception e) {
            // TODO: handle exception

        }
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

        // menampilkan status google play service
        if (status != ConnectionResult.SUCCESS) {
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();
        } else {
            // Google Play Service Tersedia
            try {
                LocationManager locationManager = (LocationManager)
                        getSystemService(LOCATION_SERVICE);

                // Membuat Kriteria Untuk Penumpangan Provider
                Criteria criteria = new Criteria();

                // Mencari Provider Terbaik
                String provider = locationManager.getBestProvider(criteria, true);

                // Mendapatkan Lokasi Terakhir
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                Location location = locationManager.getLastKnownLocation(provider);

                if (location != null) {
                    onLocationChanged(location);
                }
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.requestLocationUpdates(provider, 5000, 0, this);
            } catch (Exception e){
            }
        }
    }
    @Override
    public void onLocationChanged(Location lokasi){
        //TODO Auto-generated method stub
        latitude = lokasi.getLatitude();
        longitude = lokasi.getLongitude();
    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
        LocationListener.super.onLocationChanged(locations);
    }

    @Override
    public void onFlushComplete(int requestCode) {
        LocationListener.super.onFlushComplete(requestCode);
    }

    @Override
    public void onProviderDisabled(String provider){
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String provider){
        // TODO Auto-generated method stud
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras){
        // TODO Auto-generated method stub
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}