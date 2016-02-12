package com.devhn.amazingfactsaboutsex;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;

public class Utils {
	/**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transfering bytestream.
	 * */
	public static void copyDataBase(Context mContext, String packageName,
			String dbname) throws IOException {
		// Open your local db as the input stream
		InputStream myInput = mContext.getAssets().open(dbname);
		// Path to the just created empty db
		String outFileName = "/data/data/" + packageName + "/databases/"
				+ dbname;
		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);
		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}
		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();
	}
}
