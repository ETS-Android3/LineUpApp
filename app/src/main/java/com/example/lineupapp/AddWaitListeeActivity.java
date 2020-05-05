package com.example.lineupapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lineupapp.models.WaitList;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;

@RequiresApi(api = Build.VERSION_CODES.O)
public class AddWaitListeeActivity extends AppCompatActivity {
    EditText etName, etTime;
    String timeStr, addedTimeStr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addwaitlistee);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addedWaitlisteeBtnOnClick(View view) {
        etName = findViewById(R.id.waitListeeNameEt);
        etTime = findViewById(R.id.timeNeeded);

        // Adding timeNeeded to initial time available
        for (WaitList wl : ProfessorActivity.getWaitLists()) {
            if (wl.getName().equals(QueueActivity.title_String)) {
                System.out.println("Title in AddWaitListee: " + QueueActivity.title_String);
                DateTimeFormatter dfs = new DateTimeFormatterBuilder()
                        .appendOptional(DateTimeFormatter.ofPattern("HH:mm:ss"))
                        .appendOptional(DateTimeFormatter.ofPattern("HH:mm"))
                        .appendOptional(DateTimeFormatter.ofPattern("HH")).toFormatter();
                try {
                    LocalTime time = LocalTime.parse(wl.getNextAvailTime(), dfs);
                    LocalTime finalTime = LocalTime.parse(wl.getTimeFinal(), dfs);
                    LocalTime addedTime = time.plusMinutes(Long.parseLong(etTime.getText().toString()));
                    System.out.println("Time:" + time);
                    System.out.println("Final time:  " + finalTime);
                    System.out.println("Added time: " + addedTime);
                    if (addedTime.isAfter(finalTime)) {
                        Toast.makeText(AddWaitListeeActivity.this, "ERROR. TIME LIMIT EXCEEDED", Toast.LENGTH_SHORT).show();
                    } else if (addedTime.equals(time)) {
                        Toast.makeText(AddWaitListeeActivity.this, "ERROR. ADDED TIME CANNOT BE ZERO", Toast.LENGTH_LONG).show();
                    } else {
                        timeStr = time + " to " + addedTime + "";
                        addedTimeStr = addedTime + "";
                        String finalTimeStr = ""+finalTime;
                        wl.setNextAvailTime(addedTimeStr);
                        Intent intent = new Intent(this, QueueActivity.class);
                        intent.putExtra("WAITLISTEE_NAME", etName.getText().toString());
                        intent.putExtra("TIME_NEEDED", timeStr);
                        intent.putExtra("FINAL_TIME", finalTimeStr);
                        intent.putExtra("ADDED_TIME", addedTimeStr);
                        startActivity(intent);
                    }
                } catch (DateTimeParseException e) {
                    Toast.makeText(AddWaitListeeActivity.this, "ERROR. TIME FORMAT NOT SUPPORTED", Toast.LENGTH_LONG).show();
                }

            } else {
                System.out.println("Title string does not match.");
            }
        }


    }
}
