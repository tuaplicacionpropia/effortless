package org.effortless.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;

//import org.apache.commons.io.IOUtils;

public class IOUtils {

	public static void copy(Reader reader, OutputStream os) throws IOException {
		org.apache.commons.io.IOUtils.copy(reader, os);
	}

	public static void closeQuietly(Reader reader) {
		org.apache.commons.io.IOUtils.closeQuietly(reader);
	}

	public static void closeQuietly(OutputStream os) {
		org.apache.commons.io.IOUtils.closeQuietly(os);
	}

	public static void copy(InputStream input, OutputStream os) throws IOException {
		org.apache.commons.io.IOUtils.copy(input, os);
	}

	public static void closeQuietly(InputStream input) {
		org.apache.commons.io.IOUtils.closeQuietly(input);
	}

	public static File toFile (InputStream inputStream) throws IOException {
		File result = null;
		OutputStream outputStream = null;
	 
		try {
			result = File.createTempFile(".tmp", ".tmp");
//			result = new File("/Users/mkyong/Downloads/holder-new.js");
			// write the inputStream to a FileOutputStream
			outputStream = new FileOutputStream(result);
	 
			int read = 0;
			byte[] bytes = new byte[1024];
	 
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					// outputStream.flush();
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	 
			}
		}
		return result;
	    }
	
}
