package com.guercifzone.saveoffline;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ContentOffLine extends AppCompatActivity {
    private TextView contentTextView;
    private TextView urlTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_content_off_line);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        contentTextView = findViewById(R.id.webView);
        urlTextView = findViewById(R.id.urlTextView);
        Html.ImageGetter imageGetter = new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                Drawable drawable = getResources().getDrawable(R.drawable.ic_launcher_background);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                return drawable;
            }
        };
        String url = getIntent().getStringExtra("url");
        String htmlContent = getIntent().getStringExtra("html_content");
        urlTextView.setText("URL: " + url);
        Spanned spanned = Html.fromHtml(htmlContent, Html.FROM_HTML_MODE_LEGACY, imageGetter, null);
        if (htmlContent == null) {
            contentTextView.setText("Content not  existe");
        }else {

            contentTextView.setText(spanned);

        }

    }
}