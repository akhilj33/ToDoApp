package com.example.mytodoapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mytodoapp.MyActionListener;
import com.example.mytodoapp.R;
import com.example.mytodoapp.adapter.ToDoAdapter;
import com.example.mytodoapp.db.Database.DataBaseViewModel;
import com.example.mytodoapp.db.Database.FireStoreDbHelper;
import com.example.mytodoapp.pojo.ToDoListItem;
import com.facebook.shimmer.ShimmerFrameLayout;


import java.util.ArrayList;
import java.util.List;

public class TasksFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<ToDoListItem> mylist;
    private List<ToDoListItem> testList = new ArrayList<>();
    private ToDoListItem listItem = new ToDoListItem();
    private ShimmerFrameLayout mShimmerViewContainer;
    private SwipeRefreshLayout mPullToRefresh;
    private ImageView mPlaceHoderImage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_tasks, container, false );

        recyclerView = view.findViewById( R.id.main_recycler );
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_items);
        recyclerView.setHasFixedSize( true );
        mPlaceHoderImage = view.findViewById(R.id.placeholder_image);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( view.getContext() );
        recyclerView.setLayoutManager( layoutManager );
        mPullToRefresh = view.findViewById(R.id.pullToRefresh);

        mylist = new ArrayList<>();

            refreshData();
            mPullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    refreshData();
                    mPullToRefresh.setRefreshing(false);
                }
            });

        return view;
    }

    private void loadDataIntoAdapter() {


        if (mylist.size() > 0) {
            Toast.makeText( getActivity(), "Database Loaded", Toast.LENGTH_SHORT ).show();
        } else {
            Toast.makeText( getActivity(), "Database not Loaded", Toast.LENGTH_SHORT ).show();
        }
        RecyclerView.Adapter myAdapter = new ToDoAdapter( getActivity(), mylist );
        recyclerView.setAdapter( myAdapter );
    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmer();
//        mShimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmer();
//        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }

    private void refreshData(){
        DataBaseViewModel model = ViewModelProviders.of( this ).get( DataBaseViewModel.class );
        model.getAllItems().observe( this, new Observer<List<ToDoListItem>>() {
            @Override
            public void onChanged(List<ToDoListItem> toDoListItems) {
                mylist = toDoListItems;
                if(mylist.size()>0) {
                    loadDataIntoAdapter();
                    mShimmerViewContainer.stopShimmer();
                    mShimmerViewContainer.setVisibility( View.GONE );
                    mPlaceHoderImage.setVisibility( View.GONE );
                }
                else{
                    mShimmerViewContainer.stopShimmer();
                    mShimmerViewContainer.setVisibility( View.GONE );

                }
            }
        } );

    }


}


