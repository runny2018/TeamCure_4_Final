package com.example.teamcure_4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewActivity extends AppCompatActivity {

    RatingBar ratingBar;
    Button submit_review_button;

    int otherUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        ratingBar=findViewById(R.id.rating_bar);
        submit_review_button=findViewById(R.id.submit_review_button);

        submit_review_button=findViewById(R.id.submit_review_button);

        String ratingValue=String.valueOf(ratingBar.getRating());

        if (ChooseUserActivity.uid==1){
            otherUser=2;
        }else{
            otherUser=1;
        }

        submit_review_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseBody> call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .createReview(ChooseUserActivity.uid,otherUser, Double.parseDouble(ratingValue));
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String s=response.body().string();
                            Toast.makeText(ReviewActivity.this, s, Toast.LENGTH_SHORT).show();

                            JSONObject jsonObject=new JSONObject(s);
                            /*get a particular value from this response
                            store it in a global variable and display it in points
                            section




                             */

                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(ReviewActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }
}
