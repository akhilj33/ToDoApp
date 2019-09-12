package com.example.mytodoapp.ui.helper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.mytodoapp.R;
import com.example.mytodoapp.adapter.TagAdapter;
import com.example.mytodoapp.db.Database.FireStoreDbHelper;
import com.example.mytodoapp.db.Database.TagViewModel;
import com.example.mytodoapp.pojo.ToDoTag;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

public class TagDisplayDialog extends DialogFragment {

    private RecyclerView mRecyclerView;
    private TagAdapter tagAdapter;
    private List<ToDoTag> mTagList = new ArrayList<>( );
    private ImageView mImageView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        final ToDoTag[] toDoTags = {new ToDoTag()};
        View view = inflater.inflate( R.layout.tag_display_dialog, container );
        TextView tvTagTitle = view.findViewById( R.id.tv_tag_title );
        Button btnAddTask = view.findViewById( R.id.btn_tag_add );
        Button btnCancel = view.findViewById( R.id.btn_tag_cancel );
        mRecyclerView = view.findViewById( R.id.tags_recycler_view );
//        mImageView = view.findViewById(R.id.placeholder_failed_image);
//        mImageView.setImageResource(.id.placeholder_failed_image);

//        prepare_list();
//
//        for (ToDoTag item: mTagList)
//        {
//            FireStoreDbHelper.addTag( item );
//        }

        TagViewModel model = ViewModelProviders.of( this ).get( TagViewModel.class );
        model.retrieveTags().observe( this, new Observer<List<ToDoTag>>() {
            @Override
            public void onChanged(List<ToDoTag> toDoTagList) {
                mTagList = toDoTagList;
                if(mTagList.size()>0) {
                    loadDataIntoAdapter();
//                    TODO add placeholder
                }
                else{
//                        TODO add placeholder
                }
            }
        } );

//        FireStoreDbHelper.retrieveTags();

//        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(),2
//        );
//        mRecyclerView.setLayoutManager(gridLayoutManager);

        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL
        );
        mRecyclerView.setLayoutManager(gridLayoutManager);




        btnCancel.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        } );

        btnAddTask.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                androidx.fragment.app.FragmentTransaction ft1 = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("display_tag_dialog");
                if (prev != null) {
                    ft1.remove(prev);
                }
                ft1.addToBackStack( null );

                DialogFragment dialogfragment = new TagAddDialog();
                dialogfragment.setCancelable( false );
                ft1.add(dialogfragment, "add_tag_dialog").commit();
                getActivity().getSupportFragmentManager().executePendingTransactions();
            }
        } );
        return view;
    }

    public void prepare_list(){
        mTagList.add( new ToDoTag( "1", "Work","#c1c1c1", false) );
        mTagList.add( new ToDoTag( "2", "Home","#cbf0f8", false) );
        mTagList.add( new ToDoTag( "3", "Personal","#d7aefb", false) );
        mTagList.add( new ToDoTag( "4", "Market","#fff475", false) );
    }

    private void loadDataIntoAdapter() {

        tagAdapter = new TagAdapter( getActivity(), mTagList );
        mRecyclerView.setAdapter( tagAdapter );
    }

}
