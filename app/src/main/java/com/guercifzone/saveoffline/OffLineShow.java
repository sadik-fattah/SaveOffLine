package com.guercifzone.saveoffline;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.guercifzone.saveoffline.Adapters.WebPageAdapter;
import com.guercifzone.saveoffline.Database.WebPageDatabase;
import com.guercifzone.saveoffline.Models.WebPage;

import java.util.ArrayList;
import java.util.List;

public class OffLineShow extends AppCompatActivity {
    private WebPageDatabase webPageDatabase;
    private GridView gridView;
    private WebPageAdapter webPageAdapter;
    private List<WebPage> webPageList;
    private Button deleteButton;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_off_line_show);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        webPageDatabase = new WebPageDatabase(this);
        gridView = findViewById(R.id.gridView);
        deleteButton = findViewById(R.id.deleteButton);

        ArrayList<WebPage> webpages = webPageDatabase.getAllWebPages();
        if (webpages.isEmpty() || webpages == null){
            Toast.makeText(this, "No webpages found", Toast.LENGTH_SHORT).show();
        }else {
            webPageList = webPageDatabase.getAllWebPages();
            webPageAdapter = new WebPageAdapter(this, webpages);
            gridView.setAdapter(webPageAdapter);
        }
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WebPage selectedWebPage = (WebPage) parent.getItemAtPosition(position);
                Intent intent = new Intent(OffLineShow.this, ContentOffLine.class);
                intent.putExtra("url", selectedWebPage.getUrl());
                intent.putExtra("html_content", selectedWebPage.getHtmlContent());
                startActivity(intent);
            }
        });
  deleteButton.setOnClickListener(v -> {
      webPageDatabase.deleteWebPage(1);
      webPageList.clear();
      webPageList.addAll(webPageDatabase.getAllWebPages());
      webPageAdapter.notifyDataSetChanged();
  });

        }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload data when the activity is resumed
        webPageList.clear();
        webPageList.addAll(webPageDatabase.getAllWebPages());
        webPageAdapter.notifyDataSetChanged();
    }
    }
