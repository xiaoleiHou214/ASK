package com.example.ask.entity;

public class Message {
    private String content; // 内容

    //无参构造函数
    public Message() {

    }

    //有参构造函数
    public Message(String content) {
        this.content = content;
    }

    //getter and setter


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
