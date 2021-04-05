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

import java.util.ArrayList;
import java.util.List;

public class PreferenceWrite extends Activity {

    private ListView listView;
    private List<Message> messageList;
    private MsgAdapter msgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);

        listView = findViewById(R.id.list_item);
        messageList = getFileList();
        msgAdapter = new MsgAdapter(this,messageList);
        listView.setAdapter(msgAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                String meg = messageList.get(position).getContent();
                switch (meg){
                    case "写入配置文件内容":
                        Intent intent = new Intent(PreferenceWrite.this, WriteDetail.class);
                        String path = "/data/data/com.example.xiao.preferenceswrite/shared_prefs/SharedPreferencesRead.xml";
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
        Message msg = new Message("写入配置文件内容");
        messages.add(msg);
        return messages;
    }
}