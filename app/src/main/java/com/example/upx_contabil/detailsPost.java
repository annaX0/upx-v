package com.example.upx_contabil;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class detailsPost extends AppCompatActivity {

    private TextView postName;
    private TextView postDate;
    private TextView postMessage;
    private TextView postEmail;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_post);

        postName = findViewById(R.id.postName);
        postDate = findViewById(R.id.postDate);
        postMessage = findViewById(R.id.postMessage);
        postEmail = findViewById(R.id.postEmail);

        // Recebe os dados do Intent
        String nome = getIntent().getStringExtra("postName");
        String data = getIntent().getStringExtra("postDate");
        String mensagem = getIntent().getStringExtra("postMessage");
        String email = getIntent().getStringExtra("postEmail");

        // Define os valores nos TextViews
        postName.setText(nome);
        postDate.setText(data);
        postMessage.setText(mensagem);
        postEmail.setText(email);
    }
}