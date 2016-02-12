package com.tmobile.books.matmadavinci.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.devhn.amazingfactsaboutsex.Constants;

public class BookDatabase {
	private static BookDatabase database;
	private BookDatabaseHelper databaseHelper;
	private SQLiteDatabase readableDatabase, writableDatabase;
	private String[] allColumns = { Constants.BOOK_ID, Constants.BOOK_NUMBER,
			Constants.BOOK_CHAPTER_NUMBER, Constants.BOOK_CHAPTER_CONTENT};

	public static BookDatabase getInstance(Context ctx) {
		if (database == null) {
			database = new BookDatabase(ctx);
		}
		return database;
	}

	private BookDatabase(Context context) {
		databaseHelper = new BookDatabaseHelper(context,
				Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
		readableDatabase = databaseHelper.getReadableDatabase();
		writableDatabase  = databaseHelper.getWritableDatabase();
	}

	/**
	 * tao mot ban ghi moi trong bang
	 * 
	 * @param bookRecord
	 * @return
	 */
	public long createRecord(Book bookRecord) {
		ContentValues values = new ContentValues();
		values.put(allColumns[1], bookRecord.getBookNumber());
		values.put(allColumns[2], bookRecord.getChapterNumber());
		values.put(allColumns[3], bookRecord.getChapterContent());	
		long insertId = writableDatabase.insert(
				Constants.BOOK_TABLE, null, values);
		bookRecord.setId(insertId);
		return insertId;
	}

	public void deleteAWord(long id) {
		writableDatabase.delete(Constants.BOOK_TABLE,
				allColumns[0] + " = " + id, null);
	}
	
	public void deleteAll() {
		writableDatabase.delete(Constants.BOOK_TABLE,null, null);
	}


	/**
	 * map cac gia tri lay duoc tu trong bang vao doi tuong Constants.BOOK_ID,
	 * Constants.BOOK_KEY, Constants.BOOK_SEARCH_SUCCEED,
	 * Constants.BOOK_DIC_TYPE, Constants.BOOK_SEARCH_RESULT
	 * 
	 * @param cursor
	 * @return
	 */
	private Book cursorToObject(Cursor cursor) {
		Book word = new Book();
//		values.put(allColumns[1], wordRecord.getBookNumber());
//		values.put(allColumns[2], wordRecord.getChapterNumber());
//		values.put(allColumns[3], wordRecord.getChapterContent());
		int bookNumber = cursor.getInt(1);
		int chapterNumber = cursor.getInt(2);
		String chapterContent = cursor.getString(3);
		word.setId(cursor.getLong(0));
		word.setBookNumber(bookNumber);
		word.setChapterNumber(chapterNumber);
		word.setChapterContent(chapterContent);		
		return word;
	}

	public ArrayList<Book> getAllRecords() {
		ArrayList<Book> listWords = new ArrayList<Book>();

		Cursor cursor = readableDatabase.query(
				Constants.BOOK_TABLE, allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Book word = cursorToObject(cursor);
			listWords.add(word);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return listWords;
	}

	public Book findBookById(int id) {

		Cursor cursor = readableDatabase.query(
				Constants.BOOK_TABLE, allColumns,
				Constants.BOOK_ID + " = " + id, null, null, null, null);

		cursor.moveToFirst();
		Book word = null;
		while (!cursor.isAfterLast()) {
			word = cursorToObject(cursor);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return word;
	}

}
