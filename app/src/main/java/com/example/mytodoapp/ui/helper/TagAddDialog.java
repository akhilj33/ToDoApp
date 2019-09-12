package com.example.mytodoapp.ui.helper;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytodoapp.R;
import com.example.mytodoapp.TagListener;
import com.example.mytodoapp.adapter.ColourPickerAdapter;
import com.example.mytodoapp.db.Database.FireStoreDbHelper;
import com.example.mytodoapp.db.Database.TagViewModel;
import com.example.mytodoapp.pojo.ToDoTag;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class TagAddDialog extends DialogFragment {

    private EditText mEtTagName;
    private List<ToDoTag> allTaglist;
    private String[] mcolourList = {"#f28b82", "#fbbc04", "#FFF475", "#ccff90", "#a7ffeb", "#aecbfa", "#fdcfe8", "#e6c9a8", "#e8eaed"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate( R.layout.tag_add_dialog, container );
        mEtTagName = view.findViewById( R.id.et_tag_name );
        Button btnAddTask = view.findViewById( R.id.btn_tag_add );
        Button btnCancel = view.findViewById( R.id.btn_tag_cancel );
        final ToDoTag tagList = new ToDoTag( );
        RecyclerView mRecyclerViewColour = view.findViewById( R.id.recycler_view_colour );
        allTaglist = new ArrayList<>();
        FireStoreDbHelper.retrieveTags( new TagListener() {
            @Override
            public void onSuccessClicked(Object object) {
                allTaglist = (List<ToDoTag>) object;
            }

            @Override
            public void onFailureClicked(Object object) {

            }
        } );

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( view.getContext(), LinearLayoutManager.HORIZONTAL, true );
        mRecyclerViewColour.setLayoutManager( layoutManager );

        ColourPickerAdapter colourAdapter = new ColourPickerAdapter( getActivity(), mcolourList );
        mRecyclerViewColour.setAdapter( colourAdapter );

        btnAddTask.setOnClickListener( new View.OnClickListener() {
            int flag=0;
            @Override
            public void onClick(View view) {
                for(int i =0; i<allTaglist.size();i++){
                    if(allTaglist.get(i).getLabel().equalsIgnoreCase(mEtTagName.getText().toString())){
                        Toast.makeText( getActivity(), "Tag " +mEtTagName.getText().toString() + " already exist", Toast.LENGTH_LONG ).show();
                        flag=1;
                    }
                }
                if(flag!=1){
                    tagList.setLabel(mEtTagName.getText().toString());
                    tagList.setColor( mcolourList[Utility.colour_int] );
                    tagList.setId(new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date()));
                    if(mEtTagName.getText().toString().equals( "" ) || tagList.getColor().equals( "" ))
                    {
                        Toast.makeText( getActivity(), "Fill All Fields", Toast.LENGTH_LONG ).show();
                    }
                    else{
                        new FireStoreDbHelper().addTag( tagList );
                        getDialog().dismiss();
                    }
                }
            }
        }  );

        btnCancel.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        } );

        return view;
    }



}
