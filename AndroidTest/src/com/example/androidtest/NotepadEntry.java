package com.example.androidtest;

import android.os.Parcel;
import android.os.Parcelable;

public class NotepadEntry implements Parcelable  {
	private String title;
	private String text;
	
	public static final Parcelable.Creator<NotepadEntry> CREATOR
    = new Parcelable.Creator<NotepadEntry>() {

		public NotepadEntry createFromParcel(Parcel in) {
		  return new NotepadEntry(in);
		}

		public NotepadEntry[] newArray(int size) {
		  return new NotepadEntry[size];
		}
	};
	
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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(title);
		dest.writeString(text);
	}
}