package com.example.ask.ui.worldFile;

import android.app.Activity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);

        listView = findViewById(R.id.list_item);
        path = "/data/data/"+ "com.example.xiao.fileread"+"/files";
        messageList = getFileList();
        msgAdapter = new MsgAdapter(this,messageList);
        listView.setAdapter(msgAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                String meg = messageList.get(position).getContent();
                switch (meg){
                    case "读取文件内容":
                        Intent intent = new Intent(FileRead.this,ReadDetail.class);
                        String path = "/data/data/com.example.xiao.fileread/files/FileRead.txt";
                        intent.putExtra("path",path);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    private List<Message> getFileList(){
        //List<File> list = FileUtil.listFileSortByModifyTime(path);
        List<Message> messages = new ArrayList<>();
        /*for (int i=0;i<list.size();i++){
            Message msg = new Message(list.get(i).getName());
            messages.add(msg);
        }*/
        Message msg = new Message("读取文件内容");
        messages.add(msg);
        return messages;
    }

    public void Read(){
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream("/data/data/com.example.xiao.fileread/files/FileRead.txt")));) {
            String line = br.readLine();
            Toast.makeText(this, line, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "读取失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
