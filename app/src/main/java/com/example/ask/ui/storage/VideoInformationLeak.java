package com.example.ask.ui.storage;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.ask.R;
import com.example.ask.adapter.MsgAdapter;
import com.example.ask.entity.Message;
import com.example.ask.util.MsgUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VideoInformationLeak extends Activity {

    private ListView listView;
    private List<Message> msgList;
    private MsgAdapter adapter;
    private VideoView videoView;
    private MediaPlayer mMediaPlayer = null;

    public static int REQ_CODE = 101;
    private static final String TAG = "ExternalStorageMal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkPermission("android.permission.READ_EXTERNAL_STORAGE", android.os.Process.myPid(), android.os.Process.myUid()) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "android.permission.READ_EXTERNAL_STORAGE already granted");
            String oldPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SoundRecorder/";
            File file = new File(getExternalFilesDir(null), "SoundRecorder");
            String absPath = file.getAbsolutePath();
            //readUDiskDevsList(oldPath, absPath);
            initVideo(oldPath);

            listView = findViewById(R.id.list_item);
            videoView = findViewById(R.id.video_view);
            adapter = new MsgAdapter(this, msgList);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String meg = msgList.get(position).getContent();
                    startVideo(meg);
                }
            });
        } else {
            Log.d(TAG, "need android.permission.READ_EXTERNAL_STORAGE permission");
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, REQ_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int reqCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (reqCode == REQ_CODE && permissions[0].equals("android.permission.READ_EXTERNAL_STORAGE")
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        } else {
            throw new RuntimeException(new SecurityException());
        }
    }

    public void readUDiskDevsList(String oldPath, String newPath) {
        try {
            msgList = new ArrayList<Message>();
            (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            //读取文件夹中的文件
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }

                if (temp.isFile() && temp.getName().endsWith(".mp4")) {   //只复制MP4文件
                    //建立通道对象
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" +
                            (temp.getName()).toString());
                    Log.d(TAG, "readUDiskDevsList: " + temp.getName() + "复制成功");
                    //定义存储空间
                    byte[] b = new byte[1024 * 5];
                    //开始读文件
                    int len;
                    while ((len = input.read(b)) != -1) {
                        //将b中的数据写入到FileOutputStream对象中
                        output.write(b, 0, len);
                    }
                    //关闭流
                    output.flush();
                    output.close();
                    input.close();

                    if (temp.isDirectory()) {//如果是子文件夹
                        readUDiskDevsList(oldPath + "/" + file[i], newPath + "/" + file[i]);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("复制整个文件夹内容操作出错");
            e.printStackTrace();
        }
    }

    private void initVideo(String path) {
        Log.d(TAG, "initView: path============================" + path);
        File a = new File(path);
        final String[] file = a.list();
        final ArrayList<String> listPath = new ArrayList<String>();
        File temp = null;
        int t = 0;
        //读取文件夹中的文件
        for (int i = 0; i < file.length; i++) {           //读取path中的MP4文件
            if (path.endsWith(File.separator)) {
                temp = new File(path + file[i]);
                listPath.add(String.valueOf(temp));
                t++;
            } else {
                temp = new File(path + File.separator + file[i]);
                listPath.add(String.valueOf(temp));
                t++;
            }
        }
        msgList = new ArrayList<Message>();
        for (String filePath:listPath){
            Message message = new Message(filePath);
            msgList.add(message);
        }
    }

    private void startVideo(String path){
        String videoUrl1 = String.valueOf(path);
        Uri uri = Uri.parse(videoUrl1);

        MediaController mediaController = new MediaController(this);
        mediaController.setVisibility(View.GONE);           //隐藏进度条
        videoView.setMediaController(mediaController);

        videoView.setVideoURI(uri);
        videoView.start();

//        mMediaPlayer = new MediaPlayer();
//
//        try {
//            mMediaPlayer.setDataSource(this,uri);
//            mMediaPlayer.prepare();
//
//            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mp) {
//                    mMediaPlayer.start();
//                }
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
