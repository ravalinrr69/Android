package androidservices.ravalinrr.com.myapplication11;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    LocationManager locationManager;
    Button saveLocation;
    TextView displayOrEditText;
    String currentLocation;
    ItemDatabase itemDatabase = new ItemDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        saveLocation = (Button) findViewById(R.id.save_location);
        displayOrEditText = (TextView) findViewById(R.id.display_or_edit_loc);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        //check whether the network provider is enabled
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {

                //When the location changes, onLocationChanged(Location location) is triggered and location has the current location details
                @Override
                public void onLocationChanged(Location location) {

                    //get the latitude value from "location"
                    double latitude = location.getLatitude();
                    //get the longitude value from "location"
                    double longitude = location.getLongitude();

                    //Instaniate LatLng class with current location's latitude and longitude
                    LatLng currentLatLng = new LatLng(latitude, longitude);

                    //Instantiate Geocoder class to get list of addresses nearby
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                        //currentLocation = addressList.get(0).getSubLocality() + ", ";
                        currentLocation += addressList.get(0).getLocality() + ", ";
                        currentLocation += addressList.get(0).getCountryName();
                        mMap.addMarker(new MarkerOptions().position(currentLatLng).title(currentLocation));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 10f));
                        displayOrEditText.setText(currentLocation);
                    } catch (IOException e) {
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
            });
        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {

                //When the location changes, onLocationChanged(Location location) is triggered and location has the current location details
                @Override
                public void onLocationChanged(Location location) {

                    //get the latitude value from "location"
                    double latitude = location.getLatitude();
                    //get the longitude value from "location"
                    double longitude = location.getLongitude();

                    //Instaniate LatLng class with current location's latitude and longitude
                    LatLng currentLatLng = new LatLng(latitude, longitude);

                    //Instantiate Geocoder class to get list of addresses nearby
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {

                        List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 5);
                        String currentLocation = addressList.get(0).getLocality() + ", ";
                        currentLocation += addressList.get(0).getCountryName();
                        Log.d("current LatLng is: ", currentLatLng + "\n");
                        Log.d("current location is: ", currentLocation + "\n");
                        mMap.addMarker(new MarkerOptions().position(currentLatLng).title(currentLocation));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 10f));
                        displayOrEditText.setText(currentLocation);
                    } catch (IOException e) {
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
            });
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    public void saveLocation(View view) {

        String location = displayOrEditText.getText().toString();
        Log.d("**** location ***", " is: " + location);
        boolean isInserted = itemDatabase.insertLocation(location);

        //verifying if the value is inserted to database or not
        if (isInserted == true) {
            Toast.makeText(getApplicationContext(), "Value inserted", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Location not inserted", Toast.LENGTH_LONG).show();
        }

        Intent reviewIntent = new Intent(this, TrackActivity.class);
        reviewIntent.putExtra("addresss", displayOrEditText.getText().toString());
        startActivity(reviewIntent);
    }

    public void displayLocation(View view) {

    }

    public void myReviews(View view) {
        Intent intent = new Intent(this, MyReviews.class);
        startActivity(intent);
    }
}
