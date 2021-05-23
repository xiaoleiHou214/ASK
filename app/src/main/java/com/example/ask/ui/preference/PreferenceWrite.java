package com.example.ask.ui.preference;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ask.R;
import com.example.ask.adapter.MsgAdapter;
import com.example.ask.entity.Message;
import com.example.ask.ui.worldFile.FileWrite;
import com.example.ask.ui.worldFile.WriteDetail;
import com.example.ask.util.FileUtil;

import java.util.ArrayList;
import java.util.List;

public class PreferenceWrite extends Activity {

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
        path = "/data/data/" + packageName + "/files/" + receiverIntent.getStringExtra("path");
        messageList = getFileList();
        msgAdapter = new MsgAdapter(this, messageList);
        listView.setAdapter(msgAdapter);
        FileUtil.saveResultToFile("进入可写配置文件列表页面。", this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String meg = messageList.get(position).getContent();
                /*switch (meg){
                    case "写入配置文件内容":
                        Intent intent = new Intent(PreferenceWrite.this, WriteDetail.class);
                        String path = "/data/data/com.javiersantos.mlmanager/shared_prefs/com.javiersantos.mlmanager_preferences.xml";
                        intent.putExtra("path",path);
                        startActivity(intent);
                        break;
                }*/
                FileUtil.saveResultToFile("点击进入path路径：" + path, PreferenceWrite.this);
                Intent intent = new Intent(PreferenceWrite.this, WriteDetail.class);
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
