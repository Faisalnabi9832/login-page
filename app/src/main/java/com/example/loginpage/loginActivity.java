package com.example.loginpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginActivity extends AppCompatActivity {

    private  FirebaseAuth.AuthStateListener mAuthnStateListener;

    EditText username, password; // Corrected variable names
    Button btnSignin;
    FirebaseAuth firebaseAuth;
    FirebaseAuth mFirebaseAuth;

    TextView tvsignup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        username = findViewById(R.id.editTextUsernameforlogin);
        password = findViewById(R.id.editTextPasswordforlogin);
        btnSignin = findViewById(R.id.buttonLogin2);
        mFirebaseAuth = FirebaseAuth.getInstance();
        tvsignup = findViewById(R.id.txtforlogin);


        mAuthnStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser != null) {
                    Toast.makeText(loginActivity.this, "login successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(loginActivity.this, HomeActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(loginActivity.this, "plz login", Toast.LENGTH_SHORT).show();
                }

            }
        };

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameId = username.getText().toString(); // Corrected variable name
                String passwordId = password.getText().toString(); // Corrected variable name

                if (usernameId.isEmpty() && passwordId.isEmpty()) {
                    Toast.makeText(loginActivity.this, "Fields are Empty!", Toast.LENGTH_SHORT).show();
                } else if (!usernameId.isEmpty() && !passwordId.isEmpty()) {
                    firebaseAuth.signInWithEmailAndPassword(usernameId, passwordId).addOnCompleteListener(loginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(loginActivity.this, "Login error plz try again", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent = new Intent(loginActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }

                        }

                    });
                } else {
                    Toast.makeText(loginActivity.this, "Error occured", Toast.LENGTH_SHORT).show();
                }

            }
        });
        tvsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

        protected void onStart(){
            super.onStart();
            mFirebaseAuth.addAuthStateListener(mAuthnStateListener);
        }



    }
