package com.example.upx_contabil;

import android.os.Bundle;
import android.util.Log; // Adicionado import do Log
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ativos extends AppCompatActivity {

    private EditText inputSearchAtivos;  // EditText para entrada do símbolo
    private String currentSymbol = "IBM"; // Valor inicial do símbolo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ativos);

        inputSearchAtivos = findViewById(R.id.inputSearchAtivos);
        Button search = findViewById(R.id.btnSearch);

        // Adiciona um TextWatcher para capturar mudanças no EditText
        inputSearchAtivos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // Não necessário para esta funcionalidade
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Atualiza o símbolo ao digitar
                currentSymbol = charSequence.toString().toUpperCase(); // Converte para maiúsculas
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Não necessário para esta funcionalidade
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchStockData(currentSymbol);
            }
        });
    }

    private void fetchStockData(String symbol) {
        AlphaVantageAPI api = ApiClient.getClient().create(AlphaVantageAPI.class);
        String apiKey = "7XD6KQZLOSTHRF5W";
        Call<OverviewResponse> call = api.getOverview("OVERVIEW", symbol, apiKey);

        call.enqueue(new Callback<OverviewResponse>() {
            @Override
            public void onResponse(Call<OverviewResponse> call, Response<OverviewResponse> response) {
                if (response.isSuccessful()) {
                    OverviewResponse data = response.body();
                    if (data != null) {
                        Log.d("API Response", "Data: " + data.toString());
                        updateUI(data);
                    } else {
                        Log.d("API Response", "Response body is null");
                    }
                } else {
                    Log.d("API Response", "Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<OverviewResponse> call, Throwable t) {
                Log.e("API Error", "Error: " + t.getMessage());
            }
        });
    }


    private void updateUI(OverviewResponse data) {



        TextView textViewPERatio = findViewById(R.id.text_per_ratio);
        TextView textViewPVP = findViewById(R.id.text_pvp);
        TextView textViewDY = findViewById(R.id.text_dividend_yield);
        TextView textName = findViewById(R.id.txtName);
        TextView textDividendDate = findViewById(R.id.text_DividendDate);
        TextView textEx = findViewById(R.id.text_ExDividendDate);

        textViewPERatio.setText(data.getPERatio());
        textViewPVP.setText(data.getPriceToBookRatio());
        textViewDY.setText(data.getDividendYield());
        textName.setText(data.getName());
        textDividendDate.setText(data.getDividendDate());
        textEx.setText(data.getExDividendDate());
    }
}
