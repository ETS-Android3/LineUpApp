package com.example.lineupapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lineupapp.adapters.WaitListeeAdapter;
import com.example.lineupapp.models.WaitList;
import com.example.lineupapp.models.WaitListee;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class QueueActivity extends AppCompatActivity {
    List<WaitListee> waitListees = new ArrayList<>();
    WaitListeeAdapter wlvAdapter;
    WaitListee fullwle = new WaitListee("FULL", "");
    TextView title, hours;
    Button popBtn;
    static String title_String, hours_String;
    ListView lv;

    static boolean gotTitle = false, hideFAB = false, hidePopBtn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue);
        // Set Title to List Name
        popBtn = findViewById(R.id.delBtn);
        if (gotTitle == false) {
            getListName();
            gotTitle = true;
        }
        // Hiding floating action button to add waitlistees if waitlist has reached time limit
        if (hideFAB == true){
            FloatingActionButton addListeeBtn = findViewById(R.id.addWaitlistee);
            addListeeBtn.hide();
        }
        // Pop button does not appear if no waitlistees exist and the first is full
        if (hidePopBtn == true){
            popBtn.setVisibility(View.GONE);
        }
        // Configuring List View
        lv = findViewById(R.id.wlv);
        wlvAdapter = new WaitListeeAdapter(this, R.layout.adapter_view_listees_layout, waitListees);
        addAllWaitListees();
        addWaitListee();
        lv.setAdapter(wlvAdapter);

        // Pop first waitListee
        popBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ProfessorActivity.removeListee(title_String, waitListees.get(0))) {
                    Toast.makeText(QueueActivity.this, "Done with " + waitListees.get(0), Toast.LENGTH_SHORT).show();
                    waitListees.remove(0);
                    if (waitListees.get(0).getName().equals("FULL")){
                        popBtn.setVisibility(View.GONE);
                        hidePopBtn = true;
                    }
                    wlvAdapter.notifyDataSetChanged();
                    System.out.println("Removed");
                }
            }
        });
    }

    public void backToListsBtnOnClick(View view) {
        gotTitle = false;
        Intent intent = new Intent(this, ProfessorActivity.class);
        startActivity(intent);
    }

    public void addWaitListeeBtnOnClick(View view) {
        System.out.println("Add Waitlistee Button.");
        Intent intent = new Intent(this, AddWaitListeeActivity.class);
        startActivity(intent);
    }

    public void addWaitListee() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getString("WAITLISTEE_NAME") != null || bundle.getString("TIME_NEEDED") != null) {
                WaitListee waitListee = new WaitListee(bundle.getString("WAITLISTEE_NAME"), bundle.getString("TIME_NEEDED"));
                if (ProfessorActivity.addListee(title_String, waitListee)) {
                    waitListees.add(waitListee);
                    Toast.makeText(QueueActivity.this, "Added " + waitListees.get(waitListees.size() - 1).getName(), Toast.LENGTH_SHORT).show();
                    if (bundle.getString("ADDED_TIME") != null || bundle.getString("FINAL_TIME") != null) {
                        if (bundle.getString("ADDED_TIME").equals(bundle.getString("FINAL_TIME"))) {
                            System.out.println("added_time: "+bundle.getString("ADDED_TIME")+", final_time: " + bundle.getString("FINAL_TIME"));
                            waitListees.add(fullwle);
                            WaitListeeAdapter.getTvName().setTextColor(Color.RED); // Redundant currently
                            FloatingActionButton addListeeBtn = findViewById(R.id.addWaitlistee);
                            addListeeBtn.hide();
                            hideFAB = true;
                        }
                    }
                    wlvAdapter.notifyDataSetChanged();
                }
            }
        }
        title = findViewById(R.id.qHeader);
        title.setText(title_String);
        hours = findViewById(R.id.hourstv);
        hours.setText(hours_String);
    }

    public void addAllWaitListees() {
        // Finding list
        for (WaitList wl : ProfessorActivity.getWaitLists()) {
            if (wl.getName().equals(title_String)) {
                waitListees.clear();
                // Inserting Waitlistees into current waitlistees and adapter
                for (WaitListee wle : wl.getWaitListees()) {
                    waitListees.add(wle);
                    wlvAdapter.notifyDataSetChanged();
                }
                break;
            }
        }
        if (hidePopBtn == true){
            waitListees.add(fullwle);
            WaitListeeAdapter.getTvName().setTextColor(Color.RED); // Redundant currently
            wlvAdapter.notifyDataSetChanged();
        }
    }

    public void getListName() {
        title = findViewById(R.id.qHeader);
        hours = findViewById(R.id.hourstv);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            hours_String = bundle.getString("HOURS");
            hours.setText(hours_String);
            title.setText(bundle.getString("LIST_NAME"));
            title_String = title.getText().toString();
            System.out.println("Title: " + title_String);
        }
    }
}
