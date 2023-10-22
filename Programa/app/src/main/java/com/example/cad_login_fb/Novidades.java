package com.example.cad_login_fb;

public class Novidades {
    private String nome;
    private String info;
    private String imagemUrl;

    public Novidades(String nome, String info, String imagemUrl) {
        this.nome = nome;
        this.info = info;
        this.imagemUrl = imagemUrl;
    }

    public String getNome() {
        return nome;
    }

    public String getInfo() {
        return info;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }
}

