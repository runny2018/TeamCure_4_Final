package com.example.teamcure_4;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {

    /*@GET("posts")
    Call<List<Post>> getPosts(
            @Query("userId") Integer[] userId,
            @Query("_sort") String sort,
            @Query("_order") String order
    );

    @GET("posts")
    Call<List<Post>> getPosts(@QueryMap Map<String, String> parameters);

    @GET("posts/{id}/comments")
    Call<List<Comment>> getComments(@Path("id") int postId);

    @GET
    Call<List<Comment>> getComments(@Url String url);

    @POST("posts")
    Call<Post> createPost(@Body Post post);*/

    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> createPost(
            @Field("username") String username,
            @Field("password") String password

    );

    @GET("feed")
    Call<List<Post>> getPosts();

    @FormUrlEncoded
    @POST("feed")
    Call<ResponseBody> createFeedPost(
            @Field("user") int user,
            @Field("body") String body,
            @Field("feeling") int feeling,
            @Field("support") int support,
            @Field("imgIdentifier") int imgIdentifier

    );

    @FormUrlEncoded
    @POST("")
    Call<ResponseBody> createReview(
            @Field("userFrom") int userFrom,
            @Field("userTo") int userTo,
            @Field("rating") double rating
    );




    /*@FormUrlEncoded
    @POST("posts")
    Call<Post> createPost(@FieldMap Map<String, String> fields);*/
}
