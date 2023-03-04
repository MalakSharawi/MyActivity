package com.example.myactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity implements UserAdapter.ItemClickListener, UserAdapter.ItemClickListener2 {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    //  ImageView delete;
    TextView tv_name;
    ArrayList<User> items;
    UserAdapter[] myListData;
    UserAdapter adapter;
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    RecyclerView rv;
    ImageView delete;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        rv = findViewById(R.id.recycler);
        items = new ArrayList<User>();
        adapter = new UserAdapter(this, items, this, this);
        delete = findViewById(R.id.delete);
        getUser();

    }
    public  void getUser(){
        db.collection("UserData").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (documentSnapshots.isEmpty()) {
                            Log.d("drn", "onSuccess: LIST EMPTY");
                            return;
                        } else {
                            for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                if (documentSnapshot.exists()) {
                                    String id = documentSnapshot.getId();
                                    String username = documentSnapshot.getString("name");

                                    User user = new User(id, username);
                                    items.add(user);

                                    rv.setLayoutManager(layoutManager);
                                    rv.setHasFixedSize(true);
                                    rv.setAdapter(adapter);
                                    ;
                                    adapter.notifyDataSetChanged();
                                    Log.e("LogDATA", items.toString());

                                }
                            }
                        }


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("LogDATA", "get failed with ");


                    }
                });
    }



    public  void Delete(final User user){
    db.collection("UserData").document(user.getId())
            .delete()
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    items.remove(user);
                    adapter.notifyDataSetChanged();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("logData","get failed with delete");
                }
            });
    }

    @Override
    public void onItemClick(int position, String id) {
    Delete(items.get(position));
    }

    @Override
    public void onItemClick2(int position, String id) {

    }
}