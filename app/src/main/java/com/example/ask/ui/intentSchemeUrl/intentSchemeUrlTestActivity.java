package com.example.ask.ui.intentSchemeUrl;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.ask.R;
import com.example.ask.util.FileUtil;

public class intentSchemeUrlTestActivity extends Activity {
    TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intent_scheme_url_test);
        textView = findViewById(R.id.intent_scheme_url_test_text_view);
        textView.setText("本测试activity被成功调用\n利用成功!\n");
        FileUtil.saveResultToFile("本测试activity被成功调用", intentSchemeUrlTestActivity.this);
        FileUtil.saveResultToFile("利用成功", intentSchemeUrlTestActivity.this);


    }
}
