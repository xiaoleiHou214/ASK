package com.example.ask.ui.worldFile;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ask.R;
import com.example.ask.adapter.MsgAdapter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadDetail extends Activity {
    private TextView readText;
    private Button readBtn;
    private String filePath;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_detail);

        readText = findViewById(R.id.file_read);
        readBtn = findViewById(R.id.btn_read);
        filePath = getIntent().getStringExtra("path");

        readBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Read();
            }
        });
    }

    public void Read(){
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(filePath)));) {
            StringBuilder all = new StringBuilder();
            String line = null;
            while ((line=br.readLine())!=null){
                all.append(line);
                all.append("\n");
            }
            readText.setText(all.toString());

        } catch (IOException e) {
            Toast.makeText(this, "读取失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
