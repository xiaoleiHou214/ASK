package com.example.ask.util;

import com.example.ask.entity.Message;

import java.util.ArrayList;
import java.util.List;

public class MsgUtil {
    private List<Message> messageList;
    public static String worldFile = "WorldFile";

    public static List<Message> getList(String fragmentName){
        switch (fragmentName){
            case "WorldFile":
                return returnWorldFile();
            case "Storage":
                return returnStorage();
            case "ExposedComponents":
                return returnExposedComponents();
        }
        return null;
    }

    private static List<Message> returnWorldFile(){
        List<Message> messages = new ArrayList<>();
        Message msg1 = new Message("全局文件可读");
        Message msg2 = new Message("全局文件可写");
        messages.add(msg1);
        messages.add(msg2);
        return messages;
    }

    private static List<Message> returnExposedComponents(){
        List<Message> messages = new ArrayList<>();
        Message msg1 = new Message("Activity暴露");
        Message msg2 = new Message("Service暴露");
        Message msg3 = new Message("BroadcastReceiver暴露");
        Message msg4 = new Message("ContentProvider暴露");
        messages.add(msg1);
        messages.add(msg2);
        messages.add(msg3);
        messages.add(msg4);
        return messages;
    }

    private static List<Message> returnStorage(){
        List<Message> messages = new ArrayList<>();
        Message msg1 = new Message("数据注入");
        Message msg2 = new Message("信息泄露");
        messages.add(msg1);
        messages.add(msg2);
        return messages;
    }

}
