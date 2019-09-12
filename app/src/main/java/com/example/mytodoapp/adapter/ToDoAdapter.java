package com.example.mytodoapp.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytodoapp.MyActionListener;
import com.example.mytodoapp.R;
import com.example.mytodoapp.db.Database.FireStoreDbHelper;
import com.example.mytodoapp.pojo.ListItems;
import com.example.mytodoapp.pojo.ToDoListItem;
import com.example.mytodoapp.ui.activity.MainActivity;
import com.example.mytodoapp.ui.helper.Constants;
import com.example.mytodoapp.ui.helper.DialogDisplayHelper;
import com.example.mytodoapp.ui.helper.DialogFragmentHelper;

import java.util.ArrayList;
import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {
    public List<ToDoListItem> myList;
    private FragmentActivity mContext;
    private final SparseBooleanArray array=new SparseBooleanArray();

    public ToDoAdapter(FragmentActivity context, List<ToDoListItem> toDoListItemList) {
        this.mContext = context;
        this.myList = toDoListItemList;
    }

    public void setMyList(List<ToDoListItem> myList) {
        this.myList.clear();
        this.myList = myList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType( position );
    }

    @NonNull
    @Override
    public ToDoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from( mContext ).inflate( R.layout.row_item_todo_list, parent, false );

        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull final ToDoAdapter.ViewHolder holder, int position) {


        final ToDoListItem listItem = myList.get( position );

        holder.itemView.setTag( listItem );
        holder.tvToDoItem.setText( listItem.getTitle() );
        String dateTime = listItem.getDateTime();
        holder.tvDate.setText(listItem.getDateTime());

//        holder.itemCheckBox.setTag( position );
//
//        if (listItem.getSelected()) {
//            holder.itemCheckBox.setChecked(true);
//        } else {
//            holder.itemCheckBox.setChecked(false);
//        }
//
//        holder.itemCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked)
//                {
//                    Log.d("xyz", "unchecked");
//                }
//                else{
//                    Log.d("xyz", "checked");
//                }
//                notifyDataSetChanged();
//            }
//        });


        if (listItem.getDone()){
            holder.itemCheckBox.setChecked( true );
            holder.tvToDoItem.setPaintFlags( holder.tvToDoItem.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.itemCheckBox.setClickable( false );

//            listItem.setDone( true );
        }else{
            //no strike off
            //checkbox not hekce

            holder.itemCheckBox.setChecked( false );
//            holder.itemCheckBox.setBackgroundResource( R.drawable.ic_checkbox_unchecked_black_24dp );
            holder.itemCheckBox.setPaintFlags (holder.itemCheckBox.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        if (!TextUtils.isEmpty( dateTime )) {
            holder.tvDate.setText( listItem.getDateTime().substring( 0, 11 ) );
            holder.tvTime.setText( listItem.getDateTime().substring( 12 ) );
        } else {
            holder.tvDate.setText( "NO Date" );
            holder.tvTime.setText( "NO Time" );
        }
        holder.itemView.setId( position );

        holder.priorityIndicator.setBackgroundResource( R.drawable.shape_priority_indicator );
        GradientDrawable gd = (GradientDrawable) holder.priorityIndicator.getBackground().getCurrent();
        gd.setColor( Color.parseColor(listItem.getPriority().get( 0 ).getColor()));


    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvToDoItem, tvDate, tvTime;
        CheckBox itemCheckBox;
        View priorityIndicator;
        android.app.AlertDialog.Builder builder;

        public ViewHolder(@NonNull final View itemView) {
            super( itemView );

//            final ToDoListItem listItem = new ToDoListItem();
            tvToDoItem = itemView.findViewById( R.id.tv_todo_item );
            itemCheckBox = itemView.findViewById( R.id.checkbox );
            tvDate = itemView.findViewById( R.id.tv_date );
            tvTime = itemView.findViewById( R.id.tv_time );
            priorityIndicator = itemView.findViewById( R.id.view_indicator);
            builder = new android.app.AlertDialog.Builder(itemView.getContext());


            itemCheckBox.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!(myList.get( getAdapterPosition() ).getDone())) {
                        builder.setMessage( "Is this Task Completed?" )
                                .setCancelable( false )
                                .setPositiveButton( "Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        myList.get( getAdapterPosition() ).setDone( true );
                                        FireStoreDbHelper.updateItem(  myList.get( getAdapterPosition() ) , new MyActionListener() {
                                            @Override
                                            public void onSuccessClicked(Object object) {
                                                Toast.makeText( mContext, (String)object, Toast.LENGTH_SHORT ).show();
                                            }

                                            @Override
                                            public void onFailureClicked(Object object) {
                                                Toast.makeText( mContext, (String)object, Toast.LENGTH_SHORT ).show();
                                            }
                                        } );
//                                        FireStoreDbHelper.updateItem( myList.get( getAdapterPosition() ) );
                                        notifyItemChanged( getAdapterPosition() );
                                        notifyDataSetChanged();
                                    }
                                } )
                                .setNegativeButton( "No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        notifyItemChanged( getAdapterPosition() );
                                        dialog.cancel();
                                    }
                                } );
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                    else{
                        //Already clicked
                    }
                }
            } );

            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(final View view) {

                    FragmentTransaction ft1 = mContext.getSupportFragmentManager().beginTransaction();
//                    Fragment prev = mContext.getFragmentManager().findFragmentByTag("dialog");
//                    if (prev != null) {
//                        ft1.remove(prev);
//                    }
                    ft1.addToBackStack( null );
                    ToDoListItem listItem = myList.get( itemView.getId() );
                    DialogFragment dialogfragment = new DialogDisplayHelper();
                    Bundle bundle = new Bundle();
                    bundle.putString( Constants.KEY_ITEM_TITLE, listItem.getTitle() );
                    bundle.putString( Constants.KEY_ITEM_DESCRIPTION, listItem.getDescription() );
                    bundle.putString( Constants.KEY_ITEM_DATETIME, listItem.getDateTime() );
                    bundle.putInt(Constants.KEY_ITEM_ID, listItem.getItemId() );
                    bundle.putSerializable( Constants.KEY_ITEM_TAG, new ArrayList<>(listItem.getTag()));
                    bundle.putSerializable(  Constants.KEY_ITEM_PRIORITY,  new ArrayList<>(listItem.getPriority()));
                    dialogfragment.setArguments( bundle );
                    dialogfragment.setCancelable( false );
                    ft1.add(dialogfragment, "display_dialog").commit();
                    mContext.getSupportFragmentManager().executePendingTransactions();
                }
            } );


        }
    }


}
