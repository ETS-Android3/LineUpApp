package com.example.lineupapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lineupapp.R;
import com.example.lineupapp.models.WaitList;

import java.util.List;

public class WaitListAdapter extends ArrayAdapter<WaitList> {

    private Context mContext;
    private int mResource;
    private static int mPosition;
    private static TextView tvName, tvTimeInit, tvTimeFinal;
    private View.OnClickListener onDelListClickListener, onClickListener;


    public WaitListAdapter(Context context, int resource, List<WaitList> waitLists){
        super(context, resource, waitLists);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent){
        mPosition = position;

        String name = getItem(position).getName();
        String timeInit = getItem(position).getTimeInit();
        String timeFinal = getItem(position).getTimeFinal();

        System.out.println("position: " + position);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

//
        tvName = convertView.findViewById(R.id.listName);
        tvTimeInit = convertView.findViewById(R.id.timeInittv);
        tvTimeFinal = convertView.findViewById(R.id.timeFinaltv);

//        System.out.println("name: "+ tvName.getText().toString());
//        System.out.println("timeinit: " +tvTimeInit.getText().toString());
//        System.out.println("timeFINAL: " +tvTimeFinal.getText().toString());

//        convertView.findViewById(R.id.delListBtn).setOnClickListener(this.onDelListClickListener);

//        convertView.setOnClickListener(this.onClickListener);
//
        tvName.setText(name);
        tvTimeInit.setText(timeInit);
        tvTimeFinal.setText(timeFinal);

        return convertView;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    // Clicking on delete button
    public void setOnDelListClickListener(final View.OnClickListener onClickerListener){
        this.onDelListClickListener = onClickerListener;
    }

    public static TextView getTvName() {
        return tvName;
    }

    public static void setTvName(TextView tvName) {
        WaitListAdapter.tvName = tvName;
    }

    public static TextView getTvTimeInit() {
        return tvTimeInit;
    }

    public static void setTvTimeInit(TextView tvTimeInit) {
        WaitListAdapter.tvTimeInit = tvTimeInit;
    }

    public static TextView getTvTimeFinal() {
        return tvTimeFinal;
    }

    public static void setTvTimeFinal(TextView tvTimeFinal) {
        WaitListAdapter.tvTimeFinal = tvTimeFinal;
    }

    public static int getmPosition() {
        return mPosition;
    }
}
