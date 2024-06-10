package com.example.upx_contabil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ativos extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ativos);

        Button search = findViewById(R.id.btnSearch);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlphaVantageAPI api = ApiClient.getClient().create(AlphaVantageAPI.class);
                Call<OverviewResponse> call = api.getOverview("OVERVIEW", "IBM", "demo");

                call.enqueue(new Callback<OverviewResponse>() {
                    @Override
                    public void onResponse(Call<OverviewResponse> call, Response<OverviewResponse> response) {
                        if (response.isSuccessful()) {
                            OverviewResponse data = response.body();
                            if (data != null) {
                                updateUI(data);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<OverviewResponse> call, Throwable t) {
                        // Handle failure
                    }
                });
            }
        });
    }

    private void updateUI(OverviewResponse data) {
        TextView textViewPERatio = findViewById(R.id.text_per_ratio);
        TextView textViewPVP = findViewById(R.id.text_pvp);
        TextView textViewDY = findViewById(R.id.text_dividend_yield);

        textViewPERatio.setText(data.getPERatio());
        textViewPVP.setText(data.getPriceToBookRatio());
        textViewDY.setText(data.getDividendYield());
    }
}