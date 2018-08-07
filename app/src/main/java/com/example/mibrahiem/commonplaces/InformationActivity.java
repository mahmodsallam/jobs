package com.example.mibrahiem.commonplaces;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import static android.R.attr.data;

public class InformationActivity extends Activity {
    private static final int PLACE_PICKER_REQUEST = 1;
    private Button location, sendData;
    private TextView locationName;
    private EditText name, salary, phone;
    DatabaseReference databaseReference;
    RadioGroup group;
    double latitude;
    double longitude;
    String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_information);
        name = (EditText) findViewById(R.id.et_name);
        salary = (EditText) findViewById(R.id.et_salary);
        phone = (EditText) findViewById(R.id.et_phone);
        group = (RadioGroup) findViewById(R.id.RGroup);
        location = (Button) findViewById(R.id.bt_location);
        locationName = (TextView) findViewById(R.id.tv_location);
        sendData = (Button) findViewById(R.id.bt_insert);
        sendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = name.getText().toString();
                String userSalary = salary.getText().toString();
                String userNumber = phone.getText().toString();
                int chose = group.getCheckedRadioButtonId();
                switch (chose) {
                    case 1:
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Electrician");
                        Map<String, Object> map1 = new HashMap<String, Object>();
                        key = databaseReference.push().getKey();
                        databaseReference.updateChildren(map1);
                        DatabaseReference databaseReference1 = databaseReference.child(key);
                        Map<String, Object> map11 = new HashMap<String, Object>();
                        map11.put("userName ", userName);
                        map11.put("userSalary", userSalary);
                        map11.put("userNumber", userNumber);
                        map11.put("latitude", latitude);
                        map11.put("longitude", longitude);
                        databaseReference1.updateChildren(map11);
//                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Electrician");
//                        databaseReference.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                for (DataSnapshot s : dataSnapshot.getChildren()) {
//                                    double x = (double) s.child("latitude").getValue();
//                                    double y = (double) s.child("longitude").getValue();
//                                 }
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//
//                            }
//                        });
                        break;
                    case 2:
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Plumber");
                        Map<String, Object> map2 = new HashMap<String, Object>();
                        key = databaseReference.push().getKey();
                        databaseReference.updateChildren(map2);
                        DatabaseReference databaseReference2 = databaseReference.child(key);
                        Map<String, Object> map22 = new HashMap<String, Object>();
                        map22.put("userName ", userName);
                        map22.put("userSalary", userSalary);
                        map22.put("userNumber", userNumber);
                        map22.put("latitude", latitude);
                        map22.put("longitude", longitude);
                        databaseReference2.updateChildren(map22);
//                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Plumber");
//                        databaseReference.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                for (DataSnapshot s : dataSnapshot.getChildren()) {
//                                    double x = (double) s.child("latitude").getValue();
//                                    double y = (double) s.child("longitude").getValue();
//
//
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//
//                            }
//                        });
                        break;
                    case 3:
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Mechanic");
                        Map<String, Object> map3 = new HashMap<String, Object>();
                        key = databaseReference.push().getKey();
                        databaseReference.updateChildren(map3);
                        DatabaseReference databaseReference3 = databaseReference.child(key);
                        Map<String, Object> map33 = new HashMap<String, Object>();
                        map33.put("userName ", userName);
                        map33.put("userSalary", userSalary);
                        map33.put("userNumber", userNumber);
                        map33.put("latitude", latitude);
                        map33.put("longitude", longitude);
                        databaseReference3.updateChildren(map33);
//                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Mechanic");
//                        databaseReference.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                for (DataSnapshot s : dataSnapshot.getChildren()) {
//                                    double x = (double) s.child("latitude").getValue();
//                                    double y = (double) s.child("longitude").getValue();
//
//
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//
//                            }
//                        });
//
                        break;
                }


            }
        });
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(InformationActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, InformationActivity.this);
                LatLng x = place.getLatLng();
                latitude = x.latitude;
                longitude = x.longitude;
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, " latitude " + latitude + " longitude " + longitude, Toast.LENGTH_SHORT).show();
                locationName.setText(toastMsg);
            }
        }
    }
}
