package com.example.upx_contabil;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class register extends AppCompatActivity {
    private EditText inputName, inputEmail, inputPassword;
    private Button btnRegister, btnLogin;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        inputName = findViewById(R.id.input_name);
        inputEmail = findViewById(R.id.input_mail);
        inputPassword = findViewById(R.id.input_password);
        btnRegister = findViewById(R.id.btn_register);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(register.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputName.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
                    Toast.makeText(register.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(email, password);
                }
            }
        });
    }

    private void registerUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(register.this, "Registro bem-sucedido!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(register.this, "Falha ao registrar: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
