package com.example.ask.ui.intentSchemeUrl;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.ask.R;
import com.example.ask.util.FileUtil;

public class intentSchemeUrlActivity extends Activity {
    Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intent_scheme_url);
        button = findViewById(R.id.exploit_intent_scheme_url);
        FileUtil.saveResultToFile("请点击\"发送INTENT SCHEME URL\"。", intentSchemeUrlActivity.this);
        FileUtil.saveResultToFile("请选择待测浏览器打开", intentSchemeUrlActivity.this);
        FileUtil.saveResultToFile("打开浏览器后，点击\"Click to open intent scheme url!", intentSchemeUrlActivity.this);
        FileUtil.saveResultToFile("若打开新的activity并显示利用成功，则利用成功，否则，利用失败", intentSchemeUrlActivity.this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri content_url = Uri.parse("http://10.0.2.2:8080/intentSchemeUrl.html");
                //Uri content_url = Uri.parse("\"intent:#Intent;action=com.example.addcontact;S.name=liuao;S.phoneNum=123222223;end\"");
                intent.setData(content_url);
                startActivity(intent);
            }
        });
    }

}
