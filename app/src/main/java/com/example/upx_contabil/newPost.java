package com.example.upx_contabil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class newPost extends AppCompatActivity {
    private EditText inputName, inputMsg;
    private Button btnCadastrar, btnBack;

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        inputName = findViewById(R.id.input_name);
        inputMsg = findViewById(R.id.input_msg);
        btnCadastrar = findViewById(R.id.btnCadastrar);
        btnBack = findViewById(R.id.btn_back);

        databaseReference = FirebaseDatabase.getInstance().getReference("postagens");
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(newPost.this, comunidade.class);
                startActivity(intent);
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarPostagem();
            }
        });
    }

    private void cadastrarPostagem() {
        String nome = inputName.getText().toString().trim();
        String mensagem = inputMsg.getText().toString().trim();
        String email = currentUser != null ? currentUser.getEmail() : "Email não disponível";
        String data = Postagem.getCurrentDate();


        if (nome.isEmpty()) {
            inputName.setError("Nome é obrigatório");
            inputName.requestFocus();
            return;
        }

        if (mensagem.isEmpty()) {
            inputMsg.setError("Mensagem é obrigatória");
            inputMsg.requestFocus();
            return;
        }

        String postId = databaseReference.push().getKey();
        Postagem postagem = new Postagem(postId, nome, mensagem, data, email);

        if (postId != null) {
            databaseReference.child(postId).setValue(postagem)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(newPost.this, "Postagem cadastrada com sucesso!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(newPost.this, "Falha ao cadastrar postagem.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
