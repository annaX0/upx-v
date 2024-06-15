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
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Postagem postagem);
    }

    public PostAdapter(List<Postagem> postagemList, OnItemClickListener listener) {
        this.postagemList = postagemList;
        this.listener = listener;
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
        holder.postDate.setText(postagem.data);
        holder.bind(postagem, listener);
    }

    @Override
    public int getItemCount() {
        return postagemList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        public TextView postName, postMessage, postDate;

        public PostViewHolder(View itemView) {
            super(itemView);
            postName = itemView.findViewById(R.id.postName);
            postMessage = itemView.findViewById(R.id.postMessage);
            postDate = itemView.findViewById(R.id.postDate);
        }

        public void bind(final Postagem postagem, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(postagem);
                }
            });
        }
    }
}
