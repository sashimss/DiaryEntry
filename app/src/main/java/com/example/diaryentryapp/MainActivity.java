package com.example.diaryentryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.LineNumberReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DiaryAdapter adapter;
    DiaryDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_entries);

        db = DiaryDatabase.getInstance(this);
        List<DiaryItem> entries = db.itemDao().getAll();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DiaryAdapter(entries, (entry)->{
            Intent intent = new Intent(getApplicationContext(), EditEntry.class);
            intent.putExtra("Date", entry.getDate());
            intent.putExtra("Content", entry.getContent());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        Button AddButton = findViewById(R.id.button_add);
        AddButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), EditEntry.class);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String date = sdf.format(new Date());
            intent.putExtra("Date", sdf.format(new Date()));
            DiaryItem content = db.itemDao().getByDate(date);
            intent.putExtra("Content", content!=null ? content.getContent() : "");
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.RefreshList(db.itemDao().getAll());
    }

}