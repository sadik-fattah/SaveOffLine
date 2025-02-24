package com.guercifzone.saveoffline.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.guercifzone.saveoffline.Models.WebPage;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "webpages.db";
    private static final int DATABASE_VERSION = 1;

    private static final  String TABLE_NAME = "webpage";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_URL = "url";
    private static final String COLUMN_HTML_CONTENT = "html_content";


    private static final  String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_URL + " TEXT NOT NULL, "
            + COLUMN_HTML_CONTENT + " TEXT NOT NULL"
            + ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
onCreate(db);
    }

    public void savePage(WebPage webPage) {
        SQLiteDatabase db = this.getWritableDatabase(); // Get writable database

        // Prepare values to insert
        ContentValues values = new ContentValues();
        values.put(COLUMN_URL, webPage.getUrl());
        values.put(COLUMN_HTML_CONTENT, webPage.getHtmlContent());

        // Insert the new record into the database
        db.insert(TABLE_NAME, null, values);

        // Close the database connection
        db.close();
    }

}
