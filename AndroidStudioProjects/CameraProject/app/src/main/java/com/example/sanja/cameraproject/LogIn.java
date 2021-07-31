package com.example.sanja.cameraproject;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;

public class LogIn extends AppCompatActivity {
    EditText emailText, password;
    Button button, createAccountButton;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    static String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        emailText= findViewById(R.id.editText);
        password= findViewById(R.id.editText2);
        button = findViewById(R.id.button3);
        createAccountButton = findViewById(R.id.createAccountButton);


        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToLoadActivity = new Intent(LogIn.this,CreateAccount.class);
                startActivity(intentToLoadActivity);
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                System.out.println(emailText.getText().toString() + " ===>>>" + password.getText().toString());

                mAuth.signInWithEmailAndPassword(emailText.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(LogIn.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    userEmail = emailText.getText().toString();
                                    Intent intentToLoadActivity = new Intent(LogIn.this,MainActivity.class);
                                    startActivity(intentToLoadActivity);

                                    System.out.println("signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(LogIn.this, "Welcome "  + emailText.getText().toString(), Toast.LENGTH_SHORT).show();


                                    //updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    System.out.println("signInWithEmail:failure" + task.getException());
                                    Toast.makeText(LogIn.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }

                                // ...
                            }
                        });


            }
        });





    }
}
