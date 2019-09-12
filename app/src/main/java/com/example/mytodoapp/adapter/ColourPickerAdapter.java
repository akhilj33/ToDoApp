package com.example.mytodoapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.example.mytodoapp.R;
import com.example.mytodoapp.ui.helper.TagAddDialog;
import com.example.mytodoapp.ui.helper.Utility;

import java.util.List;

public class ColourPickerAdapter extends RecyclerView.Adapter<ColourPickerAdapter.ViewHolder> {

    private Context mContext;
    private String colourlist[];
    private  boolean colourStateArray[] = {false, false, false, false, false, false, false, false, false};
//    private SparseBooleanArray colourStateArray= new SparseBooleanArray();

    public ColourPickerAdapter(Context mContext, String[] colourlist) {
        this.mContext = mContext;
        this.colourlist = colourlist;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType( position );
    }

    @NonNull
    @Override
    public ColourPickerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from( mContext ).inflate( R.layout.single_colour_layout, parent, false );

        return new ColourPickerAdapter.ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ColourPickerAdapter.ViewHolder holder, int position) {

        if(!colourStateArray[position]) {
            holder.colourCheckBox.setBackgroundResource( R.drawable.shape_colour_picker );
            GradientDrawable gd = (GradientDrawable) holder.colourCheckBox.getBackground().getCurrent();
            gd.setColor( Color.parseColor( colourlist[position] ) );
        }
        else{
            holder.colourCheckBox.setBackgroundResource( R.drawable.shape_colour_tick);
//            GradientDrawable gd = (GradientDrawable) holder.colourCheckBox.getBackground().getCurrent();
//            gd.setColor( Color.parseColor( colourlist[position] ) );
        }
    }


    @Override
    public int getItemCount() {
        return colourlist.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox colourCheckBox;

        public ViewHolder(@NonNull final View itemView) {
            super( itemView );
            colourCheckBox = itemView.findViewById( R.id.cb_colourpicker );

            colourCheckBox.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utility.colour_int=getAdapterPosition();
                    for(int i = 0; i< colourlist.length;i++) {
                        if(i==getAdapterPosition()){
                            colourStateArray[getAdapterPosition()]=true;
                            colourCheckBox.setBackgroundResource( R.drawable.shape_colour_tick);
                            notifyItemChanged( getAdapterPosition() );
                        }
                        else {
                            colourStateArray[i]=false;
                            notifyItemChanged( i );
                        }
                    }
                    notifyDataSetChanged();

                }
            } );

        }
    }
}
