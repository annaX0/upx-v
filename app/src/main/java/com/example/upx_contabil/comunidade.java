package com.example.upx_contabil;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class comunidade extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<Postagem> postagemList;
    private List<Postagem> filteredList;
    private DatabaseReference databaseReference;
    private EditText inputSearchAtivos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_comunidade);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        postagemList = new ArrayList<>();
        filteredList = new ArrayList<>();
        postAdapter = new PostAdapter(filteredList, new PostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Postagem postagem) {
                Intent intent = new Intent(comunidade.this, detailsPost.class);
                intent.putExtra("postName", postagem.getNome());
                intent.putExtra("postDate", postagem.getData());
                intent.putExtra("postMessage", postagem.getMensagem());
                intent.putExtra("postEmail", postagem.getEmail());
                intent.putExtra("postId", postagem.getId());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(postAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("postagens");

        inputSearchAtivos = findViewById(R.id.inputSearchAtivos);
       inputSearchAtivos.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
               filterPosts(s.toString());
           }

           @Override
           public void afterTextChanged(Editable s) {

           }
       });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postagemList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Postagem postagem = postSnapshot.getValue(Postagem.class);
                    postagemList.add(postagem);
                }
                filteredList.clear();
                filteredList.addAll(postagemList);
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(comunidade.this, "Falha ao carregar postagens.", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnBack = findViewById(R.id.btn_back);
        Button btnNewPost = findViewById(R.id.btn_newPost);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(comunidade.this, home.class);
                startActivity(intent);
            }
        });

        btnNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(comunidade.this, newPost.class);
                startActivity(intent);
            }
        });
    }

    private void filterPosts(String query) {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(postagemList);
        } else {
            for (Postagem postagem : postagemList) {
                if (postagem.getNome().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(postagem);
                }
            }
        }
        postAdapter.notifyDataSetChanged();
    }

}
