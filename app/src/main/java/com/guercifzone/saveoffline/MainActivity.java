package com.guercifzone.saveoffline;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
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
    private Button saveButton,runButton,offlineButton;
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
        offlineButton = findViewById(R.id.offlineButton);
        webPageDatabase = new WebPageDatabase(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());

        saveButton.setOnClickListener(v -> {
            webView.setWebViewClient(new WebViewClient(){
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    savePageOffline();
                }
            });

        });
        runButton.setOnClickListener(v -> {
            String url = searchView.getText().toString();
            webView.loadUrl(url);
        });
        offlineButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this,OffLineShow.class));

        });


    }

    private void savePageOffline() {
       /* try {
            String url = webView.getUrl();
            webView.evaluateJavascript("document.documentElement.outerHTML", html -> {
                // html will contain the page's HTML
                String pageHtml = html;

                // Now save the HTML content to the database
                WebPage webpage = new WebPage(url,pageHtml);
                WebPageDatabase webPageDatabase = new WebPageDatabase(this);
                webPageDatabase.savePage(webpage);
                Toast.makeText(this, "Page saved offline", Toast.LENGTH_SHORT).show();
            });

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error saving page offline", Toast.LENGTH_SHORT).show();
        }*/
    webView.evaluateJavascript("document.documentElement.outerHTML",new ValueCallback<String>() {

        @Override
        public void onReceiveValue(String htmlContent) {
            saveHtmlContentToDatabase(htmlContent);
        }
    });

    }

    private void saveHtmlContentToDatabase(String htmlContent) {
        String url = webView.getUrl();
        WebPage webpage = new WebPage(url,htmlContent);
        WebPageDatabase webPageDatabase = new WebPageDatabase(this);
        webPageDatabase.savePage(webpage);
        Toast.makeText(this, "Page saved offline", Toast.LENGTH_SHORT).show();
    }


}