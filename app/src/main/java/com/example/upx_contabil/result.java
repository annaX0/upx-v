package com.example.upx_contabil;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class result extends AppCompatActivity {
    private EditText inputSearchAtivos;
    private Button btnSearch;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        inputSearchAtivos = findViewById(R.id.inputSearchAtivos);
        btnSearch = findViewById(R.id.btnSearch);
        textView = findViewById(R.id.textView);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = inputSearchAtivos.getText().toString().trim();
                if (!query.isEmpty()) {
                    searchAPI(query);
                }
            }
        });
    }

    private void searchAPI(String query) {
        String apiKey = "F2YRTWXR5LWZ4T3N";
        String url = "https://www.alphavantage.co/query?function=OVERVIEW&symbol" + query + "&apikey=" + apiKey;

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(jsonResponse);
                // Process and display your data here
                String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
                textView.setText(prettyJson);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
