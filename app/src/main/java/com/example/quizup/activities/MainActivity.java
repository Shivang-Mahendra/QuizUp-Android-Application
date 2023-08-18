package com.example.quizup.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.quizup.R;
import com.example.quizup.adapters.QuizAdapters;
import com.example.quizup.models.Quiz;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActionBarDrawerToggle actionBarDrawerToggle;
    QuizAdapters adapter;
    List<Quiz> quizList;
    FirebaseFirestore fbfs;
    NavigationView nv;
    Button BtnLogOut;
    DrawerLayout MainDrawer, dl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fbfs =  FirebaseFirestore.getInstance();
        quizList = new ArrayList<Quiz>();
//        populateDummyData();
        setUpViews();
    }

//    private void populateDummyData() {
//        quizList.add(new Quiz("17-07-2023", "17-07-2023"));
//        quizList.add(new Quiz("18-07-2023", "18-07-2023"));
//        quizList.add(new Quiz("19-07-2023", "19-07-2023"));
//        quizList.add(new Quiz("20-07-2023", "20-07-2023"));
//        quizList.add(new Quiz("21-07-2023", "21-07-2023"));
//        quizList.add(new Quiz("22-07-2023", "22-07-2023"));
//        quizList.add(new Quiz("23-07-2023", "23-07-2023"));
//    }

    void setUpViews(){

        setUpFirestore();
        setUpDrawerLayout();
        setUpRecyclerView();
        setUpDate();
    }

    private void setUpDate() {
        View DatePicker = findViewById(R.id.btnPickDate);
        DatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDatePicker<Long> date_picker = MaterialDatePicker.Builder.datePicker().build();
                date_picker.show(getSupportFragmentManager(), "Datepicker");
                date_picker.addOnPositiveButtonClickListener(selection -> {
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-mm-yyyy");
                    String date = dateFormatter.format(new Date(selection));
                    Log.d("DATEPICKER", date_picker.getHeaderText());
                    Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
                    intent.putExtra("DATE", date);
                    startActivity(intent);
                });
                date_picker.addOnNegativeButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("DATEPICKER", date_picker.getHeaderText());
                    }
                });
                date_picker.addOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Log.d("DATEPICKER", "Date Picker Cancelled");
                    }
                });
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    void setUpFirestore() {
        CollectionReference collectionReference = fbfs.collection("quizzes");
        collectionReference.addSnapshotListener((value, error) -> {
            if(value == null || error != null){
                Toast.makeText(MainActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                return ;
            }
//            Log.d("DATA", value.toObjects(Quiz.class).toString());
            quizList.clear();
            quizList.addAll(value.toObjects(Quiz.class));
            adapter.notifyDataSetChanged(); //to notify Recycler View that data has changed
        });
    }

    private void setUpRecyclerView() {
        adapter = new QuizAdapters(this, quizList);
        RecyclerView qRecyclerView = findViewById(R.id.quizRecyclerView);
        qRecyclerView.setLayoutManager( new GridLayoutManager(this,2));
        qRecyclerView.setAdapter(adapter);
    }
    void setUpDrawerLayout(){
        androidx.appcompat.widget.Toolbar ab = findViewById(R.id.AppBar);
        setSupportActionBar(ab);
        dl = findViewById(R.id.mainDrawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, dl, R.string.app_name, R.string.app_name);
        actionBarDrawerToggle.syncState();
        // to get to Profile Activity from navigation bar
        nv = (NavigationView) findViewById(R.id.navigationView);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getTitle().equals("Profile")){
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    MainDrawer = (DrawerLayout) findViewById(R.id.mainDrawer);
                    MainDrawer.closeDrawers();
                }
                return true; // tells all handling is done and no need for any further handling
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}