package com.example.mytodoapp.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mytodoapp.R;
import com.example.mytodoapp.pojo.ToDoPriority;
import com.example.mytodoapp.ui.fragment.SettingsFragment;
import com.example.mytodoapp.ui.fragment.TasksFragment;
import com.example.mytodoapp.ui.helper.BottomNavigationViewBehavior;
import com.example.mytodoapp.ui.helper.DialogFragmentHelper;
import com.example.mytodoapp.ui.helper.TagDisplayDialog;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public List<ToDoPriority> priorityList = new ArrayList<>();
    private AlertDialog.Builder builder;
    private TextView tvtodo;
    private ListView todolist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById( R.id.main_bottom_navigation );
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior( new BottomNavigationViewBehavior() );

        loadFragment( new TasksFragment() );

        bottomNavigationView.setOnNavigationItemSelectedListener( new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.settings:
                        loadFragment( new SettingsFragment() );
                        Toast.makeText( MainActivity.this, "You are in Home", Toast.LENGTH_SHORT ).show();
                        break;
                    case R.id.task_history:
                        loadFragment( new TasksFragment() );
                        Toast.makeText( MainActivity.this, "These are your task", Toast.LENGTH_SHORT ).show();
                        break;
                }
                return true;
            }
        } );


        tvtodo = findViewById( R.id.tv_todo_item );

//        updateUi();


        Toolbar toolbar = findViewById( R.id.toolbar );
        TextView mtoolbarTitle = (TextView) toolbar.findViewById( R.id.toolbar_title );
        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( false );
        getSupportActionBar().setDisplayShowHomeEnabled( true );
        getSupportActionBar().setDisplayShowTitleEnabled( false );

        FloatingActionButton fab = findViewById( R.id.fab );
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                Fragment prev = getSupportFragmentManager().findFragmentByTag( "edit_dialog" );
                if (prev != null) {
                    ft1.remove( prev );
                }
                ft1.addToBackStack( null );

                DialogFragment dialogfragment = new DialogFragmentHelper();
                ((DialogFragmentHelper) dialogfragment).setIsNew( true );
                dialogfragment.setCancelable( false );
                ft1.add( dialogfragment, "edit_dialog" ).commit();
                getSupportFragmentManager().executePendingTransactions();
            }
        } );

    }

    @Override
    public void onBackPressed() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.main_bottom_navigation);
        int seletedItemId = bottomNavigationView.getSelectedItemId();
        if (R.id.task_history != seletedItemId) {
            loadFragment( new TasksFragment() );
        } else {
            finish();
        }
    }

//    private void updateUi() {
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount( this );
//        TextView tvNameUser = findViewById( R.id.tv_name_user );
//
//        tvNameUser.setText( account.getDisplayName() );
//
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate( R.menu.menu_main, menu );
//
//
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.manage_task) {
//
//            FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
//            Fragment prev = getSupportFragmentManager().findFragmentByTag( "display_tag_dialog" );
//            if (prev != null) {
//                ft1.remove( prev );
//            }
//            ft1.addToBackStack( null );
//
//            DialogFragment dialogfragment = new TagDisplayDialog();
//            dialogfragment.show( ft1, "display_tag_dialog" );
//            return true;
//        }

//        return super.onOptionsItemSelected( item );
//    }


    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace( R.id.frame_container, fragment );
        transaction.addToBackStack( null );
        transaction.commit();
    }
}