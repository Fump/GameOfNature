package com.example.androidtest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySqlLiteHelper extends SQLiteOpenHelper {
	public static final String TABLE_NOTEPAD = "NOTEPAD";
	public static final String ENTRY_TITLE = "ENTRY_TITLE";
	public static final String ENTRY_TEXT = "ENTRY_TEXT";
	
	private static final String DATABASE_NAME = "notepad.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_NOTEPAD + "(" + ENTRY_TITLE
			+ " text not null, " + ENTRY_TEXT
			+ " text not null);";
			
	
	public MySqlLiteHelper(Context context) {
		    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    Log.w(MySqlLiteHelper.class.getName(),
	            "Upgrading database from version " + oldVersion + " to "
	                + newVersion + ", which will destroy all old data");
	        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTEPAD);
	        onCreate(db);
	}
}
