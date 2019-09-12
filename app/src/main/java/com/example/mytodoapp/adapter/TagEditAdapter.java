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

public class TagEditAdapter extends RecyclerView.Adapter<TagEditAdapter.ViewHolder> {

    private Activity mContext;
    public List<ToDoTag> tagList;


    public TagEditAdapter(Activity context, List<ToDoTag> tagList) {

        this.mContext = context;
        this.tagList = tagList;
    }

    @NonNull
    @Override
    public TagEditAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( mContext ).inflate( R.layout.tag_indicator, parent, false );
        return new TagEditAdapter.ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull TagEditAdapter.ViewHolder holder, int position) {

        ToDoTag tagItem = tagList.get( position );
        holder.tvTagItem.setText(tagItem.getLabel());
//        holder.tvTagItem.setTag( position );
//        holder.tvTagItem.setBackgroundColor( Color.parseColor(tagItem.getColor()));
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

            tvTagItem = itemView.findViewById( R.id.tv_tag_item);
            builder = new AlertDialog.Builder(itemView.getContext());
//            final int pos = (int)tvTagItem.getTag();

            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    builder.setMessage("Do you want to Remove this tag?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                  tagList.remove(getAdapterPosition());
                                    notifyDataSetChanged();
                                    Toast.makeText(view.getContext(),"Tag Removed",
                                            Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            } );
        }
    }


}
