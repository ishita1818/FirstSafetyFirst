package com.products.safetyfirst.androidhelpers;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.products.safetyfirst.Pojos.PostModel;

import java.util.ArrayList;

/**
 * Created by ishita sharma on 12/13/2017.
 */

public class PostHelper {

    public interface UpdateSnapshot{
        void updateList(ArrayList<PostDocument> mSnapshots);
    }

    private static final String TAG= "PostHelper";
    private int THRESHOLD= 10;
    private UpdateSnapshot updateSnapshot;
    private ArrayList<PostDocument> mSnapshots= new ArrayList<>();
    DocumentSnapshot postLastVisible, authorLastVisible,usersnapshot;
    Query mQuery;

    public PostHelper(Query query) {
        mQuery=query.limit(THRESHOLD);
    }

    private int document_count;
    private boolean ismakeQueryCalled= false;

    // Invoke makeQuery method when starting to load data.
    public void makeQuery(UpdateSnapshot u){
        ismakeQueryCalled=true;
            updateSnapshot=u;
            Log.v(TAG,mQuery.toString());
            mQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    if (task.isSuccessful()) {
                        document_count=0;
                        for (final DocumentSnapshot postdocument : task.getResult()) {
                            document_count++;
                            Log.v(TAG, postdocument.getId() + " => " + postdocument.getData());
                            PostModel postModel= new PostModel(postdocument.getData()) ;
                            String uid= postModel.getUid();
                            FirebaseFirestore.getInstance().collection("users").whereEqualTo("id",uid).get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if(task.isSuccessful()){
                                                if(!task.getResult().isEmpty()) {
                                                    usersnapshot = task.getResult().getDocuments().get(0);

                                                    if(document_count==10) {
                                                        mSnapshots.add(new PostDocument(postdocument,usersnapshot));
                                                        updateSnapshot.updateList(mSnapshots);
                                                        PostHelper.this.notify();
                                                        postLastVisible=postdocument;
                                                        authorLastVisible=usersnapshot;
                                                    }
                                                }
                                                else
                                                 usersnapshot=null;
                                            }
                                        }
                                    });

                        }
                    } else {
                        Log.v(TAG, "Error getting documents: ", task.getException());
                    }
                }
            });

        mSnapshots.clear();
    }

    //this method can be called as soon as we have to load more data with the same query
    //parameters as earlier. Here we can use the lastVisible documentSnapshot to start with.
    public void makeNextSetOfQuery(){
        if(!ismakeQueryCalled){
            Log.v(TAG,"Error: First invoke makeQuery");
        }
        else {
            if (postLastVisible != null)
                mQuery.startAt(postLastVisible)
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            document_count=0;
                            for (final DocumentSnapshot postdocument : task.getResult()) {
                                document_count++;
                                Log.v(TAG, postdocument.getId() + " => " + postdocument.getData());
                                PostModel postModel = new PostModel(postdocument.getData());
                                String uid = postModel.getUid();
                                FirebaseFirestore.getInstance().collection("users").whereEqualTo("uid", uid).get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    usersnapshot = task.getResult().getDocuments().get(0);
                                                    if(document_count==10) {
                                                        mSnapshots.add(new PostDocument(postdocument, usersnapshot));
                                                        updateSnapshot.updateList(mSnapshots);
                                                        PostHelper.this.notify();
                                                        postLastVisible=postdocument;
                                                        authorLastVisible=usersnapshot;
                                                    }
                                                }
                                            }
                                        });
                            }

                        } else {
                            Log.v(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
            mSnapshots.clear();
        }
    }

    DocumentSnapshot author;
    public DocumentSnapshot findAuthor(DocumentSnapshot d){
        PostModel p= new PostModel(d.getData());
        String uid= p.getUid();
        FirebaseFirestore.getInstance().collection("users").whereEqualTo("uid",uid).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            author= task.getResult().getDocuments().get(0);
                        }
                    }
                });
        return author;
    }

}
