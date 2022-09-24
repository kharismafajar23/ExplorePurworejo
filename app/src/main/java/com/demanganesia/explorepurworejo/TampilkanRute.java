package com.demanganesia.explorepurworejo;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.demanganesia.explorepurworejo.databinding.ActivityTampilkanRuteBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class TampilkanRute extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityTampilkanRuteBinding binding;
    TextView testaja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_tampilkan_rute);

        testaja = findViewById(R.id.TV_alamat_wisata_tampilkan_rute);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);

        String latitude = getIntent().getStringExtra("latitude_user");

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-7.700722956718504, 110.02520481018935);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}