package com.example.lineupapp.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.lineupapp.R;
import com.example.lineupapp.models.WaitListee;

import java.util.List;

public class WaitListeeAdapter extends ArrayAdapter<WaitListee> {

    private Context mContext;
    private static TextView tvName, tvTime;
    int mResource;
    private View.OnClickListener onDelClickListener;

    public WaitListeeAdapter(Context context, int resource, List<WaitListee> waitlistees){
        super(context, resource, waitlistees);
        mContext = context;
        mResource = resource;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent){
        String name = getItem(position).getName();
        String timeNeeded = getItem(position).getTimeNeeded();

        WaitListee waitlistee = new WaitListee(name, timeNeeded);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        tvName = convertView.findViewById(R.id.textView3);
        tvTime = convertView.findViewById(R.id.textView4);

        convertView.findViewById(R.id.delBtn).setOnClickListener(this.onDelClickListener);
        tvName.setText(name);
        tvTime.setText(timeNeeded);

        return convertView;
    }

    public void setOnDelClickListener(final View.OnClickListener onClickerListener){
        this.onDelClickListener = onClickerListener;
    }

    public static TextView getTvName() {
        return tvName;
    }

    public static void setTvName(TextView tvName) {
        WaitListeeAdapter.tvName = tvName;
    }

    public static TextView getTvTime() {
        return tvTime;
    }

    public static void setTvTime(TextView tvTime) {
        WaitListeeAdapter.tvTime = tvTime;
    }
}
