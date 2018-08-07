package com.example.mibrahiem.commonplaces;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String cityName,provider;
    Button type;
    LocationManager locationManager;
    LocationListener locationListener;
    Location location;
    DatabaseReference databaseReferenc ;
    double latitude ;
    double longitude ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
         locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider=locationManager.getBestProvider(new Criteria(),false);
         location=locationManager.getLastKnownLocation(provider);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                 latitude = location.getLatitude();
                 longitude = location.getLongitude();
                LatLng place = new LatLng(latitude, longitude);
                mMap.animateCamera(CameraUpdateFactory.zoomIn());
                mMap.addMarker(new MarkerOptions().position(place).title("you are here"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 15));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);

            }
        };
        help();

    }
    public void help() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.
                permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.
                checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager
                .PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest
                                .permission.ACCESS_COARSE_LOCATION, Manifest
                                .permission.ACCESS_FINE_LOCATION, Manifest
                                .permission.INTERNET}
                        , 10);
            }
            return;

        } else {

            locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);


        }
    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }


    private void configure_button() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.INTERNET}
                        , 10);

            }
            return;
        }
    }

String worker;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        worker=getIntent().getStringExtra("job");
        final ArrayList<DataObject> arr=new ArrayList<>();
        switch (worker){
            case "Electrician":
                    databaseReferenc = FirebaseDatabase.getInstance().getReference().child("Electrician");
                    databaseReferenc.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot s : dataSnapshot.getChildren()) {
                                double x = (double) s.child("latitude").getValue();
                                double y = (double) s.child("longitude").getValue();
                                String name = (String) s.child("userName").getValue();
                                String salary = (String) s.child("userSalary").getValue();
                                String phone = (String) s.child("userNumber").getValue();
                                String key =  s.getKey();
                                Log.i("key",key);
                                arr.add(new DataObject(x,y,key,name,salary,phone));
                            }
                            Distance[] asd=new Distance[arr.size()];
                            DataObject myPosition=new DataObject(latitude,longitude,"","","","");
                            knn(arr,myPosition,asd);
                            for(int i=0;i<5;i++){
                                for(int j=0;j<arr.size();j++){
                                    if(asd[i].key==arr.get(j).key){
                                 double x=arr.get(j).x;
                                 double y=arr.get(j).y;
                                        String name=arr.get(j).name;
                                        String salary=arr.get(j).salary;
                                        String phone=arr.get(j).phone;
                                LatLng place = new LatLng(x, y);
                                mMap.addMarker(new MarkerOptions().position(place).title(name+salary+" "+phone));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 15));
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
                                    }


                                }
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                    break;
            case "mechanic":
                databaseReferenc = FirebaseDatabase.getInstance().getReference().child("Mechanic");
                databaseReferenc.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot s : dataSnapshot.getChildren()) {
                            double x = (double) s.child("latitude").getValue();
                            double y = (double) s.child("longitude").getValue();
                            String name = (String) s.child("name").getValue();
                            String salary = (String) s.child("userSalary").getValue();
                            String phone = (String) s.child("userNumber").getValue();
                            String key =  s.getKey();
                            Log.i("key",key);
                            arr.add(new DataObject(x,y,key,name,salary,phone));
                        }
                        Distance[] asd=new Distance[arr.size()];
                        DataObject myPosition=new DataObject(latitude,longitude,"","","","");
                        knn(arr,myPosition,asd);
                        for(int i=0;i<5;i++){
                            for(int j=0;j<arr.size();j++){
                                if(asd[i].key==arr.get(j).key){
                                    double x=arr.get(j).x;
                                    double y=arr.get(j).y;
                                    String name=arr.get(j).name;
                                    String salary=arr.get(j).salary;
                                    String phone=arr.get(j).phone;
                                    LatLng place = new LatLng(x, y);
                                    mMap.addMarker(new MarkerOptions().position(place).title(name+salary+" "+phone));
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 15));
                                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
                                }
                            }
                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                break;
            case "plumer":
                databaseReferenc = FirebaseDatabase.getInstance().getReference().child("Plumber");
                databaseReferenc.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot s : dataSnapshot.getChildren()) {
                             ;
                            double x = (double) s.child("latitude").getValue();
                            double y = (double) s.child("longitude").getValue();
                            String name = (String) s.child("name").getValue();
                            String salary = (String) s.child("userSalary").getValue();
                            String phone = (String) s.child("userNumber").getValue();
                            String key =  s.getKey();
                            Log.i("key",key);
                            arr.add(new DataObject(x,y,key,name,salary,phone));
                        }
                        Distance[] asd=new Distance[arr.size()];
                        DataObject myPosition=new DataObject(latitude,longitude,"","","","");
                        knn(arr,myPosition,asd);
                        for(int i=0;i<5;i++){
                            for(int j=0;j<arr.size();j++){
                                if(asd[i].key==arr.get(j).key){
                                    double x=arr.get(j).x;
                                    double y=arr.get(j).y;
                                    String name=arr.get(j).name;
                                    String salary=arr.get(j).salary;
                                    String phone=arr.get(j).phone;
                                    LatLng place = new LatLng(x, y);
                                    mMap.addMarker(new MarkerOptions().position(place).title(name+salary+" "+phone));
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 15));
                                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                break;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    10);
            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    10);
        }
    }
    private void knn(ArrayList<DataObject> arr, DataObject dataObject, Distance[] arr2) {
        for(int i=0;i<arr.size();i++){
            Distance distance=new Distance(arr.get(i).key,getdistance(dataObject,arr.get(i)));
            arr2[i]=distance;
        }
        sortarr(arr2);
    }
    private void sortarr(Distance[] arr2) {

        for (int i = 0; i < arr2.length; i++) {
            for (int j = 0; j < arr2.length; j++) {
                if (arr2[i].d < arr2[j].d) {
                    Distance temp = arr2[i];
                    arr2[i] = arr2[j];
                    arr2[j] = temp;
                }
            }


        }
    }
    private double getdistance(DataObject dataObject1, DataObject dataObject2) {
        double m=(dataObject1.x-dataObject2.x)*(dataObject1.x-dataObject2.x)+(dataObject1.y-dataObject2.y)*(dataObject1.y-dataObject2.y);
    double g=Math.sqrt(m);
        return g;
    }

}





