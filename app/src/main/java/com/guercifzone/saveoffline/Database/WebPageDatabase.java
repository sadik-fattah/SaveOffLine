package com.guercifzone.saveoffline.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.guercifzone.saveoffline.Models.WebPage;

public class WebPageDatabase {
    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;
    public WebPageDatabase(Context context) {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
    }
    public void savePage(WebPage webPage) {
        ContentValues values = new ContentValues();
        values.put("url", webPage.getUrl());
        values.put("html_content", webPage.getHtmlContent());
        database.insert("webpage", null, values);
    }
}
