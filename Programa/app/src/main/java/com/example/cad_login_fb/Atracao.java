package com.example.cad_login_fb;

public class Atracao {
    private String nome;
    private String horario;
    private String imagemUrl;

    public Atracao(String nome, String horario, String imagemUrl) {
        this.nome = nome;
        this.horario = horario;
        this.imagemUrl = imagemUrl;
    }

    public String getNome() {
        return nome;
    }

    public String getHorario() {
        return horario;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }
}
