package com.example.opencvvedio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.opencv.android.CameraActivity;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.Collections;
import java.util.List;

import kotlin.collections.UCollectionsKt;

public class MainActivity extends CameraActivity {

    CameraBridgeViewBase cameraBridgeViewBase;
    boolean CaptureClick = false;
    Button btnCapture;
    static Mat mat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getPermission();

        cameraBridgeViewBase = findViewById(R.id.cameraView);
        btnCapture = findViewById(R.id.btnCapture);
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CaptureClick = true;

            }
        });
        cameraBridgeViewBase.setCvCameraViewListener(new CameraBridgeViewBase.CvCameraViewListener2() {
            @Override
            public void onCameraViewStarted(int width, int height) {

            }

            @Override
            public void onCameraViewStopped() {

            }

            @Override
            public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
                Mat img = inputFrame.rgba();
                Imgproc.rectangle(img, new Point(50, 50), new Point(700, 600), new Scalar(0, 0, 255), 4);
                if (CaptureClick) {
                    Mat x = img.clone();
                    Rect roi = new Rect(50 ,50,650,550);
                    mat = new Mat(x,roi);

                    sendImageTonextActivity();
                }
                return img;
            }
        });

        if (OpenCVLoader.initDebug()) {
            cameraBridgeViewBase.enableView();
        }

    }

    private void sendImageTonextActivity() {
        Log.d("in send", "yes");
        Intent intent = new Intent(this, ImageActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        CaptureClick = false;
        cameraBridgeViewBase.enableView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraBridgeViewBase.disableView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraBridgeViewBase.disableView();
    }

    @Override
    protected List<? extends CameraBridgeViewBase> getCameraViewList() {
        return Collections.singletonList(cameraBridgeViewBase);
    }


    private void getPermission() {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 101);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            getPermission();
        }
    }
}