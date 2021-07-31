package com.example.sanja.cameraproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ViewSavedImages extends AppCompatActivity {

    Button nextButton, prevButton;
    ImageView imagePreview;

    int counter = 1;
    FirebaseStorage fbs = FirebaseStorage.getInstance("gs://mycamera-d13fc.appspot.com");
    final long ONE_MEGABYTE = 1024 * 1024;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_saved_images);
        nextButton = findViewById(R.id.nextButton);
        prevButton = findViewById(R.id.prevButton);
        imagePreview = findViewById(R.id.imagePreview);

        /*StorageReference stRef = fbs.getReference();

        SimpleDateFormat form = new SimpleDateFormat("MM-dd-yyyy");
        String time = form.format(new Date());

        StorageReference myImage = stRef.child(time +"_" + counter++ +"" +".jpg");

        StorageReference imageRef = fbs.getReference().child("");
        imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                imagePreview.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ViewSavedImages.this, "No Saved Images",Toast.LENGTH_SHORT).show();
            }
        });*/

        //if no saved images:


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imagePreview.setRotation(270);


                StorageReference stRef = fbs.getReference();

                SimpleDateFormat form = new SimpleDateFormat("MM-dd-yyyy");
                String time = form.format(new Date());

                System.out.println(" ===>> " + time +"_" + counter +".jpg");

                StorageReference myImage = stRef.child( LogIn.userEmail + "/" + time +"_" + counter +".jpg");

                /*File localFile = null;
                try {
                    localFile = File.createTempFile("images","jpg");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                final File finalLocalFile = localFile;
                myImage.getFile(finalLocalFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        //taskSnapshot.
                        Bitmap bitmap = BitmapFactory.decodeFile(finalLocalFile.getAbsolutePath());
                        imagePreview.setImageBitmap(bitmap);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ViewSavedImages.this, "No More Images",Toast.LENGTH_SHORT).show();
                    }
                });*/
                myImage.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                     @Override
                     public void onSuccess(byte[] bytes) {
                         Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                         imagePreview.setImageBitmap(bitmap);
                         counter++;
                     }
                 }).addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         Toast.makeText(ViewSavedImages.this, "No More Images",Toast.LENGTH_SHORT).show();
                     }
                 });
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StorageReference stRef = fbs.getReference();

                if (counter > 1)
                counter--;

                SimpleDateFormat form = new SimpleDateFormat("MM-dd-yyyy");
                String time = form.format(new Date());

                System.out.println(" ===>> " + time +"_" + counter +".jpg");

                StorageReference myImage = stRef.child( LogIn.userEmail + "/" + time +"_" + counter +".jpg");

                //StorageReference imageRef = fbs.getReference().child("");
                myImage.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                        imagePreview.setImageBitmap(bitmap);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ViewSavedImages.this, "No More Images",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
