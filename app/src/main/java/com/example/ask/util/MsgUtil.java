package com.example.ask.util;

import android.content.Context;

import com.example.ask.R;
import com.example.ask.entity.Message;

import java.util.ArrayList;
import java.util.List;

public class MsgUtil {
    private List<Message> messageList;
    public static String worldFile = "WorldFile";

    public static List<Message> getList(String fragmentName, Context context) {
        switch (fragmentName) {
            case "WorldFile":
                return returnWorldFile(context);
            case "Storage":
                return returnStorage(context);
            case "ExposedComponents":
                return returnExposedComponents(context);
            case "SharedPreference":
                return returnSP(context);
            case "DynamicRegBroadcast":
                return returnDynamicBroadcast();
            case "FragmentInjection":
                return returnFragmentInjection();
        }
        return null;
    }

    private static List<Message> returnWorldFile(Context context) {
        List<Message> messages = new ArrayList<>();
        if (context.getResources().getString(R.string.file_world_readable).equals("1")) {
            Message msg1 = new Message("全局文件可读");
            messages.add(msg1);
        }
        if (context.getResources().getString(R.string.file_world_writable).equals("1")) {
            Message msg2 = new Message("全局文件可写");
            messages.add(msg2);
        }
        return messages;
    }

    private static List<Message> returnExposedComponents(Context context) {
        List<Message> messages = new ArrayList<>();
        if (context.getResources().getString(R.string.exported_activity).equals("1")){
            Message msg1 = new Message("Activity暴露");
            messages.add(msg1);
        }
        if (context.getResources().getString(R.string.exported_service).equals("1")){
            Message msg2 = new Message("Service暴露");
            messages.add(msg2);
        }
        if (context.getResources().getString(R.string.exported_provider).equals("1")){
            Message msg4 = new Message("ContentProvider暴露");
            messages.add(msg4);
        }
        if (context.getResources().getString(R.string.exported_receiver).equals("1")){
            Message msg3 = new Message("BroadcastReceiver暴露");
            messages.add(msg3);
        }
        return messages;
    }

    private static List<Message> returnStorage(Context context) {
        List<Message> messages = new ArrayList<>();
        Message msg1 = new Message("数据注入");
        Message msg2 = new Message("信息泄露");
        messages.add(msg1);
        messages.add(msg2);
        return messages;
    }

    private static List<Message> returnSP(Context context) {
        List<Message> messages = new ArrayList<>();
        if (context.getResources().getString(R.string.preference_world_readable).equals("1")){
            Message msg1 = new Message("配置文件可读");
            messages.add(msg1);
        }
        if (context.getResources().getString(R.string.preference_world_writable).equals("1")){
            Message msg2 = new Message("配置文件可写");
            messages.add(msg2);
        }
        return messages;
    }

    private static List<Message> returnDynamicBroadcast() {
        List<Message> messages = new ArrayList<>();
        Message msg1 = new Message("发送广播");
        messages.add(msg1);
        return messages;
    }

    private static List<Message> returnFragmentInjection() {
        List<Message> messages = new ArrayList<>();
        Message msg1 = new Message("Fragment注入");
        messages.add(msg1);
        return messages;
    }


}
