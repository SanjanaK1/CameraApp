package com.example.sanja.cameraproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.sanja.cameraproject.MainActivity.bitmaps;

public class ImageEditingScreen2 extends AppCompatActivity {
    ImageView picture2;
    Button backButton, saveButton;
    ImageView accessoryImage2;

    FirebaseStorage fbs = FirebaseStorage.getInstance("gs://mycamera-d13fc.appspot.com");
    static int counter = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_editing_screen2);

        picture2 = findViewById(R.id.imageViewPreview2);
        saveButton = findViewById(R.id.saveButton2);
        backButton = findViewById(R.id.backButton2);

        picture2.setImageBitmap(MainActivity.bitmaps[1]);
        picture2.setRotation(270);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StorageReference stRef = fbs.getReference();

                SimpleDateFormat form = new SimpleDateFormat("MM-dd-yyyy");
                String time = form.format(new Date());


                StorageReference myImage = stRef.child( LogIn.userEmail + "/" + time +"_" + counter++ +"" +".jpg");

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmaps[0].compress(Bitmap.CompressFormat.JPEG,100,baos);
                byte[] imageData = baos.toByteArray();
                UploadTask ut = myImage.putBytes(imageData);

                Toast.makeText(ImageEditingScreen2.this, " Saved",Toast.LENGTH_SHORT).show();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToLoadActivity = new Intent(ImageEditingScreen2.this,MainActivity.class);
                startActivity(intentToLoadActivity);
            }
        });
    }
}
