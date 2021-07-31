package com.example.sanja.cameraproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.media.FaceDetector;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Policy;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class MainActivity extends AppCompatActivity {

    private Camera cam;
    private Camera camBack;
    private FirebaseAuth  mAuth;

    private MyCamera myCamera;
    ImageView image1, image2,image3;
    RadioButton colorButton;
    RadioButton monoButton;
    static Bitmap[] bitmaps = new Bitmap[3];
    Button captureButton,newButton, viewButton, logOutButton;
    static final int REQUEST_IMAGE_CAPTURE=1;
    Boolean captureButtonEnabled = true;


    String userEmail = LogIn.userEmail;

    public static final String TAG = "LOG_TAG";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image1 = findViewById(R.id.imageView);
        image2 = findViewById(R.id.imageView2);
        image3 = findViewById(R.id.imageView3);
        captureButton = findViewById(R.id.button);
        newButton = findViewById(R.id.button2);
        colorButton = findViewById(R.id.radioButtonColor);
        monoButton = findViewById(R.id.radioButtonMono);
        viewButton = findViewById(R.id.viewButton);
        logOutButton = findViewById(R.id.logOutButton);


        // Create an instance of Camera
        cam = MyCamera.getCameraInstance(1);
        camBack = MyCamera.getCameraInstance(0);


        mAuth  = FirebaseAuth.getInstance();

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToLoadActivity = new Intent(MainActivity.this,LogIn.class);
                startActivity(intentToLoadActivity);
            }
        });

       /* mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });  */


        // Create our Preview view and set it as the content of our activity.
        System.out.println("-----> Before camera constructor");
        myCamera = new MyCamera(this, cam);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        System.out.println("-----> After camera constructor");
        preview.addView(myCamera);
        System.out.println("-----> After Add view");

       /* FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference myRef = db.getReference("Messages");
        myRef.setValue("Hello Sanjana"); */



       /*service firebase.storage
       {
        match /b/{bucket}/o
        {
        match /{allPaths=**}
        {
        allow read, write: if request.auth != null;
                }
            }
        }
                */

        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToLoadActivity = new Intent(MainActivity.this,ImageEditingScreen.class);
                //Bundle extras = new Bundle();

                //extras.putParcelable("imagebitmap", bitmaps[0]);

                //intentToLoadActivity.putExtras(extras);
                System.out.println("  Image Editing create 12");
                startActivity(intentToLoadActivity);

            }
        });

        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToLoadActivity = new Intent(MainActivity.this,ImageEditingScreen2.class);
                //Bundle extras = new Bundle();

                //extras.putParcelable("imagebitmap", bitmaps[0]);

                //intentToLoadActivity.putExtras(extras);
                System.out.println("  Image Editing create 12");
                startActivity(intentToLoadActivity);

            }
        });

        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToLoadActivity = new Intent(MainActivity.this,ImageEditingScreen3.class);
                //Bundle extras = new Bundle();

                //extras.putParcelable("imagebitmap", bitmaps[0]);

                //intentToLoadActivity.putExtras(extras);
                System.out.println("  Image Editing create 12");
                startActivity(intentToLoadActivity);

            }
        });

        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Camera.Parameters p = cam.getParameters();
                    p.setColorEffect(Camera.Parameters.EFFECT_NONE);
                    cam.setParameters(p);
                }catch (Exception ex){
                    System.out.println(" ---->" + ex.getMessage());
                }
            }
        });

        monoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Camera.Parameters p = cam.getParameters();
                    p.setColorEffect(Camera.Parameters.EFFECT_MONO);
                    cam.setParameters(p);
                }catch (Exception ex){
                    System.out.println(" ---->" + ex.getMessage());
                }
            }
        });


        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToLoadActivity = new Intent(MainActivity.this,ViewSavedImages.class);
                startActivity(intentToLoadActivity);
            }
        });

        final Camera.PictureCallback mPicture = new Camera.PictureCallback() {

            @Override
            public void onPictureTaken(byte[] data, Camera camera) {


                System.out.println("-----------> On picture taken");
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                bitmaps[2] = bitmaps[1];
                bitmaps[1] = bitmaps[0];
                bitmaps[0] = bitmap;

                image1.setImageBitmap(bitmaps[0]);
                image1.setRotation(270);
                image2.setImageBitmap(bitmaps[1]);
                image2.setRotation(270);
                image3.setImageBitmap(bitmaps[2]);
                image3.setRotation(270);
                //camera.release();



            }
        };

        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (captureButtonEnabled) {
                    cam.takePicture(null, null, mPicture);
                    captureButtonEnabled = false;
                }

                else {
                    Toast.makeText(MainActivity.this, "Click New to take another picture",Toast.LENGTH_SHORT).show();
                }
                //captureButton.setEnabled(false);


            }
        });
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCamera.surfaceCreated(myCamera.getSurfaceHolder());
                captureButtonEnabled = true;


            }
        });

    }

    public void dispatchTakePictureIntent(android.view.View v){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode,Intent data)
    {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap)extras.get("data");
            image1.setImageBitmap(imageBitmap);

        }
    }

}
