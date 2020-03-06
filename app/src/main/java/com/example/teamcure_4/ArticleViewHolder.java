package com.example.teamcure_4;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ArticleViewHolder extends RecyclerView.ViewHolder {
    ImageView image;
    TextView content;
    TextView userName;

    TextView userFeeling;

    public ArticleViewHolder(@NonNull View itemView) {
        super(itemView);

        image=itemView.findViewById(R.id.postImage);
        content=itemView.findViewById(R.id.postContent);
        userName=itemView.findViewById(R.id.post_user_name);
        userFeeling=itemView.findViewById(R.id.feeling_of_user);


    }
}
