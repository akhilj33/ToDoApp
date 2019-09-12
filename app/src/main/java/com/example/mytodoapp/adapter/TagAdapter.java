package com.example.mytodoapp.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytodoapp.R;
import com.example.mytodoapp.db.Database.FireStoreDbHelper;
import com.example.mytodoapp.pojo.ToDoTag;

import java.util.List;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> {

    private Activity mContext;
    private List<ToDoTag> tagList;
    private ToDoTag tagItem;


    public TagAdapter(Activity context, List<ToDoTag> tagList) {

        this.mContext = context;
        this.tagList = tagList;
    }

    @NonNull
    @Override
    public TagAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( mContext ).inflate( R.layout.tag_indicator, parent, false );
        return new TagAdapter.ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull TagAdapter.ViewHolder holder, int position) {

        tagItem = tagList.get( position );
        holder.tvTagItem.setTag(position);
        holder.tvTagItem.setText(tagItem.getLabel());
//        holder.tvTagItem.setBackgroundColor( Color.parseColor(tagItem.getColor()));
        holder.tvTagItem.setBackgroundResource( R.drawable.shape_tag_indicator );
        GradientDrawable gd = (GradientDrawable) holder.tvTagItem.getBackground().getCurrent();
        gd.setColor(Color.parseColor(tagItem.getColor()));

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

            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    int position = (int) tvTagItem.getTag();
                    final ToDoTag toDoTag = tagList.get( position );

                    if(toDoTag.isUserCreated()) {
                        builder.setMessage( "Do you want to Delete this tag?" )
                                .setCancelable( false )
                                .setPositiveButton( "Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        FireStoreDbHelper.deleteTag( toDoTag );
                                        notifyDataSetChanged();
                                        Toast.makeText( view.getContext(), "TAG deleted",
                                                Toast.LENGTH_SHORT ).show();
                                    }
                                } )
                                .setNegativeButton( "No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                } );
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                    else{

                        Toast.makeText( mContext, "Sorry!!This Tag cannot be deleted", Toast.LENGTH_LONG ).show();
                    }
                }
            } );
        }
    }

}
