package com.example.teamcure_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class ChooseUserActivity extends AppCompatActivity {

    Spinner spinner;
    String users[];
    ArrayAdapter<String> adapter;

    public static int uid;


    Button moveToMainApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_user);

        spinner=findViewById(R.id.spinner);
        users=new String[]{"User1", "User2"};

        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,users);
        spinner.setAdapter(adapter);

        moveToMainApp=findViewById(R.id.move_to_main_app_button);

        moveToMainApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ChooseUserActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position)
                {
                    case 0:uid=1;
                        break;
                    case 1:uid=2;
                        break;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
