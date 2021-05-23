package com.example.ask.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.ask.MainActivity;
import com.example.ask.R;
import com.example.ask.ui.exposedComponents.ExportedComponent;
import com.example.ask.ui.exposedComponents.ExportedProviderActivity;
import com.example.ask.ui.intentSchemeUrl.intentSchemeUrl;
import com.example.ask.ui.preference.PreferenceRead;
import com.example.ask.ui.preference.PreferenceWrite;
import com.example.ask.ui.storage.DataInjection;
import com.example.ask.ui.worldFile.FileRead;
import com.example.ask.ui.worldFile.FileWrite;
import com.example.ask.ui.worldFile.ReadDetail;
import com.example.ask.util.FileUtil;

public class AskBroadcastReceiver extends BroadcastReceiver {
    public static String category_id = "";

    @Override
    public void onReceive(Context context, Intent intent) {
        String goExploit = intent.getStringExtra("goExploit");
        String id = intent.getStringExtra("apkVulnerNum");
        switch (goExploit) {
            case "全局文件可读":
                category_id = "file_world_readable" + "_" + id;
                String fileName = intent.getStringExtra("path");
                String packageName = intent.getStringExtra("packageName");
                worldFileReadBroadcast(context, fileName, packageName);
                break;
            case "全局文件可写":
                category_id = "file_world_writable" + "_" + id;
                String fileName1 = intent.getStringExtra("path");
                String packageName2 = intent.getStringExtra("packageName");
                worldFileWriteBroadcast(context, fileName1, packageName2);
                break;
            case "配置文件可读":
                category_id = "preference_world_readable" + "_" + id;
                String fileName2 = intent.getStringExtra("path");
                String packageName3 = intent.getStringExtra("packageName");
                sharedPreferencesReadBroadcast(context, fileName2, packageName3);
                break;
            case "配置文件可写":
                category_id = "preference_world_writable" + "_" + id;
                String fileName3 = intent.getStringExtra("path");
                String packageName4 = intent.getStringExtra("packageName");
                sharedPreferencesWriteBroadcast(context, fileName3, packageName4);
                break;
            case "SD卡非法访问":
                category_id = "external_storage" + "_" + id;
                String appFilePath = intent.getStringExtra("appFilePath");
                String packageName1 = intent.getStringExtra("packageName");
                sdIllegalAccess(context, appFilePath, packageName1, category_id);
                break;
            case "程序任意调试":
                category_id = "debuggable" + "_" + id;
                break;
            case "数据任意备份":
                category_id = "allow_backup" + "_" + id;
                break;
            case "Activity组件暴漏":
                category_id = "exported_activity" + "_" + id;
                FileUtil.saveResultToFile("", context);
                FileUtil.saveResultToFile("请在手机界面配置相应参数。", context);
                Intent intent2 = new Intent();
                intent2.setClass(context, ExportedComponent.class);
                context.startActivity(intent2);
                break;
            case "Service组件暴漏":
                category_id = "exported_service" + "_" + id;
                FileUtil.saveResultToFile("", context);
                FileUtil.saveResultToFile("请在手机界面配置相应参数。", context);
                Intent intent3 = new Intent();
                intent3.setClass(context, ExportedComponent.class);
                context.startActivity(intent3);
                break;
            case "ContentProvider组件暴漏":
                category_id = "exported_provide" + "_" + id;
                FileUtil.saveResultToFile("", context);
                FileUtil.saveResultToFile("请在手机界面配置相应参数。", context);
                Intent intent6 = new Intent();
                intent6.setClass(context, ExportedProviderActivity.class);
                context.startActivity(intent6);
                break;
            case "BroadcastReceiver组件暴漏":
                category_id = "exported_receiver" + "_" + id;
                FileUtil.saveResultToFile("", context);
                FileUtil.saveResultToFile("请在手机界面配置相应参数。", context);
                Intent intent4 = new Intent();
                intent4.setClass(context, ExportedComponent.class);
                context.startActivity(intent4);
                break;
            case "动态注册Broadcast":
                category_id = "dynamic_register_receiver" + "_" + id;
                FileUtil.saveResultToFile("", context);
                FileUtil.saveResultToFile("请在手机界面配置相应参数。", context);
                Intent intent5 = new Intent();
                intent5.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent5.setClass(context, ExportedComponent.class);
                context.startActivity(intent5);
                break;
            case "Fragment注入":
                category_id = "fragment_injection" + "_" + id;
                FileUtil.saveResultToFile("", context);
                FileUtil.saveResultToFile("请在手机界面配置相应参数。", context);
                Intent intent7 = new Intent();
                intent7.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent7.setClass(context, ExportedComponent.class);
                context.startActivity(intent7);
                break;
            case "IntentSchemeURLs漏洞":
                category_id = "intent_scheme_url" + "_" + id;
                FileUtil.saveResultToFile("", context);
                Intent intent1 = new Intent();
                intent1.setClass(context, intentSchemeUrl.class);
                context.startActivity(intent1);
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
    private void worldFileReadBroadcast(Context context, String fileName, String packageName) {
        FileUtil.saveResultToFile("", context);
        Intent intent = new Intent(context, FileRead.class);
        String path = fileName;
        intent.putExtra("path", path);
        intent.putExtra("packageName", packageName);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    //全局文件可写
    private void worldFileWriteBroadcast(Context context, String fileName, String packageName) {
        FileUtil.saveResultToFile("", context);
        Intent intent = new Intent(context, FileWrite.class);
        String path = fileName;
        intent.putExtra("path", path);
        intent.putExtra("packageName", packageName);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    //配置文件可读
    private void sharedPreferencesReadBroadcast(Context context, String fileName, String packageName) {
        FileUtil.saveResultToFile("", context);
        Intent intent = new Intent(context, PreferenceRead.class);
        String path = fileName;
        intent.putExtra("path", path);
        intent.putExtra("packageName", packageName);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    //配置文件可写
    private void sharedPreferencesWriteBroadcast(Context context, String fileName, String packageName) {
        FileUtil.saveResultToFile("", context);
        Intent intent = new Intent(context, PreferenceWrite.class);
        String path = fileName;
        intent.putExtra("path", path);
        intent.putExtra("packageName", packageName);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    //SD卡数据存储
    private void sdIllegalAccess(Context context, String appFilePath, String packageName, String categoryId) {
        FileUtil.saveResultToFile("", context);
        Intent intent = new Intent(context, DataInjection.class);
        intent.putExtra("appFilePath", appFilePath);
        intent.putExtra("packageName", packageName);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
