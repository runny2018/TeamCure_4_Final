package com.example.teamcure_4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.Toolbar;

import net.gotev.uploadservice.data.UploadNotificationConfig;
import net.gotev.uploadservice.protocols.multipart.MultipartUploadRequest;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.jar.Pack200;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddPost extends AppCompatActivity implements View.OnClickListener {

    private static final int PICK_IMAGE_REQUEST = 22;
    private static final int STORAGE_PERMISSION_CODE=2342;

    Button chooseImage, uploadImage;
    EditText text_field;
    ImageView post_image;

    private Uri filePath;
    private Bitmap bitmap;

    private static final String UPLOAD_URL="http://3.229.232.102:3000/";

    int support_value;
    int feeling_value;

    int counter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        counter=0;


        requestStoragePermission();

        support_value=0;

        chooseImage=findViewById(R.id.choose_image);
        uploadImage=findViewById(R.id.upload_image);

        text_field=findViewById(R.id.text_field);
        post_image=findViewById(R.id.post_image);

        chooseImage.setOnClickListener(this);
        uploadImage.setOnClickListener(this);

        Toolbar toolbar=findViewById(R.id.toolbar_in_post);

        toolbar.setTitle("Write a Post");
        toolbar.setSubtitle("Share your thoughts with other people & get support :)");

        /*toolbar.setNavigationIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_back_black_24dp, null));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
            }
        });*/

        Switch support_switch=findViewById(R.id.support_switch);
        support_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked==true){
                    support_value=1;
                }else{
                    support_value=0;
                }
            }
        });

        Spinner spinner=findViewById(R.id.spinner);
        String feelings[]={"Horrible", "Low", "Good", "Great", "Awesome"};
        ArrayAdapter<String> adapter;
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,feelings);
        spinner.setAdapter(adapter);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position)
                {
                    case 0:feeling_value=0;
                    break;
                    case 1:feeling_value=1;
                    break;
                    case 2:feeling_value=2;
                    break;
                    case 3:feeling_value=3;
                    break;
                    case 4:feeling_value=4;
                    break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        EditText text_field=findViewById(R.id.text_field);

        Button post_feed_button=findViewById(R.id.post_feed_button);
        post_feed_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFeedPost();
            }
        });

    }

    private void createFeedPost(){
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(32);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }




        PostFeed postFeed=new PostFeed(1, text_field.getText().toString(), feeling_value,
                support_value, randomStringBuilder.toString());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://3.229.232.102:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        counter++;


        Call<ResponseBody> call=RetrofitClient
                .getInstance()
                .getApi()
                .createFeedPost(counter, text_field.getText().toString(), feeling_value,
                        support_value, new Random().nextInt(99999) + 1);





        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String s=response.body().string();
                    Toast.makeText(AddPost.this, s, Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AddPost.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });







    }




    @Override
    public void onClick(View v) {
        if (v==chooseImage){
            showFileChooser();
        }
        if (v==uploadImage){
            uploadImage();
        }
    }

    private void requestStoragePermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        ==PackageManager.PERMISSION_GRANTED){
            return;
        }

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                STORAGE_PERMISSION_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==STORAGE_PERMISSION_CODE){
            if (grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            filePath=data.getData();
            try{
                /*File file = new File(filePath);
                ImageDecoder.Source source = ImageDecoder.createSource(file);
                post_image.set
                Drawable drawable = ImageDecoder.decodeDrawable(source);
                Bitmap icon = BitmapFactory.decodeResource(source,
                        R.drawable.icon_resource);
                post_image.setImageBitmap(icon);*/
                File file = new File(String.valueOf(filePath));
                ImageDecoder.Source source = ImageDecoder.createSource(file);
                Bitmap bitmap = ImageDecoder.decodeBitmap(source);
                post_image.setImageBitmap(bitmap);
            }catch(IOException e){

            }
        }
    }

    private String getPath (Uri uri){
        Cursor cursor=getContentResolver().query(uri, null,null,null,null);
        cursor.moveToFirst();
        String document_id=cursor.getString(0);
        document_id=document_id.substring(document_id.lastIndexOf(":")+1);
        cursor.close();

        cursor=getContentResolver().query(
          MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,MediaStore.Images.Media._ID+ " = ? ",
                new String[]{document_id}, null
        );
        cursor.moveToFirst();
        String path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;
    }

    private void uploadImage(){
        String text=text_field.getText().toString().trim();
        String path=getPath(filePath);

        try{
            String uploadID= UUID.randomUUID().toString();
            new MultipartUploadRequest(getApplicationContext(), uploadID)
                    .addFileToUpload(path, "image")
                    .addParameter("name", text)
                    .setMaxRetries(3)
                    .startUpload();
        }catch (Exception e){

        }

    }

}
