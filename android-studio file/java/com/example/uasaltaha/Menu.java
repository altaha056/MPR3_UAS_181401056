package com.example.uasaltaha;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Menu extends AppCompatActivity {

    AdapterRecycleView1 adapterRecycleView1;
    RecyclerView recyclerView;
    LinearLayout mProfil, mExplore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);

        mProfil = findViewById(R.id.profile1);
        recyclerView = findViewById(R.id.recycle1);
        mExplore = findViewById(R.id.explore1);

        List<ModelData> data=new ArrayList<>();
        data.add(new ModelData("Taverns","Gather with folks, join the party, and sip the grape.",R.drawable.taverns));
        data.add(new ModelData("Aurora","The most mesmerizing theatre by the nature.",R.drawable.northernlights));
        data.add(new ModelData("Mythology","Valhalla calling your name through the boat we sailed.",R.drawable.myth3));
        data.add(new ModelData("Saga","Once upon a time when a wolf as big as mountain.",R.drawable.myth2));
        data.add(new ModelData("Norsemen","Become the warriors and travel around the world!",R.drawable.ship));
        data.add(new ModelData("Brotherhood","One tribe, one clan, one blood.",R.drawable.myth));
        adapterRecycleView1=new AdapterRecycleView1(data);

        recyclerView.setAdapter(adapterRecycleView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        mProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Profile.class));
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });

        mExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), Explore.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
            }
        });
}}