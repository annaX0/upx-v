package com.example.upx_contabil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Postagem> postagemList;

    public PostAdapter(List<Postagem> postagemList) {
        this.postagemList = postagemList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Postagem postagem = postagemList.get(position);
        holder.postName.setText(postagem.nome);
        holder.postMessage.setText(postagem.mensagem);
    }

    @Override
    public int getItemCount() {
        return postagemList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        public TextView postName;
        public TextView postMessage;

        public PostViewHolder(View itemView) {
            super(itemView);
            postName = itemView.findViewById(R.id.postName);
            postMessage = itemView.findViewById(R.id.postMessage);
        }
    }
}

