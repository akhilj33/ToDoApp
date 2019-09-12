package com.example.mytodoapp.ui.helper;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytodoapp.R;
import com.example.mytodoapp.adapter.TagDisplayAdapter;
import com.example.mytodoapp.db.Database.FireStoreDbHelper;
import com.example.mytodoapp.pojo.ToDoListItem;
import com.example.mytodoapp.pojo.ToDoPriority;
import com.example.mytodoapp.pojo.ToDoTag;

import java.util.ArrayList;
import java.util.List;

public class DialogDisplayHelper extends DialogFragment {
    private List<ToDoPriority> mPriorityList;
    private ToDoListItem mItemList;
    private List<ToDoTag> mTagList;
    private TagDisplayAdapter mTagDisplayAdapter;
    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.dialog_display_layout, container );

        TextView mTvTitle = view.findViewById( R.id.tv_title );
        TextView mTvDescription = view.findViewById( R.id.tv_description );
        TextView mTvPriority = view.findViewById( R.id.tv_priority );
        TextView mTvTag = view.findViewById( R.id.tv_tag );
        TextView mTvDatenTime = view.findViewById( R.id.tv_datentime );
        Button mBtnEdit = view.findViewById( R.id.btn_dialog_display_edit );
        Button mBtnCancel = view.findViewById( R.id.btn_dialog_display_cancel );
        Button mBtnDelete = view.findViewById( R.id.btndeletetask );
        mRecyclerView = view.findViewById( R.id.tags_display_recyclerview );
        mItemList = new ToDoListItem();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(),2
        );
        mRecyclerView.setLayoutManager(gridLayoutManager);

        final Bundle bundle = getArguments();
        final String title = bundle.getString( Constants.KEY_ITEM_TITLE,"Default" );
        final String description = bundle.getString( Constants.KEY_ITEM_DESCRIPTION,"Default" );
        final String datetime = bundle.getString( Constants.KEY_ITEM_DATETIME,"Default" );
        final int id = bundle.getInt(Constants.KEY_ITEM_ID);

        mTagList =(ArrayList<ToDoTag>)bundle.getSerializable( Constants.KEY_ITEM_TAG);
        mTagDisplayAdapter = new TagDisplayAdapter( getActivity(), mTagList );
        mRecyclerView.setAdapter( mTagDisplayAdapter );

        mPriorityList =(ArrayList<ToDoPriority>)bundle.getSerializable( Constants.KEY_ITEM_PRIORITY);
        mTvPriority.setText( mPriorityList.get( 0 ).getLabel());
        mTvPriority.setBackgroundResource( R.drawable.shape_tag_indicator );
        GradientDrawable gd = (GradientDrawable) mTvPriority.getBackground().getCurrent();
        gd.setColor( Color.parseColor( mPriorityList.get( 0 ).getColor()));

        mItemList.setItemId(id);
        mItemList.setTitle(title);
        mItemList.setDescription(description);

        mTvTitle.setText( title);
        mTvDescription.setText( description );
        mTvDatenTime.setText( datetime );


        mBtnEdit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                androidx.fragment.app.FragmentTransaction ft1 = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("display_dialog");
                if (prev != null) {
                    ft1.remove(prev);
                }
                ft1.addToBackStack( null );
                DialogFragment dialogfragment = new DialogFragmentHelper();
                ((DialogFragmentHelper) dialogfragment).setIsNew( false );

                Bundle bundle = new Bundle( );
                bundle.putString( Constants.KEY_ITEM_TITLE, title);
                bundle.putString( Constants.KEY_ITEM_DESCRIPTION, description);
                bundle.putString( Constants.KEY_ITEM_DATETIME, datetime);
                bundle.putSerializable( Constants.KEY_ITEM_TAG,  new ArrayList<>(mTagList));
                bundle.putSerializable(  Constants.KEY_ITEM_PRIORITY,  new ArrayList<>( mPriorityList ));
                bundle.putInt(Constants.KEY_ITEM_ID,  id );

                dialogfragment.setArguments( bundle );
                dialogfragment.setCancelable( false );
                ft1.add(dialogfragment, "edit_dialog").commit();
                getActivity().getSupportFragmentManager().executePendingTransactions();
            }
        } );



        mBtnCancel.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        } );
        mBtnDelete.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FireStoreDbHelper.deleteItem(mItemList);
                getDialog().dismiss();
            }
        } );



        return view;
    }
}
