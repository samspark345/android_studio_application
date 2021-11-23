package com.example.byblosmobile;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class RequestList extends ArrayAdapter<Request> {
    private Activity context;
    List<Request> requests;

    public RequestList(Activity context, List<Request> request) {
        super(context, R.layout.request_list, request);

        this.context = context;
        this.requests = request;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.request_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.date);
        TextView textViewRate = (TextView) listViewItem.findViewById(R.id.openTime);
        TextView textViewInfo = (TextView) listViewItem.findViewById(R.id.closeTime);


        Request request = requests.get(position);

        textViewName.setText(request.getServiceName());
        textViewRate.setText(request.getCustomerName());
        textViewInfo.setText(request.getStatus());

        return listViewItem;
    }
}
