package com.example.loginpage;

import android.content.Intent;
import android.graphics.Paint;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {


  EditText username, password ,fname,lname,confirmpass; // Corrected variable names
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
    fname= findViewById(R.id.editTextforfirstname);
    lname=findViewById(R.id.editTextforlastname);
    confirmpass=findViewById(R.id.editTextPasswordconfirm);
    tvsignin.setPaintFlags(tvsignin.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);



    btnSignup.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String usernameId = username.getText().toString();
        String passwordId = password.getText().toString();
        String fnameid =fname.getText().toString();
        String lnameid =lname.getText().toString();
        String confirmpasswardid =confirmpass.getText().toString();

        if (usernameId.isEmpty() || passwordId.isEmpty() || fnameid.isEmpty() || lnameid.isEmpty() || confirmpasswardid.isEmpty()) {
          Toast.makeText(MainActivity.this, "Fields are Empty!", Toast.LENGTH_SHORT).show();
        } else if (!usernameId.isEmpty() && !passwordId.isEmpty() && !fnameid.isEmpty() && !lnameid.isEmpty() && !confirmpasswardid.isEmpty()) {
          firebaseAuth.createUserWithEmailAndPassword(usernameId, passwordId)
                  .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                      if (task.isSuccessful()) {
                        // Sign up successful, now store additional information
                        String uid = firebaseAuth.getCurrentUser().getUid();
                        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
                        userRef.child("fname").setValue(fnameid);
                        userRef.child("lname").setValue(lnameid);
                        userRef.child("confirmpass").setValue(confirmpasswardid);

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
