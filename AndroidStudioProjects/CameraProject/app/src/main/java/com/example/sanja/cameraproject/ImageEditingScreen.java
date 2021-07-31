package com.example.sanja.cameraproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
//import com.google.android.gms.vision.Frame;
//import com.google.android.gms.vision.face.Face;
//import com.google.android.gms.vision.face.FaceDetector;
//import android.media.FaceDetector.Builder;

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


public class ImageEditingScreen extends AppCompatActivity {
    ImageView picture;
    ImageView image1;
    ImageView accessoryImage;
    Button backButton, saveButton;

    FirebaseStorage fbs = FirebaseStorage.getInstance("gs://mycamera-d13fc.appspot.com");
    static int counter = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        System.out.println("Image Editing create 0");
        setContentView(R.layout.activity_image_editing_screen);
        picture = findViewById(R.id.imageViewPreview);
        image1 = findViewById(R.id.imageView);
        saveButton = findViewById(R.id.saveButton2);
        backButton = findViewById(R.id.backButton);

       // accessoryImage = findViewById(R.id.accessoryImage);

        final Bitmap bm = MainActivity.bitmaps[0];
        picture.setImageBitmap(bm);
        picture.setRotation(270);

        Paint rectPaint = new Paint();
        rectPaint.setStrokeWidth(5);
        rectPaint.setColor(Color.YELLOW);
        rectPaint.setStyle(Paint.Style.STROKE);

        final Bitmap tempBm = Bitmap.createBitmap(bm.getWidth(),bm.getHeight(),Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(tempBm);
        canvas.drawBitmap(tempBm,0,0,null);


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

                Toast.makeText(ImageEditingScreen.this, " Saved",Toast.LENGTH_SHORT).show();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToLoadActivity = new Intent(ImageEditingScreen.this,MainActivity.class);
                startActivity(intentToLoadActivity);
            }
        });
    }
}
