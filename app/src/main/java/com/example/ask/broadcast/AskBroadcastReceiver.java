package com.example.ask.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.ask.MainActivity;
import com.example.ask.R;
import com.example.ask.ui.preference.PreferenceRead;
import com.example.ask.ui.preference.PreferenceWrite;
import com.example.ask.ui.worldFile.FileRead;
import com.example.ask.ui.worldFile.FileWrite;
import com.example.ask.ui.worldFile.ReadDetail;
import com.example.ask.util.FileUtil;

public class AskBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String goExploit = intent.getStringExtra("goExploit");
        switch (goExploit) {
            case "全局文件可读":
                String fileName = intent.getStringExtra("path");
                worldFileReadBroadcast(context, fileName);
                break;
            case "全局文件可写":
                String fileName1 = intent.getStringExtra("path");
                worldFileWriteBroadcast(context, fileName1);
                break;
            case "配置文件可读":
                String fileName2 = intent.getStringExtra("path");
                sharedPreferencesReadBroadcast(context, fileName2);
                break;
            case "配置文件可写":
                String fileName3 = intent.getStringExtra("path");
                sharedPreferencesWriteBroadcast(context, fileName3);
                break;
            case "SD卡非法访问":
                break;
            case "程序任意调试":
                break;
            case "数据任意备份":
                break;
            case "Activity组件暴漏":
                break;
            case "Service组件暴漏":
                break;
            case "ContentProvider组件暴漏":
                break;
            case "BroadcastReceiver组件暴漏":
                break;
            case "动态注册Broadcast":
                break;
            case "Fragment注入":
                break;
            case "IntentSchemeURLs漏洞":
                break;
            case "隐式Intent调用":
                break;
            case "WebView弱配置接口":
                break;
        }

    }

    //全局文件可读
    private void worldFileReadBroadcast(Context context, String fileName) {
        FileUtil.saveResultToFile("", context);
        Intent intent = new Intent(context, FileRead.class);
        String path = fileName;
        intent.putExtra("path", path);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    //全局文件可写
    private void worldFileWriteBroadcast(Context context, String fileName) {
        FileUtil.saveResultToFile("", context);
        Intent intent = new Intent(context, FileWrite.class);
        String path = fileName;
        intent.putExtra("path", path);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    //配置文件可读
    private void sharedPreferencesReadBroadcast(Context context, String fileName) {
        FileUtil.saveResultToFile("", context);
        Intent intent = new Intent(context, PreferenceRead.class);
        String path = fileName;
        intent.putExtra("path", path);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    //配置文件可写
    private void sharedPreferencesWriteBroadcast(Context context, String fileName) {
        FileUtil.saveResultToFile("", context);
        Intent intent = new Intent(context, PreferenceWrite.class);
        String path = fileName;
        intent.putExtra("path", path);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    //SD卡数据存储
    //数据注入
    //数据泄露
    //Fragment注入
    //动态广播注册
}
