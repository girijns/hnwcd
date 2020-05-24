package org.girijns.hnwcd;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class DeviceArrayAdapter<String> extends ArrayAdapter {
    private final Context context;
    private final List<String> values;
    public DeviceArrayAdapter(Context context, int resource, List<String> values) {
        super(context, resource, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = super.getView(position, convertView, parent);
        TextView textView=(TextView) row.findViewById(android.R.id.text1);
        textView.setTextColor(context.getResources().getColor(R.color.text_color));
        if(getItem(position).toString().contains("Ethernet LAN")) {
            row.setBackgroundColor (context.getResources().getColor(R.color.header_bg_color));
        } else {
            row.setBackgroundColor(context.getResources().getColor(R.color.row_bg_color));
        }
        return row;
    }
}
