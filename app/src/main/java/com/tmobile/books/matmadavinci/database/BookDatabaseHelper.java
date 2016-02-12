package com.tmobile.books.matmadavinci.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.devhn.amazingfactsaboutsex.Constants;

public class BookDatabaseHelper extends SQLiteOpenHelper {

	public BookDatabaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, Constants.DATABASE_NAME, factory,
				Constants.DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
//		db.execSQL(Constants.CREATE_TABLE_BOOK);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
//		db.execSQL("DROP TABLE IF EXISTS " + Constants.BOOK_TABLE);
//		onCreate(db);
	}

}
