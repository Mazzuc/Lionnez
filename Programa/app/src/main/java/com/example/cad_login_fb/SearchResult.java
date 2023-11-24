package com.example.cad_login_fb;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.SerializedName;

public class SearchResult {

    @SerializedName("nome")
    private String nome;


    @SerializedName("descricao")
    private String descricao;

    @SerializedName("habitat")
    private String habitat;

    @SerializedName("pais")
    private String pais;

    @SerializedName("alimentacao")
    private String alimentacao;

    @SerializedName("peso")
    private String peso;

    @SerializedName("altura")
    private String altura;

    @SerializedName("curiosidades")
    private String curiosidades;

    @SerializedName("imagem")
    private String imagem;

    // Adicione getters e setters conforme necessário

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getHabitat() {
        return habitat;
    }

    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getAlimentacao() {
        return alimentacao;
    }

    public void setAlimentacao(String alimentacao) {
        this.alimentacao = alimentacao;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getCuriosidades() {
        return curiosidades;
    }

    public void setCuriosidades(String curiosidades) {
        this.curiosidades = curiosidades;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}
