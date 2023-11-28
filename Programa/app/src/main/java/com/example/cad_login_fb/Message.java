package com.example.cad_login_fb;
// src/main/java/seu/pacote/Message.java
public class Message {
    private String name;
    private String text;

    // construtor vazio necessário para trabalhar com Firestore
    public Message() {}

    public Message(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }
}
