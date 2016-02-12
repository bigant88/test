package com.devhn.amazingfactsaboutsex;

import java.util.ArrayList;

import com.tmobile.books.matmadavinci.database.Book;


public class AppSingleton {

	private static AppSingleton instance;
//	private ArrayList<String> chapterContent;
	private ArrayList<Book> listBooks;
	private boolean isDataLoaded;
	private AppSingleton() {
//		chapterContent = new ArrayList<String>();
		listBooks = new ArrayList<Book>();
	}

	public static void initInstance() {
		if (instance == null) {
			// Create the instance
			instance = new AppSingleton();
		}
	}

	public static AppSingleton getInstance() {
		return instance;
	}

	public ArrayList<Book> getListBooks() {
		return listBooks;
	}

	public void setListBooks(ArrayList<Book> listBooks) {
		this.listBooks = listBooks;
	}

//	public ArrayList<String> getChapterContent() {
//		return chapterContent;
//	}
//	public void setChapterContent(ArrayList<String> s){
//		this.chapterContent = s;
//	}
//	
//	public void addToChapterContent(String s){
//		chapterContent.add(s);
//	}

	public boolean isDataLoaded() {
		return isDataLoaded;
	}

	public void setDataLoaded(boolean isDataLoaded) {
		this.isDataLoaded = isDataLoaded;
	}
	
}
