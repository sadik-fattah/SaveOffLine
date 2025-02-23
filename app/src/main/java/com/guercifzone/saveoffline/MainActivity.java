package com.guercifzone.saveoffline;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.guercifzone.saveoffline.Database.WebPageDatabase;
import com.guercifzone.saveoffline.Models.WebPage;

import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity  {
    private WebView webView;
    private Button saveButton,runButton;
    private EditText searchView;
    private WebPageDatabase webPageDatabase;
    @SuppressLint("MissingInflatedId")
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
        webView = findViewById(R.id.webView);
        saveButton = findViewById(R.id.saveButton);
        runButton = findViewById(R.id.runButton);
        searchView = findViewById(R.id.serachView);
        webPageDatabase = new WebPageDatabase(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        saveButton.setOnClickListener(v -> {
            savePageOffline();
        });
        runButton.setOnClickListener(v -> {
            String url = searchView.getText().toString();
            webView.loadUrl(url);
        });
    }

    private void savePageOffline() {
        try {
            String url = webView.getUrl();
            String htmlContent = webView.getUrl();
            WebPage webPage = new WebPage(htmlContent, url);
            webPageDatabase.savePage(webPage);
            Toast.makeText(this, "Page saved offline", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error saving page offline", Toast.LENGTH_SHORT).show();
        }
    }


}