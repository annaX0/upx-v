package com.example.upx_contabil;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class conceitos extends AppCompatActivity {

    private EditText editTextNome, editTextTexto, inputSearchConceitos;
    private DatabaseReference databaseReference;
    private LinearLayout conceitosContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_conceitos);

        editTextNome = findViewById(R.id.editTextNome);
        editTextTexto = findViewById(R.id.editTextTexto);
        inputSearchConceitos = findViewById(R.id.inputSearchConceitos);
        Button btnBack = findViewById(R.id.btn_back);
        Button btnNewConceito = findViewById(R.id.newConceito);
        conceitosContainer = findViewById(R.id.conceitosContainer);

        databaseReference = FirebaseDatabase.getInstance().getReference("conceitos");

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(conceitos.this, home.class);
                startActivity(intent);
            }
        });

        btnNewConceito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = editTextNome.getText().toString().trim();
                String texto = editTextTexto.getText().toString().trim();

                if (!nome.isEmpty() && !texto.isEmpty()) {
                    String id = databaseReference.push().getKey();
                    Conceito conceito = new Conceito(nome, texto, false);
                    databaseReference.child(id).setValue(conceito);
                    Toast.makeText(conceitos.this, "Conceito adicionado para aprovação", Toast.LENGTH_SHORT).show();
                    clearInputFields();
                } else {
                    Toast.makeText(conceitos.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        inputSearchConceitos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterConceitos(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        loadConceitos("");
    }

    private void clearInputFields() {
        editTextNome.setText("");
        editTextTexto.setText("");
    }

    private void loadConceitos(String filter) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                conceitosContainer.removeAllViews();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Conceito conceito = snapshot.getValue(Conceito.class);
                    if (conceito != null && conceito.visivel && (filter.isEmpty() || conceito.nome.toLowerCase().contains(filter.toLowerCase()))) {
                        addConceitoView(conceito.nome, conceito.texto);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(conceitos.this, "Erro ao carregar conceitos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterConceitos(String query) {
        loadConceitos(query.trim());
    }

    private void addConceitoView(String nome, String texto) {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        TextView nomeTextView = new TextView(this);
        nomeTextView.setText(nome);
        nomeTextView.setTextColor(getResources().getColor(android.R.color.white));
        nomeTextView.setTextSize(24);
        layout.addView(nomeTextView);

        TextView textoTextView = new TextView(this);
        textoTextView.setText(texto);
        textoTextView.setTextColor(getResources().getColor(android.R.color.white));
        textoTextView.setTextSize(16);
        layout.addView(textoTextView);

        conceitosContainer.addView(layout);
    }

    public static class Conceito {
        public String nome;
        public String texto;
        public boolean visivel;

        public Conceito() {
        }

        public Conceito(String nome, String texto, boolean visivel) {
            this.nome = nome;
            this.texto = texto;
            this.visivel = visivel;
        }
    }
}
