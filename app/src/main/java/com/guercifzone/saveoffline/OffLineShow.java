package com.guercifzone.saveoffline;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.guercifzone.saveoffline.Adapters.WebPageAdapter;
import com.guercifzone.saveoffline.Database.WebPageDatabase;
import com.guercifzone.saveoffline.Models.WebPage;

import java.util.ArrayList;
import java.util.List;

public class OffLineShow extends AppCompatActivity {
    private SwipeRefreshLayout swipeRefreshLayout;
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
        swipeRefreshLayout = findViewById(R.id.main);

        ArrayList<WebPage> webpages = webPageDatabase.getAllWebPages();
        if (webpages.isEmpty() || webpages == null){
            Toast.makeText(this, "No webpages found", Toast.LENGTH_SHORT).show();
        }else {
            webPageList = webPageDatabase.getAllWebPages();
            webPageAdapter = new WebPageAdapter(this, webpages);
            gridView.setAdapter(webPageAdapter);
        }
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_dark,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_red_dark
        );
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                                    @Override
                                                    public void onRefresh() {
                                                        refreshData();
                                                    }
                                                });
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


        }

    private void refreshData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                webPageList.clear(); // Clear the current list
                webPageList.addAll(webPageDatabase.getAllWebPages()); // Add updated data

                // Notify the adapter to update the GridView
                webPageAdapter.notifyDataSetChanged();

                // Stop the refresh animation
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);
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
