package com.tfml.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tfml.R;
import com.tfml.model.stateResponseModel.StateResponseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by webwerks on 25/8/16.
 */

public class StateAdapter extends ArrayAdapter<StateResponseModel>{
    Context context;
    public StateAdapter(Context context, List<StateResponseModel>data) {
        super(context, 0,data);
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            row = LayoutInflater.from(context).inflate(R.layout.simple_state_spinner, parent, false);
        }
        TextView stateName = (TextView) row.findViewById(R.id.txtState);
        stateName.setText(getItem(position).getName());
        return row;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position,convertView,parent);
    }
}