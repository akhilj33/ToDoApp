package com.example.mytodoapp.ui.helper;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytodoapp.MyActionListener;
import com.example.mytodoapp.R;
import com.example.mytodoapp.adapter.TagEditAdapter;
import com.example.mytodoapp.adapter.TagSpinnerAdapter;
import com.example.mytodoapp.adapter.ToDoAdapter;
import com.example.mytodoapp.db.Database.FireStoreDbHelper;
import com.example.mytodoapp.db.Database.TagViewModel;
import com.example.mytodoapp.pojo.ToDoListItem;
import com.example.mytodoapp.pojo.ToDoPriority;
import com.example.mytodoapp.pojo.ToDoTag;
import com.example.mytodoapp.TagListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DialogFragmentHelper extends DialogFragment implements TagListener {

    private ToDoTag tagItem = new ToDoTag();
    private TagViewModel model;
    private List<ToDoTag> tagList = new ArrayList<>();
    private List<ToDoTag> mItemTagList = new ArrayList<>();
    private ToDoListItem listItem;
    private Button mBtnOk;
    private TextView mTvDateTime;
    private EditText mEtDescription, mEtTitle;
    private boolean mIsNew;
    private RadioGroup mRadioGroupPriority;
    private String mDateTime;
        private ToDoPriority mToDoPriorityItem;
    private List<ToDoPriority> mPriorityList;
    private RecyclerView mRecyclerView;
    private TagEditAdapter mTagEditAdapter;
    private ToDoAdapter toDoAdapter;
    int id;

    @Override
    public void onAttach(Context context) {
        super.onAttach( context );
        model = ViewModelProviders.of( this ).get( TagViewModel.class );
    }

    public void setIsNew(boolean isNew) {
        mIsNew = isNew;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate( R.layout.dialog_edit_layout, container );

        mToDoPriorityItem = new ToDoPriority();
        mPriorityList = new ArrayList<>();

        TextView mTvTitle = view.findViewById( R.id.tv_title );
        mRadioGroupPriority = view.findViewById( R.id.rg_priority );
        mEtTitle = view.findViewById( R.id.et_title );
        mEtDescription = view.findViewById( R.id.et_description );
        mBtnOk = view.findViewById( R.id.dialog_btn_add );
        mTvDateTime = view.findViewById( R.id.tv_dnt_display );
        Button mBtnCancel = view.findViewById( R.id.btncancel );
        mRecyclerView = view.findViewById( R.id.tags_edit_recyclerview );

//        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
//        mRecyclerView.setLayoutManager( gridLayoutManager );

        GridLayoutManager gridLayoutManager = new GridLayoutManager( view.getContext(), 2
        );
        mRecyclerView.setLayoutManager( gridLayoutManager );

        model.retrieveTags().observe( this, new Observer<List<ToDoTag>>() {
            @Override
            public void onChanged(List<ToDoTag> toDoTagList) {
                tagList = toDoTagList;
                tagList.add(0, new ToDoTag( "none", "Select Tag", "none",false ));
                initSpinner( view );
            }
        } );

        if (mIsNew) {
            mTvTitle.setText( R.string.label_dialog_title );
            mBtnOk.setText( R.string.label_btn_add_task );
        } else {

            Bundle bundle = getArguments();
            assert bundle != null;
            String title = bundle.getString( Constants.KEY_ITEM_TITLE, "Default" );
            String description = bundle.getString( Constants.KEY_ITEM_DESCRIPTION, "Default" );
            String datetime = bundle.getString( Constants.KEY_ITEM_DATETIME, "Default" );
            mPriorityList = (ArrayList<ToDoPriority>) bundle.getSerializable( Constants.KEY_ITEM_PRIORITY );
            id = bundle.getInt(Constants.KEY_ITEM_ID);

            SelectPriorityRadioButton();
            mItemTagList=(ArrayList<ToDoTag>)bundle.getSerializable( Constants.KEY_ITEM_TAG);
            loadDataIntoAdapter();
            mEtTitle.setText( title );
            mEtDescription.setText( description );
            mTvDateTime.setText( datetime );
            mTvTitle.setText( R.string.label_edit_task );
            mBtnOk.setText( R.string.label_btn_save );
        }

        mBtnOk.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOkTapped();
            }
        } );

        mBtnCancel.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        } );



        mTvDateTime.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDateTapped(view);
            }
        } );

        return view;
    }

    private void onDateTapped(View view){
        final Calendar c = Calendar.getInstance();
        int mYear = c.get( Calendar.YEAR ); // current year
        int mMonth = c.get( Calendar.MONTH ); // current month
        int mDay = c.get( Calendar.DAY_OF_MONTH ); // current day
        // date picker dialog
        // set day of month , month and year value in the edit text
        //                                c.set( year, monthOfYear, dayOfMonth );
        //                                Date date = c.getTime();
        //                                        mTvDateTime.setText( mDateTime );
        DatePickerDialog datePickerDialog = new DatePickerDialog( view.getContext(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        mDateTime = dayOfMonth + "-"
                                + (monthOfYear + 1) + "-" + year;

//                                c.set( year, monthOfYear, dayOfMonth );
//                                Date date = c.getTime();

                        TimePickerDialog timePickerDialog = new TimePickerDialog( view.getContext(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {

                                mDateTime = mDateTime + " " + hourOfDay + ":" + minutes;
//                                        mTvDateTime.setText( mDateTime );

                                SimpleDateFormat fmt = new SimpleDateFormat( "dd-MM-yyyy HH:mm" );
                                try {
                                    Date date = fmt.parse( mDateTime );
                                    SimpleDateFormat fmtOut = new SimpleDateFormat( "EEE, d MMM hh:mm a" );
                                    mTvDateTime.setText( fmtOut.format( date ) );
                                } catch (ParseException e) {
                                    mTvDateTime.setText( "Date" );
                                }
                            }
                        }, 0, 0, false );
                        timePickerDialog.show();
                    }
                }, mYear, mMonth, mDay );
        datePickerDialog.show();
    }

    private void onOkTapped() {
        listItem = new ToDoListItem();

        int priorityId = mRadioGroupPriority.getCheckedRadioButtonId();
        RadioButton mRadioButtonPriority = (RadioButton) mRadioGroupPriority.findViewById( priorityId );
        mToDoPriorityItem = Utility.checkPriorityList( mRadioButtonPriority.getText().toString(), mToDoPriorityItem );
        mPriorityList.clear();
        mPriorityList.add( mToDoPriorityItem );
        listItem.setPriority( mPriorityList );
        listItem.setTitle( mEtTitle.getText().toString() );
        listItem.setDescription( mEtDescription.getText().toString() );
        listItem.setDateTime( mTvDateTime.getText().toString() );


        if (mBtnOk.getText().toString().equalsIgnoreCase( "Add Task" )) {
            SimpleDateFormat sdf1 = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
            String currentTime = sdf1.format(new Date());
//            SimpleDateFormat sdf2 = new SimpleDateFormat("yyMMdd", Locale.getDefault());
//            String currentDate = sdf2.format(new Date());
            listItem.setItemId(Integer.parseInt( currentTime ));
            listItem.setCreatedDate( Utility.getCurrentFormattedDate() );
            listItem.setModifiedDate( Utility.getCurrentFormattedDate() );
            listItem.setTag( mItemTagList );


            if(mEtTitle.getText().toString().equals( "" )
                    || mEtDescription.getText().toString().equals( "" )
                    || mTvDateTime.getText().toString().equals( "" )
                    || mItemTagList.size()==0 || mPriorityList.size()==0)
            {
                Toast.makeText( getActivity(), "Fill All Fields", Toast.LENGTH_LONG ).show();
            }
            else{
                new FireStoreDbHelper().insertItem( listItem );
                Toast.makeText( getActivity(), "TASK ADDED", Toast.LENGTH_SHORT ).show();
                getDialog().dismiss();
            }
        } else if (mBtnOk.getText().toString().equalsIgnoreCase( "SAVE CHANGES" )) {
            listItem.setModifiedDate( Utility.getCurrentFormattedDate() );
            listItem.setTag( mItemTagList );
            listItem.setItemId( id );
            if(mEtTitle.getText().toString().isEmpty()
                    || mEtDescription.getText().toString().isEmpty()
                    || mTvDateTime.getText().toString().isEmpty()
                    || mItemTagList.size()==0 || mPriorityList.size()==0)
            {
                Toast.makeText( getActivity(), "Fill All Fields", Toast.LENGTH_LONG ).show();
            }
            else{
                FireStoreDbHelper.updateItem( listItem, new MyActionListener() {
                    @Override
                    public void onSuccessClicked(Object object) {
                        Toast.makeText( getActivity(), (String)object, Toast.LENGTH_SHORT ).show();
                        getDialog().dismiss();
                    }

                    @Override
                    public void onFailureClicked(Object object) {
                        Toast.makeText( getActivity(), (String)object, Toast.LENGTH_SHORT ).show();
                        getDialog().dismiss();
                    }
                } );
            }
        }

    }

    private void initSpinner(View view) {
        final AppCompatSpinner tagSpinner = view.findViewById( R.id.spinner_tag );
        tagSpinner.setVisibility( View.VISIBLE );
        final TagSpinnerAdapter myAdapter = new TagSpinnerAdapter( getActivity(), tagList, this );
        tagSpinner.setAdapter( myAdapter );
        tagSpinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tagItem= (ToDoTag) tagSpinner.getSelectedItem();
                if(tagItem.getLabel().equals("Select Tag")){

                }
                else {
                    mItemTagList.add( tagItem );
                    if (mItemTagList.size() == 1) {
                        loadDataIntoAdapter();
                    } else {
                        mTagEditAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        } );

    }

    public void SelectPriorityRadioButton() {

        switch (mPriorityList.get( 0 ).getLabel()) {
            case Constants.PRIORITY_HIGH:
                mRadioGroupPriority.check( R.id.rb_high_priority );
            case Constants.PRIORITY_MEDIUM:
                mRadioGroupPriority.check( R.id.rb_medium_priority );
            case Constants.PRIORITY_LOW:
                mRadioGroupPriority.check( R.id.rb_low_priority );
        }

    }


    @Override
    public void onSuccessClicked(Object object) {
        int position = (int) object;
        Toast.makeText( getActivity(), "On Click " + tagList.get( position ).getLabel(), Toast.LENGTH_SHORT ).show();

//         TODO: 8/21/2019 DO not push default tag on server
    }

    @Override
    public void onFailureClicked(Object object) {

    }

    private void loadDataIntoAdapter() {

        mTagEditAdapter = new TagEditAdapter( getActivity(), mItemTagList );
        mRecyclerView.setAdapter( mTagEditAdapter );
    }


}

