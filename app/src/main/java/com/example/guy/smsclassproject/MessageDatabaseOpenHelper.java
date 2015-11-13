package com.example.guy.smsclassproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by txz141530 on 11/12/2015.
 * ref--http://developer.android.com/guide/topics/data/data-storage.html#db
 */
public class MessageDatabaseOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String MESSAGEDATABASE_TABLE_NAME = "messageDatabase";
    private static final String MESSAGEDATABASE_TABLE_CREATE ="CREATE TABLE" + MESSAGEDATABASE_TABLE_NAME + " (" + KEY_WORD +"TEXT, " + KEY_DEFINITION + " TEXT);";

    public MessageDatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MESSAGEDATABASE_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
