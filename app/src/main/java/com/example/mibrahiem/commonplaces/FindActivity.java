package com.example.mibrahiem.commonplaces;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FindActivity extends Activity {
    private  int who =1;
    private RadioGroup radioGroup;
    private RadioButton mechanic,plumber,electrician;
    private Button findLocation;
    private EditText count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_find);
        radioGroup= (RadioGroup) findViewById(R.id.bt_findRadioGroup);
        mechanic= (RadioButton) findViewById(R.id.rb_findMech);
        electrician= (RadioButton) findViewById(R.id.rb_findElec);
        plumber= (RadioButton) findViewById(R.id.rb_findPlumer);
        findLocation= (Button) findViewById(R.id.bt_findLocation);


        findLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=radioGroup.getCheckedRadioButtonId();
                Intent intent=new Intent(FindActivity.this,MapsActivity.class);
                switch (id) {
                    case R.id.rb_findElec:
                        Toast.makeText(FindActivity.this, "Electrician", Toast.LENGTH_SHORT).show();
                        intent.putExtra("job","Electrician");
                        startActivity(intent);
                                                             break;
                    case R.id.rb_findMech:
                        Toast.makeText(FindActivity.this, "mechanic", Toast.LENGTH_SHORT).show();
                        intent.putExtra("job","mechanic");
                        startActivity(intent);
                                                                break;
                    case R.id.rb_findPlumer:
                        Toast.makeText(FindActivity.this, "plumer", Toast.LENGTH_SHORT).show();
                        intent.putExtra("job","plumer");
                        startActivity(intent);
                                                                break;
                    default:
                        Toast.makeText(FindActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                                                  break;
                }


            }
        });

    }



    }

