package com.example.cad_login_fb;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiServiceCard {
    @GET("api/Animal/ByNome/{nome}")
    Call<AnimalCard> getAnimalByNome(@Path("nome") String nome);
}