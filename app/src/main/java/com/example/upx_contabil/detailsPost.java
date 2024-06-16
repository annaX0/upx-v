package com.example.upx_contabil;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class detailsPost extends AppCompatActivity {

    private TextView postName, postDate, postMessage, postEmail;
    private EditText commentInput;
    private Button btnSubmitComment;
    private RecyclerView commentsRecyclerView;
    private CommentsAdapter commentsAdapter;
    private List<Comment> commentList;
    private DatabaseReference commentsReference;
    private String postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_post);

        postName = findViewById(R.id.postName);
        postDate = findViewById(R.id.postDate);
        postMessage = findViewById(R.id.postMessage);
        postEmail = findViewById(R.id.postEmail);
        commentInput = findViewById(R.id.commentInput);
        btnSubmitComment = findViewById(R.id.btn_submitComment);
        commentsRecyclerView = findViewById(R.id.commentsRecyclerView);

        commentList = new ArrayList<>();
        commentsAdapter = new CommentsAdapter(commentList);
        commentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentsRecyclerView.setAdapter(commentsAdapter);

        String nome = getIntent().getStringExtra("postName");
        String data = getIntent().getStringExtra("postDate");
        String mensagem = getIntent().getStringExtra("postMessage");
        String email = getIntent().getStringExtra("postEmail");
        postId = getIntent().getStringExtra("postId");

        if (postId == null) {
            Toast.makeText(this, "Post ID não fornecido.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        postName.setText(nome);
        postDate.setText(data);
        postMessage.setText(mensagem);
        postEmail.setText(email);

        commentsReference = FirebaseDatabase.getInstance().getReference("postagens").child(postId).child("comments");

        loadComments();

        btnSubmitComment.setOnClickListener(v -> submitComment());

        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());
    }

    private void loadComments() {
        commentsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                commentList.clear();
                for (DataSnapshot commentSnapshot : snapshot.getChildren()) {
                    Comment comment = commentSnapshot.getValue(Comment.class);
                    if (comment != null) {
                        commentList.add(comment);
                    }
                }
                commentsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(detailsPost.this, "Falha ao carregar comentários.", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void submitComment() {
        String commentText = commentInput.getText().toString().trim();
        if (commentText.isEmpty()) {
            Toast.makeText(this, "Digite um comentário", Toast.LENGTH_SHORT).show();
            return;
        }

        String userEmail = getCurrentUserEmail();

        Comment comment = new Comment(userEmail, commentText);
        commentsReference.push().setValue(comment)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        commentInput.setText("");
                    } else {
                        Toast.makeText(detailsPost.this, "Falha ao enviar comentário.", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private String getCurrentUserEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return user != null ? user.getEmail() : "user@example.com";
    }

}
