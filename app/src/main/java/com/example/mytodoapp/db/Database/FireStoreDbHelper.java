package com.example.mytodoapp.db.Database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mytodoapp.MyActionListener;
import com.example.mytodoapp.pojo.ToDoListItem;
import com.example.mytodoapp.pojo.ToDoPriority;
import com.example.mytodoapp.pojo.ToDoTag;
import com.example.mytodoapp.TagListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class FireStoreDbHelper {

    private static final String TODO_ITEMS = "ToDoDatabase";
    private static final String TODO_TAGS = "Tags";

//    public static FirebaseDatabase getFirestoreInstance() {
//        return FirebaseDatabase.getInstance();
//    }

    public static FirebaseFirestore getFirestoreInstance() {
        return FirebaseFirestore.getInstance();
    }

    public static CollectionReference getTagCollectionReference() {
        return getFirestoreInstance().collection( TODO_TAGS );
    }

    public static CollectionReference getToDoCollectionReference() {
        return getFirestoreInstance().collection( TODO_ITEMS );
    }

    public static void addTag(ToDoTag toDoTag) {
        getTagCollectionReference().add( toDoTag );
    }


    public void insertItem(ToDoListItem listItem) {
        getToDoCollectionReference().add( listItem );
    }

    public static void retrieveTags(final TagListener listener) {
        final List<ToDoTag> tagList = new ArrayList<>();
        getTagCollectionReference().get()
                .addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (QueryDocumentSnapshot snapshots : queryDocumentSnapshots) {
                                tagList.add( snapshots.toObject( ToDoTag.class ) );
                            }
                        listener.onSuccessClicked( tagList );
                    }
                } )
                .addOnFailureListener( new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w( TAG, "loadPost:onCancelled", e );
                        listener.onFailureClicked( "Nahi aaye tags!" );
                    }
                } );
    }

    public static void retrieveItems(final MyActionListener listener) {
        final List<ToDoListItem> itemsList = new ArrayList<>();
        getToDoCollectionReference().orderBy( "done" ).get()
                .addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot snapshots : queryDocumentSnapshots) {
                            itemsList.add( snapshots.toObject( ToDoListItem.class ) );
                        }
                        listener.onSuccessClicked( itemsList );
                    }
                } )
                .addOnFailureListener( new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w( TAG, "loadPost:onCancelled", e );
                        listener.onFailureClicked( "Nahi aaya data!" );
                    }
                } );
    }

    public static void retrieveItemsOrderByDate(final MyActionListener listener) {
        final List<ToDoListItem> itemsList = new ArrayList<>();
        getToDoCollectionReference().orderBy( "dateTime" ).get()
                .addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot snapshots : queryDocumentSnapshots) {
                            itemsList.add( snapshots.toObject( ToDoListItem.class ) );
                        }
                        listener.onSuccessClicked( itemsList );
                    }
                } )
                .addOnFailureListener( new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w( TAG, "loadPost:onCancelled", e );
                        listener.onFailureClicked( "Nahi aaya data!" );
                    }
                } );
    }

    public static void retrieveItemsOrderByPriority(final MyActionListener listener) {
        final List<ToDoListItem> itemsList = new ArrayList<>();
        getToDoCollectionReference().orderBy( "priority" ).get()
                .addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot snapshots : queryDocumentSnapshots) {
                            itemsList.add( snapshots.toObject( ToDoListItem.class ) );
                        }
                        listener.onSuccessClicked( itemsList );
                    }
                } )
                .addOnFailureListener( new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w( TAG, "loadPost:onCancelled", e );
                        listener.onFailureClicked( "Nahi aaya data!" );
                    }
                } );
    }

    public static void retrieveItemsOrderByTag(final MyActionListener listener) {
        final List<ToDoListItem> itemsList = new ArrayList<>();
        getToDoCollectionReference().orderBy( "tag" ).get()
                .addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot snapshots : queryDocumentSnapshots) {
                            itemsList.add( snapshots.toObject( ToDoListItem.class ) );
                        }
                        listener.onSuccessClicked( itemsList );
                    }
                } )
                .addOnFailureListener( new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w( TAG, "loadPost:onCancelled", e );
                        listener.onFailureClicked( "Nahi aaya data!" );
                    }
                } );
    }

    public static void updateTags(final ToDoTag toDoTag) {
        getFirestoreInstance().collection( TODO_TAGS ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (toDoTag.getId().equals( document.get( "id" ) )) {
                            getFirestoreInstance().collection( TODO_TAGS )
                                    .document( document.getId() )
                                    .update( "label", toDoTag.getLabel(),
                                            "color", toDoTag.getColor() );
                        }
                    }
                } else {
                    Log.d( TAG, "Error getting documents: ", task.getException() );
                }
            }
        } );

    }


    public static void updateItem(final ToDoListItem listItem, final MyActionListener listener) {
        final List<ToDoPriority> finalPriorityList1 = listItem.getPriority();
        final List<ToDoTag> tagList = listItem.getTag();
        getFirestoreInstance().collection( TODO_ITEMS ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (listItem.getItemId() == ((Integer.parseInt( String.valueOf( document.get( "itemId" ) ) )))) {
                            getFirestoreInstance().collection( TODO_ITEMS )
                                    .document( document.getId() )
                                    .update( "title", listItem.getTitle(),
                                            "description", listItem.getDescription(),
                                            "dateTime", listItem.getDateTime(),
                                            "modifiedDate", listItem.getModifiedDate(),
                                            "priority", finalPriorityList1,
                                             "tag", tagList,
                                            "done", listItem.getDone());
                        }
                    }
                    listener.onSuccessClicked( "Task Updated" );
                } else {
                    listener.onFailureClicked( "Task Not Updated" );
                }
            }
        } );
    }

        public static void deleteTag(final ToDoTag toDoTag) {
            getFirestoreInstance().collection( TODO_TAGS ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        List<String> list = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (toDoTag.getId().equals( document.get( "id" ) )) {
                                DocumentReference tagRef = getFirestoreInstance().collection( TODO_TAGS )
                                        .document( document.getId() );
                                tagRef.delete();
                            }
                        }
                    } else {
                        Log.d( TAG, "Error getting documents: ", task.getException() );
                    }
                }
            } );
        }

        public static void deleteItem(final ToDoListItem listItem) {
            getFirestoreInstance().collection( TODO_ITEMS ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (listItem.getItemId() == ((Integer.parseInt( String.valueOf( document.get( "itemId" ) ) )))) {
                                DocumentReference itemRef = getFirestoreInstance().collection( TODO_ITEMS )
                                        .document( document.getId() );
                                itemRef.delete();
                            }
                        }
                    } else {
                        Log.d( TAG, "Error getting documents: ", task.getException() );
                    }
                }
            } );


        }
}

