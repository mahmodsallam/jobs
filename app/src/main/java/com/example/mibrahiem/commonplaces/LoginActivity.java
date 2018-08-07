package com.example.mibrahiem.commonplaces;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
Button btLogin;
EditText etmail,etpassword;
    private FirebaseAuth mAuth;

String mail, pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etmail= (EditText) findViewById(R.id.et_logMail);
        etpassword= (EditText) findViewById(R.id.et_logpass);

        mAuth = FirebaseAuth.getInstance();
        btLogin= (Button) findViewById(R.id.bt_login);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mail=etmail.getText().toString();
                pass=etpassword.getText().toString();
                userLogin();
            }
        });

    }
    private void userLogin() {

        mAuth.signInWithEmailAndPassword(mail,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                         if(task.isSuccessful()){

                             Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                             finish();

                         }
                        else
                            Toast.makeText(LoginActivity.this, "Eror", Toast.LENGTH_SHORT).show();                    }
                });
    }

}
