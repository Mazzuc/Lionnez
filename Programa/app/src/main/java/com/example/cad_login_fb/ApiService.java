package com.example.cad_login_fb;

import com.google.gson.JsonElement;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("api/Animal/ByNome/{searchTerm}")
    Call<JsonElement> getAnimalByName(@Path("searchTerm") String searchTerm);
}
