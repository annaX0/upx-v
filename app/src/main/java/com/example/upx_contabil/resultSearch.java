package com.example.upx_contabil;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class resultSearch extends AppCompatActivity {
    private EditText inputSearchAtivos;
    private TextView textViewEBITDA;

    private static final String BASE_URL = "https://www.alphavantage.co/";
    private static final String API_KEY = "F2YRTWXR5LWZ4T3N";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_search);

        inputSearchAtivos = findViewById(R.id.inputSearchAtivos);
        textViewEBITDA = findViewById(R.id.textViewEBITDA);

        Button btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String symbol = inputSearchAtivos.getText().toString().trim();
                System.out.print(symbol);
                if (!symbol.isEmpty()) {
                    fetchCompanyOverview(symbol);
                }
            }
        });
    }
    private void fetchCompanyOverview(String symbol) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AlphaVantageService service = retrofit.create(AlphaVantageService.class);
        Call<CompanyOverview> call = service.getCompanyOverview(symbol, API_KEY);

        call.enqueue(new Callback<CompanyOverview>() {
            @Override
            public void onResponse(Call<CompanyOverview> call, Response<CompanyOverview> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CompanyOverview overview = response.body();
                    textViewEBITDA.setText("EBITDA: " + overview.getEbitda());
                } else {
                    Log.e("API_CALL", "Response not successful: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<CompanyOverview> call, Throwable t) {
                Log.e("API_CALL", "Failed to make API call", t);
            }
        });
    }
}