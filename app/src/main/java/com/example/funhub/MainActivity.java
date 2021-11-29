package com.example.funhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private EditText Name;
    private EditText Passwordet;
    private Button LoginButton, SignUp;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth=FirebaseAuth.getInstance();
        Name = (EditText) findViewById(R.id.username);
        Passwordet = (EditText) findViewById(R.id.password);
        LoginButton = (Button) findViewById(R.id.Login);
        SignUp=(Button)findViewById(R.id.Signup);
        progressDialog= new ProgressDialog(this);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(Name.getText().toString(), Passwordet.getText().toString());
            }
        });
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private  void  validate(String username, String userPassword) {
        String Username = Name.getText().toString();
        String password = Passwordet.getText().toString();
        if (TextUtils.isEmpty(username)) {
            Name.setError("Enter Username");
            return;
        } else if (TextUtils.isEmpty(password)) {
            Passwordet.setError("Enter password");
            return;
        }
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Successfully login", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, HomePage.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Oooh Login failed", Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();

            }

        });
    }
}