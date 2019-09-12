package com.example.mytodoapp.ui.helper;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.mytodoapp.networking.ApiProgressListener;
import com.example.mytodoapp.networking.GetAllItemsAPI;
import com.example.mytodoapp.db.Database.DataBaseViewModel;
import com.example.mytodoapp.pojo.ToDoListItem;
import com.example.mytodoapp.pojo.ToDoPriority;
import com.example.mytodoapp.pojo.ToDoResponseObject;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Utility {

    public static final String API_TODO_BASE_URL = "https://demo9956420.mockable.io/";
//    public static int Tag_id = 4;
//    public static int TOdo_Item_id = 0;
    public static int colour_int;
    public static boolean SWITCH_DATE = false;
    public static boolean SWITCH_PRIORITY = false;
    public static boolean SWITCH_TAG = false;
//    public static boolean colourStateArray[] = {false, false, false, false, false, false, false, false, false};
    public ToDoPriority priorityItem = new ToDoPriority();

    public static ToDoPriority checkPriorityList(String priorityLabel, ToDoPriority priorityItem){

        switch (priorityLabel){
            case Constants.PRIORITY_HIGH:
                priorityItem.setLabel( Constants.PRIORITY_HIGH );
                priorityItem.setId( 1 );
                priorityItem.setColor( "#D20264" );
                break;
            case Constants.PRIORITY_MEDIUM:
                priorityItem.setLabel( Constants.PRIORITY_MEDIUM );
                priorityItem.setId( 2 );
                priorityItem.setColor( "#45D82F" );
                break;
            case Constants.PRIORITY_LOW:
                priorityItem.setLabel( Constants.PRIORITY_LOW );
                priorityItem.setId( 3 );
                priorityItem.setColor( "#FFD505" );
                break;
        }
        return priorityItem;
    }

    public static void PreparePriorityList(List<ToDoPriority> priorityList){
        priorityList.add( new ToDoPriority( 1, "High Priority", "Red" ) );
        priorityList.add( new ToDoPriority( 2, "Medium Priority", "Green" ) );
        priorityList.add( new ToDoPriority( 3, "Low Priority", "Blue" ) );
    }

    public static ToDoResponseObject getListFromFile(Context ctx) {

        String json = null;

        try {
            InputStream is = ctx.getAssets().open( "todo_item_pojo.json" );
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read( buffer );
            is.close();
            json = new String( buffer, "UTF-8" );
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return new Gson().fromJson( json, ToDoResponseObject.class );
    }

    public static void loadItemsintoDataBaseOnLoad(final Fragment ctx) {

//        DatabaseHelper db = new DatabaseHelper(ctx);
//       DataBaseViewModel model = new DataBaseViewModel(ctx.);
//        DataBaseViewModel model = ViewModelProviders.of(this).get(DataBaseViewModel.class);
        final DataBaseViewModel model = ViewModelProviders.of( ctx ).get( DataBaseViewModel.class );

//        ToDoResponseObject listFromFile = getListFromFile( ctx.getActivity() );
        loadItemsFromNetwork( ctx.getActivity(), new ApiProgressListener() {

            @Override
            public void onSuccesOfAPI(Object data) {

                final List<ToDoListItem> listFromFile = (List<ToDoListItem>) data;
                if (listFromFile != null && listFromFile.size() > 0) {


                    for (ToDoListItem item : listFromFile) {
                        model.insertItem( item );
                    }

                    new PreferenceHelper( ctx.getActivity() ).setDbIsLoadedFirstTime();
                } else {
                    Toast.makeText( ctx.getActivity(), "List was empty!", Toast.LENGTH_SHORT ).show();
                }

            }

            @Override
            public void onFailureOfAPI(Object data) {
                Toast.makeText( ctx.getActivity(), "Some error occurred! Code: " + ((int) data), Toast.LENGTH_SHORT ).show();
            }
        } );


    }

    public static void loadItemsFromNetwork(final Context ctx, final ApiProgressListener apiProgressListener) {

        final List<ToDoListItem> toDoListItems = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( API_TODO_BASE_URL )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();

        GetAllItemsAPI getAllItemsAPI = retrofit.create( GetAllItemsAPI.class );

        Call<List<ToDoListItem>> call = getAllItemsAPI.getToDoListItem();

        call.enqueue( new Callback<List<ToDoListItem>>() {

            @Override
            public void onResponse(Call<List<ToDoListItem>> call, Response<List<ToDoListItem>> response) {

                if (!response.isSuccessful()) {
                    apiProgressListener.onFailureOfAPI( response.code() );
                    return;
                }

                List<ToDoListItem> body = response.body();
                if (body != null) {
                    toDoListItems.addAll( body );
                    testPrintResponse( toDoListItems );
                }

                apiProgressListener.onSuccesOfAPI( toDoListItems );

            }

            @Override
            public void onFailure(Call<List<ToDoListItem>> call, Throwable t) {
                Toast.makeText( ctx, t.getMessage(), Toast.LENGTH_SHORT ).show();
            }
        } );

    }

    public static String getCurrentFormattedDate() {
        DateFormat df = new SimpleDateFormat( "dd-MM-yyyy, HH:mm" );
        String date = df.format( Calendar.getInstance().getTime() );
        return date;
    }

    public static void testPrintResponse(List<ToDoListItem> toDoListItems) {

        if (toDoListItems == null || toDoListItems.size() < 0) {
            Log.d( "Utility", "NO data received" );
            return;
        }
        for (ToDoListItem item : toDoListItems) {

            item.toString();

        }

    }


}
