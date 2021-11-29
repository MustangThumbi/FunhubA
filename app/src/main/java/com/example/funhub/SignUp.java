package com.example.funhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {
    private EditText email;
    private EditText Password;
    private EditText confirm_password;
    private TextView Info;
    private Button SignupButton;
    private int counter = 5;
    private FirebaseAuth firebaseAuth;
    private TextView Signswitch;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firebaseAuth = FirebaseAuth.getInstance();
        email = (EditText) findViewById(R.id.emailid);
        Password = (EditText) findViewById(R.id.pass);
        confirm_password = (EditText) findViewById(R.id.confirmpass);
        SignupButton = (Button) findViewById(R.id.Signup);
        progressDialog= new ProgressDialog(this);
        Signswitch = findViewById(R.id.Logintv);
        SignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Signup();

            }
        });
        Signswitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, MainActivity.class);
                startActivity(intent);
                finish();
            }


        });

    }

    private void Signup() {
        String emailid = email.getText().toString();
        String pass = Password.getText().toString();
        String confirmpass = confirm_password.getText().toString();
        if (TextUtils.isEmpty(emailid)) {
            email.setError("Please enter Username");
            return;
        } else if (TextUtils.isEmpty(pass)) {
            Password.setError("Please enter Password");
            return;
        } else if (TextUtils.isEmpty(confirmpass)) {
            confirm_password.setError("Please confirm Password");
            return;


        } else if (!pass.equals(confirmpass)) {
            confirm_password.setError("Different Password");
            return;
        } else if (pass.length() < 5) {
            Password.setError("Password too weak");
            return;

        }
        else if(!isValidEmail(emailid)){
            email.setError("Invalid email");
            return;
        }

        progressDialog.setMessage(" please wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth.createUserWithEmailAndPassword(emailid, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUp.this, "Successfully registered", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SignUp.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(SignUp.this, "Ah sign up failed", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }


                }
        );
    }

    private boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}