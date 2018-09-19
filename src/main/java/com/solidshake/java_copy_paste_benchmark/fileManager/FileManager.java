package com.solidshake.java_copy_paste_benchmark.fileManager;

import java.io.*;

public class FileManager {
		
	public static File createTestFile(String path, String fileName, long fileSize) throws IOException {
		File testFile = new File(path + "\\" + fileName);
		RandomAccessFile randomTestFile = new RandomAccessFile(testFile, "rw");
		randomTestFile.setLength(fileSize);
		randomTestFile.close();
		
		return testFile;
	}
	
	public static boolean deleteTestFile(File fileName) {
		return fileName.delete();
	}
}
