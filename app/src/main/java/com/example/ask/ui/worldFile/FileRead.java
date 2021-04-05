package com.example.ask.ui.worldFile;

import android.app.Activity;
import android.content.Intent;
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

import java.io.File;
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
        Toast.makeText(this,getFilesDir().toString(),Toast.LENGTH_LONG).show();
        Log.i("FileRead_path:",getFilesDir().toString());
        path = "/data/user/0/"+ "com.example.xiao.fileread"+"/files";
        path = getFilesDir().toString();
        messageList = getFileList();
        msgAdapter = new MsgAdapter(this,messageList);
        listView.setAdapter(msgAdapter);
        Toast.makeText(this,getFilesDir().toString(),Toast.LENGTH_LONG).show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                String meg = messageList.get(position).getContent();
                switch (meg){
                    case "数据注入":
                        Intent intent = new Intent(FileRead.this, DataInjection.class);
                        startActivity(intent);
                        break;
                    case "信息泄露":
                        Intent intent1 = new Intent(FileRead.this, InformationLeak.class);
                        startActivity(intent1);
                        break;
                }
            }
        });
    }

    private List<Message> getFileList(){
        List<File> list = FileUtil.listFileSortByModifyTime(path);
        List<Message> messages = new ArrayList<>();
        for (int i=0;i<list.size();i++){
            Message msg = new Message(list.get(i).getName());
            messages.add(msg);
        }
        return messages;
    }
}
