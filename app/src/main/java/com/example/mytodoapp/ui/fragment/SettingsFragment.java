package com.example.mytodoapp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mytodoapp.R;
import com.example.mytodoapp.pojo.ToDoListItem;
import com.example.mytodoapp.ui.activity.Login;
import com.example.mytodoapp.ui.activity.MainActivity;
import com.example.mytodoapp.ui.helper.TagDisplayDialog;
import com.example.mytodoapp.ui.helper.Utility;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends Fragment {

    private List<ToDoListItem> mToDoList = new ArrayList<>(  );
    private TextView signout;
    private Switch mSwitchDate, mSwitchPriority, mSwitchTag;
//    private GoogleApiClient mGoogleApiClient;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_settings, container, false );
        Toolbar toolbar = view.findViewById( R.id.toolbar );
        TextView mManageTags = view.findViewById( R.id.label_manage_tags );
        TextView mSortByDate = view.findViewById( R.id.label_by_date );
        TextView mSortByPriority = view.findViewById( R.id.label_by_priority );
        TextView mSortByTags = view.findViewById( R.id.label_by_tags );
        mSwitchDate = view.findViewById(R.id.switchdate);
        mSwitchPriority= view.findViewById(R.id.switchpriority);
        mSwitchTag= view.findViewById(R.id.switchtag);
        signout = view.findViewById( R.id.tv_logout);

        if(Utility.SWITCH_DATE) {
            mSwitchDate.setChecked( true );
        }
        else if(Utility.SWITCH_PRIORITY){
                mSwitchPriority.setChecked( true );
        }
        else if(Utility.SWITCH_TAG){
                mSwitchTag.setChecked( true );
        }

        mManageTags.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft1 = getActivity().getSupportFragmentManager().beginTransaction();
                ft1.addToBackStack( null );

                DialogFragment dialogfragment = new TagDisplayDialog();
                dialogfragment.setCancelable( false );
                ft1.add(dialogfragment, "display_tag_dialog").commit();
                getActivity().getSupportFragmentManager().executePendingTransactions();
            }
        } );

        mSwitchDate.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Utility.SWITCH_DATE = true;
                    Utility.SWITCH_PRIORITY =false;
                    Utility.SWITCH_TAG = false;
                    mSwitchPriority.setChecked( false );
                    mSwitchTag.setChecked( false );
                    mSwitchPriority.setClickable(false);
                    mSwitchTag.setClickable( false );
                } else {
                    Utility.SWITCH_DATE = false;
                }
            }
        } );


        mSwitchPriority.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Utility.SWITCH_PRIORITY = true;
                    Utility.SWITCH_DATE = false;
                    Utility.SWITCH_TAG = false;
                    mSwitchDate.setChecked( false );
                    mSwitchDate.setClickable(false);
                    mSwitchTag.setChecked( false );
                    mSwitchTag.setClickable( false );
                } else {
                    Utility.SWITCH_PRIORITY = false;
                }
            }
        } );

        mSwitchTag.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Utility.SWITCH_TAG = true;
                    Utility.SWITCH_PRIORITY = false;
                    Utility.SWITCH_DATE = false;
                    mSwitchPriority.setChecked( false );
                    mSwitchDate.setClickable(false);
                    mSwitchDate.setClickable(false);
                    mSwitchPriority.setClickable(false);
                } else {
                    Utility.SWITCH_TAG = false;
                }
            }
        } );
        signout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getActivity(),"Logged Out",Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(getActivity(), Login.class);
                                startActivity(i);
                                getActivity().finish();
            }
        } );

        return view;
    }
}
