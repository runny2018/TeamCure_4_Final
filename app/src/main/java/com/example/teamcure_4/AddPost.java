package com.example.teamcure_4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import net.gotev.uploadservice.data.UploadNotificationConfig;
import net.gotev.uploadservice.protocols.multipart.MultipartUploadRequest;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.jar.Pack200;

public class AddPost extends AppCompatActivity implements View.OnClickListener {

    private static final int PICK_IMAGE_REQUEST = 22;
    private static final int STORAGE_PERMISSION_CODE=2342;

    Button chooseImage, uploadImage;
    EditText text_field;
    ImageView post_image;

    private Uri filePath;
    private Bitmap bitmap;

    private static final String UPLOAD_URL="ads";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        requestStoragePermission();

        chooseImage=findViewById(R.id.choose_image);
        uploadImage=findViewById(R.id.upload_image);

        text_field=findViewById(R.id.text_field);
        post_image=findViewById(R.id.post_image);

        chooseImage.setOnClickListener(this);
        uploadImage.setOnClickListener(this);






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
