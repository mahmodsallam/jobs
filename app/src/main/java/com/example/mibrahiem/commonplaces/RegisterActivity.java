package com.example.mibrahiem.commonplaces;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;

public class RegisterActivity extends AppCompatActivity {
    Spinner spinner;
    EditText etMail,etPassword;
    String email,password;
    private FirebaseAuth mAuth;
    Button btReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etMail= (EditText) findViewById(R.id.et_regMail);
        etPassword =  (EditText) findViewById(R.id.et_regpass);
        mAuth = FirebaseAuth.getInstance();
        btReg= (Button) findViewById(R.id.bt_register);
        btReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            rgister();
        }

        });

}

    private void rgister() {
        email=etMail.getText().toString();
        password=etPassword.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                            finish();
                            Toast.makeText(RegisterActivity.this, "Done", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                             FirebaseAuthException e = (FirebaseAuthException )task.getException();
                            Toast.makeText(RegisterActivity.this, " failed."+e.getMessage(),Toast.LENGTH_SHORT).show();
                            Log.i("aaa",e.getMessage());
                        }

                    }
                });
        }
}