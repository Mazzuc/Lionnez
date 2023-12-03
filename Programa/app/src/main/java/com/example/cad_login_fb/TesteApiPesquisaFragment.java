package com.example.cad_login_fb;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TesteApiPesquisaFragment extends Fragment {

    private EditText editTextSearch;
    private RecyclerView recyclerViewResults;
    private SearchResultAdapter adapter;
    private ApiService apiService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_teste_api_pesquisa, container, false);

        // Inicialize o Retrofit aqui
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://zooapi-efub.onrender.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .build())
                .build();

        apiService = retrofit.create(ApiService.class);

        editTextSearch = view.findViewById(R.id.editTextSearch);
        recyclerViewResults = view.findViewById(R.id.recyclerViewResults);

        adapter = new SearchResultAdapter(new ArrayList<>());
        recyclerViewResults.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewResults.setAdapter(adapter);

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchTerm = charSequence.toString();
                getAnimalList(searchTerm);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        adapter.setOnItemClickListener(new SearchResultAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SearchResult item) {
                openDetalhesActivity(item);
            }
        });

        Button btnMamifero = view.findViewById(R.id.btmamifero);
        btnMamifero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carregarAnimaisPorTipo(1);
            }
        });

        Button btnAves = view.findViewById(R.id.btnaves);
        btnAves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carregarAnimaisPorTipo(2);
            }
        });

        Button btnAnfibios = view.findViewById(R.id.btAnfibios);
        btnAnfibios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carregarAnimaisPorTipo(3);
            }
        });

        Button btnReptil = view.findViewById(R.id.btReptil);
        btnReptil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carregarAnimaisPorTipo(4);
            }
        });

        Button btnAquaticos = view.findViewById(R.id.btnAquaticos);
        btnAquaticos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carregarAnimaisPorTipo(5);
            }
        });


        return view;
    }

    private void getAnimalList(String searchTerm) {
        Call<JsonElement> call = apiService.getAnimalByName(searchTerm);

        // imprimir a URL no logcat
        Log.d("URL da API", call.request().url().toString());

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                // Verifica se a resposta da requisição foi bem-sucedida (código 2xx)
                if (response.isSuccessful()) {
                    // Obtém o corpo da resposta
                    JsonElement body = response.body();

                    // Verifica se o corpo não é nulo
                    if (body != null) {
                        // Verifica se o corpo é um array JSON
                        if (body.isJsonArray()) {
                            // Converte o corpo para um array JSON
                            JsonArray jsonArray = body.getAsJsonArray();

                            // Parseia o array JSON para uma lista de SearchResult
                            List<SearchResult> searchResults = parseJsonArray(jsonArray);

                            // Chama o método updateUI para atualizar a UI com os resultados
                            updateUI(searchResults);
                        } else if (body.isJsonObject()) {
                            // Caso o corpo seja um objeto JSON

                            // Converte o corpo para um objeto JSON
                            JsonObject jsonObject = body.getAsJsonObject();

                            // Parseia o objeto JSON para um SearchResult
                            SearchResult singleResult = parseJsonObject(jsonObject);

                            // Cria uma lista contendo apenas o SearchResult obtido
                            List<SearchResult> searchResults = new ArrayList<>();
                            searchResults.add(singleResult);

                            // Chama o método updateUI para atualizar a UI com os resultados
                            updateUI(searchResults);
                        }
                    } else {
                        // Log de erro em caso de resposta vazia
                        Log.e("API Erro", "Resposta vazia");
                    }
                } else {
                    // Log de erro em caso de falha na requisição (código diferente de 2xx)
                    Log.e("API Erro", "Falha ao buscar dados: " + response.code());

                    // Adicione este log para ver mais detalhes sobre a resposta
                    try {
                        Log.e("API Erro", "Resposta: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e("API Erro", "Failed to make API call", t);
                if (t instanceof HttpException) {
                    Response<?> response = ((HttpException) t).response();
                    try {
                        Log.e("API Erro", "Response: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }



    private void carregarAnimaisPorTipo(int tipoId) {
        Call<JsonElement> call = apiService.getAnimaisPorTipo(tipoId);

        // Adicione este log para imprimir a URL no Logcat
        Log.d("URL da API", call.request().url().toString());

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful()) {
                    JsonElement body = response.body();
                    if (body != null && body.isJsonArray()) {
                        JsonArray jsonArray = body.getAsJsonArray();
                        List<SearchResult> searchResults = parseJsonArray(jsonArray);
                        updateUI(searchResults);
                    } else {
                        Log.e("API Erro", "Resposta inválida: " + body);
                    }
                } else {
                    Log.e("API Erro", "Resposta não bem-sucedida: " + response.code());
                    try {
                        Log.e("API Erro", "Resposta: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e("API Erro", "Falha ao fazer a chamada da API", t);
                if (t instanceof HttpException) {
                    Response<?> response = ((HttpException) t).response();
                    try {
                        Log.e("API Erro", "Response: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    private List<SearchResult> parseJsonArray(JsonArray jsonArray) {
        List<SearchResult> searchResults = new ArrayList<>();

        for (JsonElement jsonElement : jsonArray) {
            if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                SearchResult searchResult = parseJsonObject(jsonObject);
                searchResults.add(searchResult);
            }
        }

        return searchResults;
    }

    private SearchResult parseJsonObject(JsonObject jsonObject) {
        SearchResult searchResult = new SearchResult();

        if (jsonObject.has("nome")) {
            searchResult.setNome(jsonObject.get("nome").getAsString());
        }

        if (jsonObject.has("descricao")) {
            searchResult.setDescricao(jsonObject.get("descricao").getAsString());
        }

        // Adicione outros campos conforme necessário
        if (jsonObject.has("habitat") && !jsonObject.get("habitat").isJsonNull()) {
            searchResult.setHabitat(jsonObject.get("habitat").getAsString());
        }

        if (jsonObject.has("habitatResum") && !jsonObject.get("habitatResum").isJsonNull()) {
            searchResult.setHabitatResum(jsonObject.get("habitatResum").getAsString());
        }

        if (jsonObject.has("pais") && !jsonObject.get("pais").isJsonNull()) {
            searchResult.setPais(jsonObject.get("pais").getAsString());
        }

        if (jsonObject.has("alimentacao") && !jsonObject.get("alimentacao").isJsonNull()) {
            searchResult.setAlimentacao(jsonObject.get("alimentacao").getAsString());
        }

        if (jsonObject.has("peso") && !jsonObject.get("peso").isJsonNull()) {
            searchResult.setPeso(jsonObject.get("peso").getAsString());
        }

        if (jsonObject.has("altura") && !jsonObject.get("altura").isJsonNull()) {
            searchResult.setAltura(jsonObject.get("altura").getAsString());
        }

        if (jsonObject.has("curiosidades") && !jsonObject.get("curiosidades").isJsonNull()) {
            searchResult.setCuriosidades(jsonObject.get("curiosidades").getAsString());
        }

        if (jsonObject.has("imagem") && !jsonObject.get("imagem").isJsonNull()) {
            searchResult.setImagem(jsonObject.get("imagem").getAsString());
        }
        if (jsonObject.has("imagemDois") && !jsonObject.get("imagemDois").isJsonNull()) {
            searchResult.setImagemDois(jsonObject.get("imagemDois").getAsString());
        }
        if (jsonObject.has("imagemTres") && !jsonObject.get("imagemTres").isJsonNull()) {
            searchResult.setImagemTres(jsonObject.get("imagemTres").getAsString());
        }
        if (jsonObject.has("imagemQuatro") && !jsonObject.get("imagemQuatro").isJsonNull()) {
            searchResult.setImagemQuatro(jsonObject.get("imagemQuatro").getAsString());
        }

        return searchResult;
    }

    private void updateUI(List<SearchResult> searchResults) {
        adapter.setData(searchResults);
    }

    private void openDetalhesActivity(SearchResult item) {
        Intent intent = new Intent(getActivity(), DetalhesActivity.class);
        intent.putExtra("title", item.getNome());
        intent.putExtra("description", item.getDescricao());
        intent.putExtra("habitatResum", item.getHabitatResum());
        intent.putExtra("pais", item.getPais());
        intent.putExtra("alimentacao", item.getAlimentacao());
        intent.putExtra("peso", item.getPeso());
        intent.putExtra("altura", item.getAltura());
        intent.putExtra("curiosidades", item.getCuriosidades());
        intent.putExtra("imagem", item.getImagem());
        intent.putExtra("imagemDois", item.getImagemDois());
        intent.putExtra("imagemTres", item.getImagemTres());
        intent.putExtra("imagemQuatro", item.getImagemQuatro());
        startActivity(intent);
    }
}