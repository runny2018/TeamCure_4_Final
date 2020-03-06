package com.example.teamcure_4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ArticlesAdaptor extends RecyclerView.Adapter<ArticleViewHolder> {

    ArrayList<Article> articles;

    public ArticlesAdaptor() {
        articles=new ArrayList<>();
    }

    public void setData(ArrayList<Article> articles){
        this.articles=articles;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View articleView =layoutInflater.inflate(R.layout.post_recycler_row,parent, false);
        return new ArticleViewHolder(articleView);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article article=articles.get(position);
        Picasso.get().load(article.image).into(holder.image);
        holder.content.setText(article.content);
        holder.userName.setText(article.userName);
        holder.userFeeling.setText(String.valueOf(article.userFeeling));

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
}
