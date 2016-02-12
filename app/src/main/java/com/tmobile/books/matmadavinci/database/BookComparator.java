package com.tmobile.books.matmadavinci.database;

import java.util.Comparator;

public class BookComparator implements Comparator<Book> {

	@Override
	public int compare(Book arg0, Book arg1) {

		return (int) (arg1.getId() - arg0.getId());
	}

}
