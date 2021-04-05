package com.example.ask.ui.storage;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.ask.R;

import java.io.File;

public class InformationLeak extends Activity {

    public static int REQ_CODE = 101;
    private static final String TAG = "ExternalStorageMal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storage_information_leak);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkPermission("android.permission.READ_EXTERNAL_STORAGE", android.os.Process.myPid(), android.os.Process.myUid()) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "android.permission.READ_EXTERNAL_STORAGE already granted");
            performAction();
        } else {
            Log.d(TAG, "need android.permission.READ_EXTERNAL_STORAGE permission");
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, REQ_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int reqCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        if(reqCode == REQ_CODE && permissions[0].equals("android.permission.READ_EXTERNAL_STORAGE")
                && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            performAction();
        }
        else{
            throw new RuntimeException(new SecurityException());
        }
    }

    private void performAction(){
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        TextView result = (TextView) findViewById(R.id.result);
        File file = new File(getExternalFilesDir(null), "ssn_bkup.jpg");
        String absPath = file.getAbsolutePath();
        String path = absPath.replace("com.example.ask", "com.example.externalstorage");
        Log.d(TAG, path);
        Bitmap image = BitmapFactory.decodeFile(path);
        if (image != null) {
            result.setText("ImageView loaded with SSN");
        } else {
            result.setText("Unable to load image");
        }
        imageView.setImageBitmap(image);
    }
}
