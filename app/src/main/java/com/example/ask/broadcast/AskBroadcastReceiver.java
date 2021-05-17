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
    public static String category_id = "";

    @Override
    public void onReceive(Context context, Intent intent) {
        String goExploit = intent.getStringExtra("goExploit");
        String id = intent.getStringExtra("issueID");
        switch (goExploit) {
            case "全局文件可读":
                category_id = "file_world_readable" + "_" + id;
                String fileName = intent.getStringExtra("path");
                worldFileReadBroadcast(context, fileName);
                break;
            case "全局文件可写":
                category_id = "file_world_writable" + "_" + id;
                String fileName1 = intent.getStringExtra("path");
                worldFileWriteBroadcast(context, fileName1);
                break;
            case "配置文件可读":
                category_id = "preference_world_readable" + "_" + id;
                String fileName2 = intent.getStringExtra("path");
                sharedPreferencesReadBroadcast(context, fileName2);
                break;
            case "配置文件可写":
                category_id = "preference_world_writable" + "_" + id;
                String fileName3 = intent.getStringExtra("path");
                sharedPreferencesWriteBroadcast(context, fileName3);
                break;
            case "SD卡非法访问":
                category_id = "external_storage" + "_" + id;
                break;
            case "程序任意调试":
                category_id = "debuggable" + "_" + id;
                break;
            case "数据任意备份":
                category_id = "allow_backup" + "_" + id;
                break;
            case "Activity组件暴漏":
                category_id = "exported_activity" + "_" + id;
                break;
            case "Service组件暴漏":
                category_id = "exported_service" + "_" + id;
                break;
            case "ContentProvider组件暴漏":
                category_id = "exported_receiver" + "_" + id;
                break;
            case "BroadcastReceiver组件暴漏":
                category_id = "exported_provider" + "_" + id;
                break;
            case "动态注册Broadcast":
                category_id = "file_world_readable" + "_" + id;
                break;
            case "Fragment注入":
                category_id = "dynamic_register_receiver" + "_" + id;
                break;
            case "IntentSchemeURLs漏洞":
                category_id = "intent_scheme_url" + "_" + id;
                break;
            case "隐式Intent调用":
                category_id = "implicit_intent" + "_" + id;
                FileUtil.saveResultToFile("", context);
                FileUtil.saveResultToFile("请打开待测应用，并操作使其发送隐式Intent。", context);
                break;
            case "WebView弱配置接口":
                category_id = "weak_configured_webview" + "_" + id;
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
