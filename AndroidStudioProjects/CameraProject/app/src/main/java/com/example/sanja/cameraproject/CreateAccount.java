package com.example.sanja.cameraproject;

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
import com.google.firebase.auth.FirebaseUser;

public class CreateAccount extends AppCompatActivity
{
    EditText email, password;
    Button signUpButton;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        signUpButton = findViewById(R.id.createAccountButton);
        email = findViewById(R.id.signUpEmail);
        password = findViewById(R.id.signUpPassword);



        System.out.println(" ---???  Here");

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(CreateAccount.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                System.out.println(" ---???  Here1");
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    //Log.d(TAG, "createUserWithEmail:success");
                                    System.out.println(" ---???  Here2");
                                    Intent intentToLoadActivity = new Intent(CreateAccount.this,LogIn.class);
                                    startActivity(intentToLoadActivity);

                                    //System.out.println(" ---???  Here3");

                                    //System.out.println("Create Account :success");
                                    //FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(CreateAccount.this, "Create Account :success "  + email.getText().toString(), Toast.LENGTH_SHORT).show();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(CreateAccount.this, "Couldnt create Account",
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }

                                // ...
                            }
                        });

            }
        });
    }
}
