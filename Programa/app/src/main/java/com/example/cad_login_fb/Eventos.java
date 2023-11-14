package com.example.cad_login_fb;

public class Eventos {
    private String nome;
    private String horario;
    private String imagemUrl;
    private String duracao;
    private String dia;

    public Eventos(String nome, String horario, String imagemUrl, String duracao, String dia) {
        this.nome = nome;
        this.horario = horario;
        this.imagemUrl = imagemUrl;
        this.duracao = duracao;
        this.dia = dia;
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

    public String getDuracao() {
        return duracao;
    }

    public String getDia() {
        return dia;
    }
}
