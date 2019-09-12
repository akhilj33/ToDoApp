package com.example.mytodoapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mytodoapp.R;
import com.example.mytodoapp.pojo.ToDoTag;
import com.example.mytodoapp.TagListener;

import java.util.List;

public class TagSpinnerAdapter extends BaseAdapter {

    private Context mContext;
    private List<ToDoTag> tagList;
    private TagListener mSpinnerItemClicked;

    public TagSpinnerAdapter(Context mContext, List<ToDoTag> tagList, TagListener spinnerItemClicked) {
        this.mContext = mContext;
        this.tagList = tagList;
        this.mSpinnerItemClicked = spinnerItemClicked;
    }

    @Override
    public int getCount() {
        return tagList.size();
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);
        TextView tv = (TextView) view;
        if (position == 0) {
            tv.setTextColor(Color.GRAY);
        } else {
            tv.setTextColor(Color.BLACK);
        }
        return view;
    }

    @Override
    public Object getItem(int i) {
        return tagList.get( i );
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

//        Log.d( "Pos", "int i is" +  i  );

        ToDoTag tagItem = tagList.get( i );

        if(view==null){
            view = LayoutInflater.from( mContext ).inflate(R.layout.spinner_single_item,null);
            holder = new ViewHolder();
            holder.mSpinnerList = (TextView) view.findViewById(R.id.spinner_item);
            view.setTag(holder);
        }
        else{
            holder = (TagSpinnerAdapter.ViewHolder) view.getTag();
        }
        holder.mSpinnerList.setText(tagItem.getLabel());
        if(tagItem.getLabel().equals("Select Tag")){
            holder.mSpinnerList.setTextColor( Color.GRAY);
        }
        return view;
    }

    static class ViewHolder {
        private TextView mSpinnerList;
    }

}
