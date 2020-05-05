package com.example.lineupapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lineupapp.adapters.WaitListAdapter;
import com.example.lineupapp.models.WaitList;
import com.example.lineupapp.models.WaitListee;

import java.util.ArrayList;
import java.util.List;

public class ProfessorActivity extends AppCompatActivity {
    private static List<WaitList> waitLists = new ArrayList<WaitList>();
    private static List<String> lists = new ArrayList<>();
    private static WaitListAdapter adapter;
    private int mPosition;
    private AdapterView<?> mParent;
    ListView lv;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor);

        lv = (ListView) findViewById(R.id.listView);
        adapter = new WaitListAdapter(this, R.layout.adapter_view_lists_layout, waitLists);
        addList();
        lv.setAdapter(adapter);

        // Clicking on a list action
//        this.adapter.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//                String listName = WaitListAdapter.getTvName().getText().toString();
//                String timeInit = WaitListAdapter.getTvTimeInit().getText().toString();
//                String timeFin = WaitListAdapter.getTvTimeFinal().getText().toString();
//                Intent intent = new Intent(ProfessorActivity.this, QueueActivity.class);
//                System.out.println("listName: " + listName);
//                System.out.println("timeInit: " + timeInit);
//                System.out.println("timeFin: " + timeFin);
//                System.out.println("mPosition from WaitListAdapter: " + WaitListAdapter.getmPosition());
//                for (WaitList wl : waitLists){
//                    if (wl.getName().equals(listName) && wl.getTimeInit().equals(timeInit) && wl.getTimeFinal().equals(timeFin)){
//                        System.out.println("List matched.");
//                        intent.putExtra("LIST_NAME", wl.getName());
//                        intent.putExtra("HOURS", "Hours: "+wl.getTimeInit() + " to " + wl.getTimeFinal());
//                        startActivity(intent);
//                    }
//                }
//                System.out.println("list not found");
//            }
//        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("CLICKED");
                // GET POSITION
                // GET WAITLIST
                // GO INTO QUEUE ACTIVITYs
                mPosition = position;
                mParent = parent;
                WaitList waitList = (WaitList) parent.getItemAtPosition(position);
                Intent intent = new Intent(ProfessorActivity.this, QueueActivity.class);
                intent.putExtra("LIST_NAME", waitList.getName());
                intent.putExtra("HOURS", "Hours: " + waitList.getTimeInit() + " to " + waitList.getTimeFinal());
                startActivity(intent);
            }
        });

//        this.adapter.setOnDelListClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//                // Find waitlist -> Remove it
//                for (WaitList wl : waitLists){
//                    if (wl.getName().equals(WaitListAdapter.getTvName().getText()) && wl.getTimeInit().equals(WaitListAdapter.getTvTimeInit().getText())
//                            && wl.getTimeFinal().equals(WaitListAdapter.getTvTimeFinal().getText())){
//                        Toast.makeText(ProfessorActivity.this, "Removed " +  wl.getName(), Toast.LENGTH_SHORT).show();
//                        waitLists.remove(wl);
//                        adapter.notifyDataSetChanged();
//                        System.out.println("Removed list.");
//                    }
//                }
//            }
//        });
    }

    public void onDelListClickListener(View view) {
        Toast.makeText(ProfessorActivity.this, "Feature not supported yet", Toast.LENGTH_SHORT).show();
//        WaitList waitList = (WaitList) mParent.getItemAtPosition(mPosition);
//        System.out.println("waitList in del: " + waitList);
//        for (WaitList wl : waitLists){
//            if (wl.getName().equals(waitList.getName()) && wl.getTimeInit().equals(waitList.getTimeInit())
//                    && wl.getTimeFinal().equals(waitList.getTimeFinal())){
//                Toast.makeText(ProfessorActivity.this, "Removed " +  wl.getName(), Toast.LENGTH_SHORT).show();
//                waitLists.remove(wl);
//                adapter.notifyDataSetChanged();
//                System.out.println("Removed list.");
//            }
//        }
    }

    public static List<WaitList> getWaitLists() {
        List<WaitList> wlClone = new ArrayList<WaitList>();
        wlClone.addAll(waitLists);
        return wlClone;
    }

    public static boolean addListee(String listName, WaitListee waitListee) {
        System.out.println("Trying to find waitlist: " + listName);
        for (WaitList wl : waitLists) {
            if (listName.equals(wl.getName())) {
                wl.addWaitListee(waitListee);
                return true;
            }
        }
        System.out.println("List not found.");
        return false;
    }

    public static boolean removeListee(String listName, WaitListee waitListee) {
        for (WaitList wl : waitLists) {
            if (listName.equals(wl.getName())) {
                wl.removeWaitListee(waitListee);
                return true;
            }
        }
        System.out.println("List not found.");
        return false;
    }

    public void addListBtnActivity(View view) {
        System.out.println("Add Waitlist button activity.");
        Intent intent = new Intent(this, AddListActivity.class);
        startActivity(intent);
    }

    public void backBtnActivity(View view) {
        System.out.println("Going back to login page.");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addList() {
        System.out.println("Add List method");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getString("LIST_NAME") != null) {
                WaitList waitList = new WaitList(bundle.getString("LIST_NAME"), bundle.getString("TIME_INIT"), bundle.getString("TIME_FINAL"));
                waitLists.add(waitList);
                adapter.notifyDataSetChanged();
                Toast.makeText(ProfessorActivity.this, "Added " + waitLists.get(waitLists.size() - 1).getName(), Toast.LENGTH_SHORT).show();
                System.out.println("Added list: " + bundle.getString("LIST_NAME"));
            }
        } else {
            System.out.println("Bundle is empty");
        }
    }


}
