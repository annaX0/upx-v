package com.example.upx_contabil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Postagem {
    public String id;
    public String nome;
    public String mensagem;
    public String data;
    public String email;

    public Postagem() {
    }

    public Postagem(String id, String nome, String mensagem, String data, String email) {
        this.id = id;
        this.nome = nome;
        this.mensagem = mensagem;
        this.data = data;
        this.email = email;
    }

    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(new Date());
    }
}
