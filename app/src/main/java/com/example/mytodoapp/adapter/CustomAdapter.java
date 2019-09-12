package com.example.mytodoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mytodoapp.pojo.ListItems;
import com.example.mytodoapp.R;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private Context mContext;
    private List<ListItems> mylist;

    public CustomAdapter(Context context, List<ListItems> mylist) {
        this.mContext = context;
        this.mylist = mylist;
    }

    @Override
    public int getCount() {
        return mylist.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (view == null) {
            view = LayoutInflater.from( mContext ).inflate( R.layout.row_item_todo_list, null );
            holder = new ViewHolder();
            holder.cb = view.findViewById( R.id.checkbox );
            holder.tv = view.findViewById( R.id.tv_todo_item );
//            holder.icon = view.findViewById( R.id.iv_arrow );
            view.setTag( holder );
        } else {
            holder = (ViewHolder) view.getTag();
        }

//        holder.icon.setImageResource( R.drawable.ic_triangle_24 );

        //Setting data in view
        holder.tv.setText( mylist.get( position ).getTodoitems() );
        return view;
    }

    static class ViewHolder {
        private TextView tv;
        private CheckBox cb;
        private ImageView icon;
    }
}
