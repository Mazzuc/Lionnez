package com.example.cad_login_fb;

import com.google.gson.annotations.SerializedName;

public class WeatherResponse {
    @SerializedName("main")
    public MainData main;

    public static class MainData {
        @SerializedName("temp")
        public float temperature; // Temperatura em Kelvin
    }
}
