package com.example.byblosmobile;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.sql.Time;
import java.util.List;

public class AvailabilityList extends ArrayAdapter<TimeSlot> {
    private Activity context;
    List<TimeSlot> timeSlots;

    public AvailabilityList(Activity context, List<TimeSlot> timeSlots) {
        super(context, R.layout.availability_list, timeSlots);

        this.context = context;
        this.timeSlots = timeSlots;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.availability_list, null, true);

        TextView date= (TextView) listViewItem.findViewById(R.id.date);
        TextView openTime= (TextView) listViewItem.findViewById(R.id.openTime);
        TextView closeTime = (TextView) listViewItem.findViewById(R.id.closeTime);


        TimeSlot timeSlot = timeSlots.get(position);

        date.setText(timeSlot.getDay());
        openTime.setText(Integer.toString(timeSlot.getStartHour()));
        closeTime.setText(Integer.toString(timeSlot.getEndHour()));

        return listViewItem;
    }
}