package com.example.mytodoapp.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytodoapp.R;
import com.example.mytodoapp.pojo.ToDoTag;

import java.util.List;

public class TagDisplayAdapter extends RecyclerView.Adapter<TagDisplayAdapter.ViewHolder> {

    private Activity mContext;
    public List<ToDoTag> tagList;


    public TagDisplayAdapter(Activity context, List<ToDoTag> tagList) {

        this.mContext = context;
        this.tagList = tagList;
    }

    @NonNull
    @Override
    public TagDisplayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( mContext ).inflate( R.layout.tag_display_single_item, parent, false );
        return new TagDisplayAdapter.ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull TagDisplayAdapter.ViewHolder holder, int position) {

        ToDoTag tagItem = tagList.get( position );
        holder.tvTagItem.setText(tagItem.getLabel());
        holder.tvTagItem.setBackgroundResource( R.drawable.shape_tag_indicator );
        GradientDrawable gd = (GradientDrawable) holder.tvTagItem.getBackground().getCurrent();
        gd.setColor( Color.parseColor(tagItem.getColor()));

    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvTagItem;
        AlertDialog.Builder builder;

        public ViewHolder(@NonNull final View itemView) {
            super( itemView );
            tvTagItem = itemView.findViewById( R.id.tv_tag_display_item);
        }
    }


}
