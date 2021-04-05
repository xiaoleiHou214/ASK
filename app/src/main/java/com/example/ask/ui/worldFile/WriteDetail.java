package com.example.ask.ui.worldFile;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ask.R;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class WriteDetail extends Activity {

    private EditText writeText;
    private Button writeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_detail);

        writeText = findViewById(R.id.file_write);
        writeBtn = findViewById(R.id.btn_write);

        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = writeText.getText().toString();
                if(str!=null&&!"".equals(str)){
                    write();
                }else {
                    Toast.makeText(WriteDetail.this, "写入内容不可以为空！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void write(){
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("/data/data/com.example.xiao.fileread/files/FileRead.txt")));){
            bw.write(writeText.getText().toString());
            Toast.makeText(this, "写入成功！", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "操作失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
