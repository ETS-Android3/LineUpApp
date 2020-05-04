package com.example.lineupapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lineupapp.models.WaitList;
import com.example.lineupapp.models.WaitListee;
import com.example.lineupapp.adapters.WaitListeeAdapter;

import java.util.ArrayList;
import java.util.List;

public class QueueActivity extends AppCompatActivity {
    List<WaitListee> waitListees = new ArrayList<>();
    WaitListeeAdapter wlvAdapter;
    TextView title, hours;
    static String title_String, hours_String;
    ListView lv;

    static boolean gotTitle = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue);
        // Set Title to List Name
        if (gotTitle == false) {
            getListName();
            gotTitle = true;
        }
        // Configuring List View
        lv = (ListView) findViewById(R.id.wlv);
        wlvAdapter = new WaitListeeAdapter(this, R.layout.adapter_view_listees_layout, waitListees);
        addAllWaitListees();
        addWaitListee();
        lv.setAdapter(wlvAdapter);

        // Remove Listee when clicking on delete button
        this.wlvAdapter.setOnDelClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WaitListee currentwle = new WaitListee(WaitListeeAdapter.getTvName().getText().toString(), WaitListeeAdapter.getTvTime().getText().toString());
                System.out.println("currentwle: " + currentwle);
                if (ProfessorActivity.removeListee(title_String, currentwle)){
                    Toast.makeText(QueueActivity.this, "Done with " + currentwle.getName(), Toast.LENGTH_SHORT).show();
                    for (WaitListee wle : waitListees){
                        if (currentwle.getName().equals(wle.getName()) && currentwle.getTimeNeeded().equals(wle.getTimeNeeded())) {
                            System.out.println("currentwle matched in waitListees (QueueActivitiy). Proceeding to delete.");
                            waitListees.remove(wle);
                            wlvAdapter.notifyDataSetChanged();
                        }
                    }
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
        System.out.println("Add Waitlistee");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getString("WAITLISTEE_NAME") != null || bundle.getString("TIME_NEEDED") != null) {
                WaitListee waitListee = new WaitListee(bundle.getString("WAITLISTEE_NAME"), bundle.getString("TIME_NEEDED"));
                if (ProfessorActivity.addListee(title_String, waitListee)) {
                    waitListees.add(waitListee);
                    wlvAdapter.notifyDataSetChanged();
                    Toast.makeText(QueueActivity.this, "Added " + waitListees.get(waitListees.size() - 1).getName(), Toast.LENGTH_SHORT).show();
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
            System.out.println("Title: "+title_String);
        }
    }
}
