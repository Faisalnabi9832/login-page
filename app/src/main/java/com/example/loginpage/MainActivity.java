package com.example.loginpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


  EditText username, password; // Corrected variable names
  Button btnSignup;
  FirebaseAuth firebaseAuth;

  TextView tvsignin;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
// Add this line in the onCreate method of your main application class or main activity
    FirebaseApp.initializeApp(this);

    firebaseAuth = FirebaseAuth.getInstance();
    username = findViewById(R.id.editTextUsername);
    password = findViewById(R.id.editTextPassword);
    btnSignup = findViewById(R.id.buttonLogin);
    tvsignin=findViewById(R.id.tvsigin);



    btnSignup.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String usernameId = username.getText().toString(); // Corrected variable name
        String passwordId = password.getText().toString(); // Corrected variable name

        if (usernameId.isEmpty() && passwordId.isEmpty()) {
          Toast.makeText(MainActivity.this, "Fields are Empty!", Toast.LENGTH_SHORT).show();
        } else if (!usernameId.isEmpty() && !passwordId.isEmpty()) {
          firebaseAuth.createUserWithEmailAndPassword(usernameId, passwordId)
                  .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                      if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Sign up successful!", Toast.LENGTH_SHORT).show();
                      } else {
                        Toast.makeText(MainActivity.this, "Sign up unsuccessful: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                      }
                    }
                  });

        } else {
          Toast.makeText(MainActivity.this, "Error Occurred or credential is not well", Toast.LENGTH_SHORT).show();
        }
      }
    });

    tvsignin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent=new Intent(MainActivity.this,loginActivity.class);
        startActivity(intent);
      }
    });
  }
}
