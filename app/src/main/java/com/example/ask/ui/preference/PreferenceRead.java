package com.example.ask.ui.preference;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ask.R;
import com.example.ask.adapter.MsgAdapter;
import com.example.ask.entity.Message;
import com.example.ask.ui.worldFile.FileRead;
import com.example.ask.ui.worldFile.ReadDetail;
import com.example.ask.util.FileUtil;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PreferenceRead extends Activity {
    private ListView listView;
    private List<Message> messageList;
    private MsgAdapter msgAdapter;
    private String path;
    private String packageNmae;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);

        listView = findViewById(R.id.list_item);
        Intent receiverIntent = getIntent();
        packageNmae = receiverIntent.getStringExtra("packageName");
        path = "/data/data/"+packageNmae+"/shared_prefs/" + receiverIntent.getStringExtra("path");
        messageList = getFileList();
        msgAdapter = new MsgAdapter(this,messageList);
        listView.setAdapter(msgAdapter);
        FileUtil.saveResultToFile("进入可读配置文件列表页面。", this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                String meg = messageList.get(position).getContent();
                /*switch (meg){
                    case "读取配置文件内容":
                        Intent intent = new Intent(PreferenceRead.this, ReadDetail.class);
                        String path = "/data/data/org.horaapps.leafpic.debug/shared_prefs/org.horaapps.leafpic.debug_preferences.xml";
                        intent.putExtra("path",path);
                        startActivity(intent);
                        break;
                }*/
                FileUtil.saveResultToFile("点击进入path路径：" + path, PreferenceRead.this);
                Intent intent = new Intent(PreferenceRead.this, ReadDetail.class);
                intent.putExtra("path", path);
                startActivity(intent);
            }
        });
    }

    private List<Message> getFileList(){
        List<Message> messages = new ArrayList<>();
        Message message = new Message(path);
        messages.add(message);
        return messages;
    }
}
