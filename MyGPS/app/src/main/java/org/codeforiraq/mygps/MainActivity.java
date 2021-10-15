package org.codeforiraq.mygps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private LocationManager locationManager; //LocationManager: this class provide access to the system location services
    private LocationListener locationListener; //LocationListener: uses for receiving notification when the device location has changed


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);  //locationManager provide access:(LOCATION_SERVICE) to system location services
        locationListener = new LocationListener() {
            
            @Override //Called when the location has changed.
            public void onLocationChanged(Location location) {

                Log.d("Location" , location.toString());
            }

                      
             @Override  //Called when the location has changed and locations are being delivered in batches.
            public void onLocationChanged(@NonNull List<Location> locations) {

            }
           
        
             @Override //Called when a provider this listener is registered with becomes enabled. (GPS WORKING)
            public void onProviderEnabled(String provider) {

            }

            @Override //Called when the provider this listener is registered with becomes disabled. (GPS NOT WORKING)
            public void onProviderDisabled(String provider) {

            }
        };


        if(Build.VERSION.SDK_INT < 23){
            //we request the location updates from the location manager (GPS WORKing)
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    0, 0, locationListener);
        }else{
                /*** Ckeck if we have permission or not***/
        //if permission.ACCESS_FINE_LOCATION &&permission.ACCESS_COARSE_LOCATION not Granted
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
               && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)  {
        //Request Permission
ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);            
                           
  
            }else {//we request the location updates from the location manager (GPS WORKing)
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        0, 0, locationListener);
            }
        }
    }


   /** ask for permission at runtime**/
    @Override//This interface is the contract for receiving the results for Runtime permission requests.
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        //if we have a grandresult and this grandresult is granted
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
    // CheckselfPermission(ACCESS_FINE_LOCATION) is granted
if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

    //we request the location updates from the location manager
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
         }
      }
   }
