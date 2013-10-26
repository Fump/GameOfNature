package com.example.androidtest;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NotepadDataSource {
	
	private SQLiteDatabase database;
	private MySqlLiteHelper dbHelper;
	private String[] allColumns = { MySqlLiteHelper.ENTRY_TITLE, MySqlLiteHelper.ENTRY_TEXT};
	
	public NotepadDataSource(Context context){
		dbHelper = new MySqlLiteHelper(context);
	}
	
	public void open() {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public void addEntry(NotepadEntry e) {
		ContentValues values = new ContentValues();
		
		values.put(MySqlLiteHelper.ENTRY_TITLE, e.getTitle());
		values.put(MySqlLiteHelper.ENTRY_TEXT, e.getText());
		
		database.insert(MySqlLiteHelper.TABLE_NOTEPAD, null, values);
	}
	
	public List<NotepadEntry> getAllEntires() {
		List<NotepadEntry> entries = new ArrayList<NotepadEntry>();
		
		Cursor cursor = database.query(MySqlLiteHelper.TABLE_NOTEPAD, 
				allColumns, null, null, null, null, null);
		
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()) {
			entries.add(cursorToEntry(cursor));
			cursor.moveToNext();
		}
		
		cursor.close();
		
		return entries;
	}
	
	private NotepadEntry cursorToEntry(Cursor cursor) {
		String title = cursor.getString(0);
		String text = cursor.getString(1);
		
		return new NotepadEntry(title,text);
	}
}
