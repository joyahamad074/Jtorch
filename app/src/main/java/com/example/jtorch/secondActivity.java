package com.example.jtorch;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

public class secondActivity extends AppCompatActivity {
    ImageButton btn_on, btn_off;
    private final int CAMERA_REQUEST_CODE = 2;
    boolean hasCameraFlash = false;
    private boolean isFlashOn = false;

    //@android.support.annotation.RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_second);
        btn_on = (ImageButton) findViewById(R.id.btn_on);
        btn_off =(ImageButton) findViewById(R.id.btn_off);
        hasCameraFlash = getPackageManager().
                hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        final MediaPlayer mp = MediaPlayer.create(this,R.raw.flash_sound);


        btn_on.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                askPermission(Manifest.permission.CAMERA, CAMERA_REQUEST_CODE);
                btn_on.setVisibility(View.GONE);
                mp.start();
                btn_off.setVisibility(View.VISIBLE);
                NotificationCompat.Builder builder = (NotificationCompat.Builder)
                        new NotificationCompat.Builder(getApplicationContext())
                        .setSmallIcon(R.drawable.ic_message)
                         .setContentTitle("Flash Light is On")
                        .setContentText("This is a Notification for Flashlight");
                NotificationManager notificationManager = (NotificationManager)
                        getSystemService(NOTIFICATION_SERVICE);
               notificationManager.notify(0,builder.build());

            }


        });

        btn_off.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                askPermission(Manifest.permission.CAMERA, CAMERA_REQUEST_CODE);
                btn_off.setVisibility(View.GONE);
                mp.start();
                btn_on.setVisibility(View.VISIBLE);
                NotificationCompat.Builder builder = (NotificationCompat.Builder)
                        new NotificationCompat.Builder(getApplicationContext())
                                .setSmallIcon(R.drawable.ic_message)
                                .setContentTitle("Flash Light is Off")
                                .setContentText("This is a Notification for Flashlight");
                NotificationManager notificationManager = (NotificationManager)
                        getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(0,builder.build());

            }


        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void flashLight() {
        if (hasCameraFlash) {
            if (isFlashOn) {
                flashLightOff();
                isFlashOn=false;
            } else {
                flashLightOn();
                isFlashOn=true;

            }
        } else {
            Toast.makeText(secondActivity.this, "No flash available on your device",
                    Toast.LENGTH_SHORT).show();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void flashLightOn() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, true);
            Toast.makeText(secondActivity.this, "Flash On",
                    Toast.LENGTH_SHORT).show();

        } catch (CameraAccessException e) {
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void flashLightOff() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, false);
            Toast.makeText(secondActivity.this, "Flash OFF",
                    Toast.LENGTH_SHORT).show();
        } catch (CameraAccessException e) {
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void askPermission(String permission, int requestCode) {
        // We already have permission do what you want
        if (ContextCompat.checkSelfPermission(this,permission)!= PackageManager.PERMISSION_GRANTED){
            // We Dont have permission
            ActivityCompat.requestPermissions(this,new String[]{permission},requestCode);

        }else flashLight();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    hasCameraFlash = getPackageManager().
                            hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
                    Toast.makeText(this, "Camera Permission Granted", Toast.LENGTH_LONG).show();
                    flashLight();

                } else {
                    Toast.makeText(this, "Camera Permission Denied", Toast.LENGTH_LONG).show();
                }
                break;
        }

    }






}
