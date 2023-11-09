package proyectoDAM.giac_app_v01.menuPrincipal_U;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.List;
import java.util.Locale;

import proyectoDAM.giac_app_v01.R;

public class PopupDirActivity extends AppCompatActivity implements LocationListener {

    TextView textView_location;
    LocationManager locationManager;
    Button mapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_dir);

        //CREACION DEL POP-UP
        DisplayMetrics medidaventana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidaventana);
        int ancho = medidaventana.widthPixels;
        int alto = medidaventana.heightPixels;
        getWindow().setLayout((int)(ancho * 0.85), (int)(alto*0.5));
        ////////////////////////////////////////////////////////////////
        textView_location = findViewById(R.id.text_location);
        //Runtime permissions
        if (ContextCompat.checkSelfPermission(PopupDirActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(PopupDirActivity.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }

        //CREACION DE LA DIRECCION
        getLocation();

        //CREACION DEL MAPA
        mapa = findViewById(R.id.button_location);
        mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PopupDirActivity.this, PopupMapaActivity.class));
            }
        });

    }

    @SuppressLint("MissingPermission")
    private void getLocation() {

        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5, PopupDirActivity.this);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(this, ""+location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();
        try {
            Geocoder geocoder = new Geocoder(PopupDirActivity.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String address = addresses.get(0).getAddressLine(0);

            textView_location.setText(address);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}