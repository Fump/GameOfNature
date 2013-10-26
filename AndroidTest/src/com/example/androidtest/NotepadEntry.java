package com.example.androidtest;

import android.os.Parcel;
import android.os.Parcelable;

public class NotepadEntry  {
	private String title;
	private String text;
	
	public NotepadEntry(Parcel source) {
	    title = source.readString();
	    text = source.readString();
	}
	
	public NotepadEntry(String title, String text) {
		this.title = title;
		this.text = text;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
	@Override
	public String toString() {
		return title;
	}
}