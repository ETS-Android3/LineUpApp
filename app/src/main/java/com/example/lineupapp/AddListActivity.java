package com.example.lineupapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class AddListActivity extends AppCompatActivity {
    Button addedListBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addlist);

        addListOnClick();
    }

    public void addListOnClick() {

        addedListBtn = (Button) findViewById(R.id.addedListBtn);

        addedListBtn.setOnClickListener(new View.OnClickListener() {
            private EditText et;
            private EditText etTimeInit;
            private EditText etTimeFinal;

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                et = findViewById(R.id.listEt);
                etTimeInit = findViewById(R.id.timeToet);
                etTimeFinal = findViewById(R.id.timeFinalet);

                System.out.println("Add list Button Clicked.");
                Intent intent = new Intent(AddListActivity.this, ProfessorActivity.class);
                intent.putExtra("LIST_NAME", et.getText().toString());
                intent.putExtra("TIME_INIT", etTimeInit.getText().toString());
                intent.putExtra("TIME_FINAL", etTimeFinal.getText().toString());
                startActivity(intent);

            }
        });


    }
}
