package com.example.ask.ui.storage;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

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
    }

    private void writeToFile() {
        TextView res = (TextView) findViewById(R.id.result);
        File file = new File(getExternalFilesDir(null), "bible.txt");
        String absPath = file.getAbsolutePath();
        String myPath = absPath.replace("edu.ksu.cs.malicious", "edu.ksu.cs.benign");
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
