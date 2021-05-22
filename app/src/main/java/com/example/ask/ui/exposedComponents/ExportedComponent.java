package com.example.ask.ui.exposedComponents;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.ask.R;
import com.example.ask.util.FileUtil;
import com.example.ask.util.ResourceUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.ask.broadcast.AskBroadcastReceiver.category_id;

public class ExportedComponent extends Activity {
    String[] actions;
    ListView extraList;
    LinearLayout target_component_info;
    TextView target_component_name;
    LinearLayout action_info;
    TextView please_add_extra;
    Spinner action_name;
    LinearLayout target_package_info;
    TextView target_package_name;
    Button send_command;

    String action;
    String target_package;
    String target_component;
    String class_path;
    Map<String, String> final_extras = new HashMap<>();
    ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exported_component);
        target_component_info = (LinearLayout) findViewById(R.id.target_component_info);
        target_package_info = (LinearLayout) findViewById(R.id.target_package_info);
        target_component_name = (TextView) findViewById(R.id.target_component_name);
        action_info = (LinearLayout) findViewById(R.id.action_info);
        please_add_extra = (TextView) findViewById(R.id.please_add_extra);
        action_name = (Spinner) findViewById(R.id.action_name);
        extraList = (ListView) findViewById(R.id.extras);
        target_package_name = (TextView) findViewById(R.id.target_package_name);
        send_command = (Button) findViewById(R.id.send_command);

        String extras_label = category_id + "_extras";
        String action_label = category_id + "_actions";
        String component_name_label = category_id + "_tag_name";
        String package_name_label = category_id + "_package_name";
        String class_path_label = category_id + "_class_path";
        FileUtil.saveResultToFile("请根据界面提示在相应位置选择或填写参数", ExportedComponent.this);

        if (category_id.contains("dynamic_register_receiver")) {
            target_component_info.setVisibility(View.INVISIBLE);
            target_package_info.setVisibility(View.INVISIBLE);
        }

        String[] extras = ResourceUtil.getStringArrayByName(ExportedComponent.this, extras_label);
        actions = ResourceUtil.getStringArrayByName(ExportedComponent.this, action_label);
        String component_name = ResourceUtil.getStringByName(ExportedComponent.this, component_name_label);
        String package_name = ResourceUtil.getStringByName(ExportedComponent.this, package_name_label);
        String class_name = ResourceUtil.getStringByName(ExportedComponent.this, class_path_label);

        if (component_name != null && package_name != null) {
            target_component_name.setText(component_name);
            target_package_name.setText(package_name);
            target_package = package_name;
            target_component = component_name;
        }

        if (category_id.contains("fragment_injection")) {
            target_component_info.setVisibility(View.INVISIBLE);
            if (package_name != null && !"".equals(package_name) && class_name != null) {
                target_package_name.setText(package_name);
                target_package = package_name;
                class_path = class_name.replace("/",".");
            }
        }


        if (actions == null) {
            action_info.setVisibility(View.INVISIBLE);
        } else {
            ArrayAdapter<String> actionAdapter = new ArrayAdapter<String>(this, R.layout.item_select, actions);
            //设置下拉框的标题，不设置就没有难看的标题了
            action_name.setPrompt("请选择action");
            //设置下拉框的数组适配器
            action_name.setAdapter(actionAdapter);
            //设置下拉框默认的显示第一项
            action_name.setSelection(0);
            //给下拉框设置选择监听器，一旦用户选中某一项，就触发监听器的onItemSelected方法
            action_name.setOnItemSelectedListener(new MySelectedListener());
        }

        if (extras == null) {
            please_add_extra.setVisibility(View.INVISIBLE);
            extraList.setVisibility(View.INVISIBLE);
        } else {
            extraList.setAdapter(new ExtraAdapter(ExportedComponent.this, extras));
        }

        send_command.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if (category_id.contains("dynamic_register_receiver")) {
                    intent.setAction(action);
                    for (String extra_key : final_extras.keySet()) {
                        intent.putExtra(extra_key, final_extras.get(extra_key));
                    }
                    sendBroadcast(intent);
                    FileUtil.saveResultToFile("发送广播", ExportedComponent.this);
                    FileUtil.saveResultToFile("请根据目标应用的特定行为来自行判定目标广播接收器是否成功被唤醒，有无危险行为，以确定漏洞利用成功与否", ExportedComponent.this);
                } else if (category_id.contains("fragment_injection")) {
                    for (String extra_key : final_extras.keySet()) {
                        intent.putExtra(extra_key, final_extras.get(extra_key));
                    }
                    intent.setPackage(target_package);
                    intent.setClassName(target_package, class_path);
                    startActivity(intent);
                    FileUtil.saveResultToFile("发送广播", ExportedComponent.this);
                    FileUtil.saveResultToFile("请根据目标应用的特定行为来自行判定目标广播接收器是否成功被唤醒，有无危险行为，以确定漏洞利用成功与否", ExportedComponent.this);
                } else if (category_id.contains("exported_activity")) {
                    if (target_package != null && target_component != null) {
                        intent.setComponent(new ComponentName(target_package, target_component));
                    }
                    intent.setAction(action);
                    for (String extra_key : final_extras.keySet()) {
                        intent.putExtra(extra_key, final_extras.get(extra_key));
                    }
                    startActivity(intent);
                    FileUtil.saveResultToFile("发送intent至目标activity", ExportedComponent.this);
                    FileUtil.saveResultToFile("请根据目标应用的特定行为来自行判定目标activity是否启动成功，有无危险行为，以确定漏洞利用成功与否", ExportedComponent.this);


                } else if (category_id.contains("exported_service")) {
                    if (target_package != null && target_component != null) {
                        intent.setComponent(new ComponentName(target_package, target_component));
                    }
                    intent.setAction(action);
                    for (String extra_key : final_extras.keySet()) {
                        intent.putExtra(extra_key, final_extras.get(extra_key));
                    }
                    startService(intent);
                    FileUtil.saveResultToFile("发送intent至目标服务", ExportedComponent.this);
                    FileUtil.saveResultToFile("请根据目标应用的特定行为来自行判定目标服务是否启动成功，有无危险行为，以确定漏洞利用成功与否", ExportedComponent.this);

                } else if (category_id.contains("exported_receiver")) {
                    if (target_package != null && target_component != null) {
                        intent.setComponent(new ComponentName(target_package, target_component));
                    }
                    intent.setAction(action);
                    for (String extra_key : final_extras.keySet()) {
                        intent.putExtra(extra_key, final_extras.get(extra_key));
                    }
                    sendBroadcast(intent);
                    FileUtil.saveResultToFile("发送广播", ExportedComponent.this);
                    FileUtil.saveResultToFile("请根据目标应用的特定行为来自行判定目标广播接收器是否成功被唤醒，有无危险行为，以确定漏洞利用成功与否", ExportedComponent.this);
                }
            }
        });


    }

    public void saveExtraData(String extra_key, String extra_value) {
        final_extras.put(extra_key, extra_value);
    }

    private class MySelectedListener implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            action = actions[position];
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

}
