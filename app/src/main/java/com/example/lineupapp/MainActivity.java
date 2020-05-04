package com.example.lineupapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // activity_main
        final Button loginBtn = (Button) findViewById(R.id.loginBtn);

        // activity_professor

        // activity_queue

//        loginBtn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                setContentView(R.layout.activity_professor);
//                Intent intent = new Intent(this, ProfessorActivity.class);
//            }
//        });
//
//        Button createAccBtn = (Button) findViewById(R.id.createAccBtn);
//        createAccBtn.setOnClickListener(new View.OnClickListener(){         // Configure
//            @Override
//            public void onClick(View view){
//
//            }
//        });
    }

    public void loginBtnActivity(View view) {
        System.out.println("Login button activity.");
        Intent intent = new Intent(this, ProfessorActivity.class);
        startActivity(intent);
    }

    public void createAccBtnActivity(View view) {
        System.out.println("Create account button activity.");
    }

}
