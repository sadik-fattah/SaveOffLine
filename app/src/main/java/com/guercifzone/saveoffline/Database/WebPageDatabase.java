package com.guercifzone.saveoffline.Database;



import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.guercifzone.saveoffline.Models.WebPage;

import java.util.ArrayList;
import java.util.List;

public class WebPageDatabase {

    private SQLiteDatabase db;
    private DatabaseHelper dbhelper;

    public WebPageDatabase(Context context) {
        dbhelper = new DatabaseHelper(context);
        db = dbhelper.getWritableDatabase();
    }
    public void savePage(WebPage webPage) {

        ContentValues values = new ContentValues();
        values.put("url", webPage.getUrl());
        values.put("html_content", webPage.getHtmlContent());
        db.insert("webpage", null, values);
    }
    public boolean isWebPageExists(String url) {
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursor = db.query("webpages", new String[]{"url"}, "url = ?", new String[]{url}, null, null, null);

        boolean exists = cursor.getCount() > 0;
        cursor.close();

        return exists;
    }
public void deletePage(String url) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        db.delete("webpage", "url = ?", new String[]{url});
}
    @SuppressLint("Range")
    public ArrayList<WebPage> getAllWebPages() {
        ArrayList<WebPage> webpages = new ArrayList<>();
        // Query to get all the webpages
        Cursor cursor = db.rawQuery("SELECT * FROM webpage", null);
        if (cursor.moveToFirst()) {
            do {

                 String url = cursor.getString(cursor.getColumnIndex("url"));
                String htmlContent = cursor.getString(cursor.getColumnIndex("html_content"));
                webpages.add(new WebPage(url, htmlContent));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return webpages;
    }

}
