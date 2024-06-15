package com.example.upx_contabil;

import android.content.Intent;
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

import android.widget.CalendarView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ativos extends AppCompatActivity {

    private EditText inputSearchAtivos;
    private String currentSymbol = "IBM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ativos);

        inputSearchAtivos = findViewById(R.id.inputSearchAtivos);
        Button search = findViewById(R.id.btnSearch);

        inputSearchAtivos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                currentSymbol = charSequence.toString().toUpperCase();
            }

            @Override
            public void afterTextChanged(Editable editable) {
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
        CalendarView calendarView = findViewById(R.id.calendarView);

        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ativos.this, home.class);
                startActivity(intent);
            }
        });


        String date1 = data.getDividendDate();
        String date2 = data.getExDividendDate();

        markDateOnCalendar(calendarView, date1);
        markDateOnCalendar(calendarView, date2);

        double dividendYield = Double.parseDouble(data.getDividendYield());


        textViewPERatio.setText(data.getPERatio());
        textViewPVP.setText(data.getPriceToBookRatio());

        textViewDY.setText(String.format(Locale.US, "%.2f", dividendYield));



        textName.setText(data.getName());
        textDividendDate.setText(data.getDividendDate());
        textEx.setText(data.getExDividendDate());


    }

    private void markDateOnCalendar(CalendarView calendarView, String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date date = sdf.parse(dateString);
            if (date != null) {
                long dateInMillis = date.getTime();
                calendarView.setDate(dateInMillis, false, true);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
