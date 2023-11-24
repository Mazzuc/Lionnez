package com.example.cad_login_fb;
import com.google.gson.annotations.SerializedName;

public class SearchResult {

    @SerializedName("nome")
    private String nome;

    @SerializedName("descricao")
    private String descricao;

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
}
