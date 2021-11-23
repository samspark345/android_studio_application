package com.example.byblosmobile;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ServiceList extends ArrayAdapter<Service> {
    private Activity context;
    List<Service> services;

    public ServiceList(Activity context, List<Service> service) {
        super(context, R.layout.layout_service_list, service);

        this.context = context;
        this.services = service;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_service_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.date);
        TextView textViewRate = (TextView) listViewItem.findViewById(R.id.openTime);
        TextView textViewInfo = (TextView) listViewItem.findViewById(R.id.closeTime);


        Service service = services.get(position);

        textViewName.setText(service.getName());
        textViewRate.setText(service.getRate());
        textViewInfo.setText(service.getRequiredInfo());

        return listViewItem;
    }
}