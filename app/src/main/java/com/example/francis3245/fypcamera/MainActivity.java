package com.example.francis3245.fypcamera;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.kosalgeek.android.photoutil.ImageBase64;

import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {
    //123
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView cameraimageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button cambutton = (Button) findViewById(R.id.CameraButton);
        cameraimageview = (ImageView) findViewById(R.id.CameraImageView);

        Button viewbutton = (Button) findViewById(R.id.ViewGallery);
        viewbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GalleryActivity.class);
                startActivity(intent);
            }
        });

        Button uploadbutton = (Button) findViewById(R.id.UploadPhoto);
        /*uploadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Bitmap bitmap = ImageLoader.init().from(selectedPhoto).requestSize(1024,1024).getBitmap();
                    String encodeImage = ImageBase64.encode(bitmap);
                    Log.d(TAG.encodeImage);
                }catch (FileNotFoundException e){
                    Toast.makeText(getApplicationContext(), "FileNotFound Exception", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        if (!hasCamera()) {
            cambutton.setEnabled(false);}
    }

    private boolean hasCamera() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }



    public void launchCamera(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap) extras.get("data");
            cameraimageview.setImageBitmap(photo);
            Save savefile = new Save();
            savefile.saveImage(this, photo);
        }
    }
}
