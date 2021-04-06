package com.example.ask.ui.dynamicBroadCast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.example.ask.R;
import com.example.ask.adapter.MsgAdapter;
import com.example.ask.ui.preference.PreferenceRead;
import com.example.ask.ui.worldFile.ReadDetail;

public class DynamicRegBroadcastActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_reg_broadcast);

        Intent intent = new Intent("com.example.action.EMAIL");
        intent.putExtra("email", "rookie@test.com");
        intent.putExtra("text", "I can send email without any permissions");
        sendBroadcast(intent);
    }
}
