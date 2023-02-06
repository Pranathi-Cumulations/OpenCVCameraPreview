package com.example.opencvvedio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.nio.charset.CoderResult;

public class ImageActivity extends AppCompatActivity {

    ImageView imageView;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        imageView = findViewById(R.id.imageView);

        if(OpenCVLoader.initDebug()){
            Log.d("ImageActivity","loaded");
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("image", "In start");
        Mat tmp = MainActivity.mat;
        Core.rotate(tmp,tmp,Core.ROTATE_90_CLOCKWISE);
        bitmap = Bitmap.createBitmap(tmp.cols(), tmp.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(tmp,bitmap);
        imageView.setImageBitmap(bitmap);
    }
}