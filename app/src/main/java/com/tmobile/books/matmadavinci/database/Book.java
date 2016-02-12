package com.tmobile.books.matmadavinci.database;

public class Book {
	private long id;
	private int bookNumber; // tap 1 - tap 2
	// public static final String BOOK_NUMBER = "BOOK_NUMBER";//tap 1 - tap 2 -
	// tap 3
	private int chapterNumber; // 1-2-3
	// public static final String BOOK_CHAPTER_NUMBER = "BOOK_CHAPTER_NUMBER";
	// // 1-2-3
	private String chapterContent;
	// public static final String BOOK_CHAPTER_CONTENT =
	// "BOOK_CHAPTER_CONTENT";//noi dung chuong 1
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getBookNumber() {
		return bookNumber;
	}
	public void setBookNumber(int bookNumber) {
		this.bookNumber = bookNumber;
	}
	public int getChapterNumber() {
		return chapterNumber;
	}
	public void setChapterNumber(int chapterNumber) {
		this.chapterNumber = chapterNumber;
	}
	public String getChapterContent() {
		return chapterContent;
	}
	public void setChapterContent(String chapterContent) {
		this.chapterContent = chapterContent;
	}

}
