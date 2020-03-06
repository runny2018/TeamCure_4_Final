package com.example.teamcure_4;

import android.app.LauncherActivity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.ErrorListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AlloutFragment extends Fragment {

    private static final String URL_DATA="http://3.229.232.102:3000/feed";
    RecyclerView recyclerView;
    ArticlesAdaptor adaptor;
    ArrayList<Article> articles;

    private static String content = null;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        final View rootView = inflater.inflate(R.layout.fragment_allout, container, false);
        Button addPost = rootView.findViewById(R.id.add_post);

        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addPostIntent = new Intent(getActivity(), AddPost.class);
                startActivity(addPostIntent);

            }
        });

        final TextView json_text;
        json_text=rootView.findViewById(R.id.json_text);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://3.229.232.102:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, retrofit2.Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    //json_text.setText("Code: " + response.code());
                    return;
                }

                List<Post> posts = response.body();

                for (Post post : posts) {
                    content = "";

                    content += "ID: " + post.getId() + "\n";
                    content += "User ID: " + post.getUserId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";

                    content += "UID: " + post.getUID()+ "\n";
                    content += "User: " + post.getUser()+ "\n";
                    content += "Body: " + post.getBody()+ "\n";
                    content += "Image: " + post.getImg()+ "\n";
                    content += "Feeling: " + post.getFeeling()+ "\n";
                    content += "Support: " + post.getSupport()+ "\n";
                    content += "Identifier: " + post.getImg_identifier()+ "\n\n";


                    //json_text.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                //json_text.setText(t.getMessage());
            }
        });


        recyclerView=rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adaptor=new ArticlesAdaptor();
        recyclerView.setAdapter(adaptor);
        articles=new ArrayList<>();
        getData();















        return rootView;

    }

    private void getData(){
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL_DATA, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    for (int i=0;i<response.length();i++){
                        JSONObject jsonObject=response.getJSONObject(i);
                        Article article=new Article();
                        article.setImage(jsonObject.getString("img"));
                        article.setContent(jsonObject.getString("body"));
                        article.setUserName(jsonObject.getString("userName"));
                        article.setUserFeeling(jsonObject.getInt("feeling"));
                        articles.add(article);

                    }
                } catch (JSONException e){
                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }

                adaptor.setData(articles);
                adaptor.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error occured", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);

    }



    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_card_demo, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
