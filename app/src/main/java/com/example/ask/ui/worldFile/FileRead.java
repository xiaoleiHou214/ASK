package com.example.ask.ui.worldFile;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ask.R;
import com.example.ask.adapter.MsgAdapter;
import com.example.ask.entity.Message;
import com.example.ask.ui.storage.DataInjection;
import com.example.ask.ui.storage.InformationLeak;
import com.example.ask.util.FileUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileRead extends Activity {
    private ListView listView;
    private List<Message> messageList;
    private MsgAdapter msgAdapter;
    private String path;
    private String packageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);

        listView = findViewById(R.id.list_item);
        Intent receiverIntent = getIntent();
        packageName = receiverIntent.getStringExtra("packageName");
        path = "/data/data/"+packageName+"/files/" + receiverIntent.getStringExtra("path");
        messageList = getFileList();
        msgAdapter = new MsgAdapter(this, messageList);
        listView.setAdapter(msgAdapter);
        FileUtil.saveResultToFile("进入可读文件列表页面。", this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*String meg = messageList.get(position).getContent();
                switch (meg) {
                    case "读取文件内容":
                        Intent intent = new Intent(FileRead.this,ReadDetail.class);
                        String path = "/data/data/com.avjindersinghsekhon.minimaltodo/files/todoitems.json";
                        intent.putExtra("path",path);
                        startActivity(intent);
                        break;
                }*/
                FileUtil.saveResultToFile("点击进入path路径：" + path, FileRead.this);
                Intent intent = new Intent(FileRead.this, ReadDetail.class);
                intent.putExtra("path", path);
                startActivity(intent);
            }
        });
    }

    private List<Message> getFileList() {
        List<Message> messages = new ArrayList<>();
        Message message = new Message(path);
        messages.add(message);
        return messages;
    }
}
