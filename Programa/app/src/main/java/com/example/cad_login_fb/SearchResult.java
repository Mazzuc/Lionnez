package com.example.cad_login_fb;

import com.google.gson.annotations.SerializedName;

public class SearchResult {

    @SerializedName("nome")
    private String nome;

    @SerializedName("descricao")
    private String descricao;

    @SerializedName("tipoAnim")
    private String tipoAnim;

    @SerializedName("habitat")
    private String habitat;

    @SerializedName("pais")
    private String pais;

    @SerializedName("alimentacao")
    private String alimentacao;

    @SerializedName("peso")
    private String peso;

    @SerializedName("habitatResum")
    private String habitatResum;

    @SerializedName("altura")
    private String altura;

    @SerializedName("curiosidades")
    private String curiosidades;

    @SerializedName("imagem")
    private String imagem;

    @SerializedName("imagemDois")
    private String imagemDois;

    @SerializedName("imagemTres")
    private String imagemTres;

    @SerializedName("imagemQuatro")
    private String imagemQuatro;



    // Adicione getters e setters conforme necessário...

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getTipoAnim() {
        return tipoAnim;
    }

    public void setTipoAnim(String tipoAnim) {
        this.tipoAnim = tipoAnim;
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

    // Método adicionado para obter a URL da imagem
    public String getImagemUrl() {
        return imagem;
    }



    public void setHabitatResum(String habitatResum) { this.habitatResum = habitatResum; }

    public String getHabitatResum() {
        return habitatResum;
    }

    public String getImagemDois() {
        return imagemDois;
    }

    public void setImagemDois(String imagemDois) {
        this.imagemDois = imagemDois;
    }

    public String getImagemTres() {
        return imagemTres;
    }

    public void setImagemTres(String imagemTres) {
        this.imagemTres = imagemTres;
    }

    public String getImagemQuatro() {
        return imagemQuatro;
    }

    public void setImagemQuatro(String imagemQuatro) {
        this.imagemQuatro = imagemQuatro;
    }
}
