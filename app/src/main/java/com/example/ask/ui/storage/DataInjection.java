package com.example.ask.ui.storage;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.example.ask.MainActivity;
import com.example.ask.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataInjection extends Activity {

    private static String TAG = "Malicious";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storge_data_injection);

        int REQUEST_EXTERNAL_STORAGE = 1;
        String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        int permission = ActivityCompat.checkSelfPermission(DataInjection.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(DataInjection.this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
    }

    private void writeToFile() {
        TextView res = (TextView) findViewById(R.id.result);
        File file = new File(getExternalFilesDir(null), "bible.txt");
        String absPath = file.getAbsolutePath();
        String myPath = absPath.replace("com.example.ask", "com.example.externalstorage");
        Log.d("ExternalStorageBenign", "Benign = " + file.getAbsolutePath());
        Log.d("ExternalStorageBenign", "Mal = " + myPath);
        try (InputStream is = getResources().openRawResource(R.raw.bible);
             OutputStream os = new FileOutputStream(new File(myPath))) {
            byte[] data = new byte[is.available()];
            is.read(data);
            os.write(data);
            Log.d("ExternalStorageBenign", myPath + " written successfully");
        } catch (IOException e) {
            e.printStackTrace();
            res.setText("Unable to write into file");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (checkPermission("android.permission.WRITE_EXTERNAL_STORAGE",
                android.os.Process.myPid(), android.os.Process.myUid()) == PackageManager.PERMISSION_GRANTED) {
            Log.d("ExternalStorageBenign", "android.permission.WRITE_EXTERNAL_STORAGE already granted");
            writeToFile();
        } else {
            Log.d("ExternalStorageBenign", "need android.permission.WRITE_EXTERNAL_STORAGE permission");
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 100) {
            if (permissions[0].equalsIgnoreCase("android.permission.WRITE_EXTERNAL_STORAGE") &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                writeToFile();
            } else {
                throw new RuntimeException("Permission has not granted");
            }
        }
    }
}
