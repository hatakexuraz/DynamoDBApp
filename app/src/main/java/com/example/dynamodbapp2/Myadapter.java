package com.example.dynamodbapp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Myadapter extends BaseAdapter {

    Context context;

    public Myadapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return GenerateKeys.keysholder.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        viewHolder viewHolder = null;
        if (row==null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.singlerow, parent, false);
            viewHolder = new viewHolder(row);
            row.setTag(viewHolder);
        }else {
            viewHolder = (Myadapter.viewHolder) row.getTag();
        }

        viewHolder.textView.setText(GenerateKeys.keysholder.get(position).get("key").toString());
        return null;
    }

    private class viewHolder{
        TextView textView;
        public viewHolder(View view){
            textView = view.findViewById(R.id.singletextview);
        }
    }
}
