package com.example.myactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.core.View;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button btn_save;
    EditText et_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_save=findViewById(R.id.btn_save);
        et_name=findViewById(R.id.et_user_name);


    }

    public void saveToFirebase(android.view.View view) {
        String username=et_name.getText().toString();
        if (!username.isEmpty()){


        Map<String, Object> user = new HashMap<>();
        user.put("name", username.toString());


// Add a new document with a generated ID
        db.collection("UserData")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                        OpenSecondactivity();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                    }
                });
        }else{
            Toast.makeText(this, "Please fill the filed", Toast.LENGTH_SHORT).show();
        }
    }

    public void OpenSecondactivity(){
        Intent intent= new Intent(this,MainActivity2.class);
        startActivity(intent);
    }
}