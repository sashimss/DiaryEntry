package com.example.diaryentryapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class EditEntry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_entry);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView dateTV = findViewById(R.id.text_date);
        ListView contentLV = findViewById(R.id.list_content);
        EditText contentEV = findViewById(R.id.edit_content);
        Button DoneBTN = findViewById(R.id.button_done);

        // GET DATE AND CONTENT
        Intent intent = getIntent();
        String date = intent.getStringExtra("Date");
        String content = intent.getStringExtra("Content");
        List<String> lines = new ArrayList<>(Arrays.asList(content.split("\\R")));

        dateTV.setText(date);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.list_view_layout,
                R.id.text_list_item,
                lines
        );
        contentLV.setAdapter(adapter);
        contentEV.requestFocus();

        // ACTIVATE KEYBOARD
        contentEV.post(() -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(contentEV, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        // DIARY DATABASE
        DiaryDatabase db = DiaryDatabase.getInstance(this);
        DoneBTN.setOnClickListener(v -> {
            System.out.println("HERE");
            lines.add(contentEV.getText().toString());
//            String new_content = content + contentEV.getText().toString();
            String new_content = String.join("\n", lines);
            new Thread(() ->
                    db.itemDao().insert(new DiaryItem(dateTV.getText().toString(), new_content))).start();
            finish();
        });
    }


}