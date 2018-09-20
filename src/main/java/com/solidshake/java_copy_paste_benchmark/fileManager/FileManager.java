package com.solidshake.java_copy_paste_benchmark.fileManager;

import java.io.*;

import com.solidshake.java_copy_paste_benchmark.Benchmark;

public class FileManager {
		
	public static File createTestFile(String path, String fileName, long fileSize) throws IOException {
		File testFile = new File(path + "\\" + fileName);
		RandomAccessFile randomTestFile = new RandomAccessFile(testFile, "rw");

		if(testFile.getUsableSpace()/2 < fileSize || fileSize <= 0.0) {
			randomTestFile.close();
			deleteTestFile(testFile);

			throw new IllegalArgumentException("Incorrect file size");
		} 	
		
		randomTestFile.setLength(fileSize);	
		randomTestFile.close();
		
		return testFile;
	}
	
	public static boolean deleteTestFile(File fileName) {
		return fileName.delete();
	}
	
	public static String printSysInfo(long testFileSize) {
		String fileSizeInfo = "Test file size: " + testFileSize/Benchmark.B_2_MB_COEFFICIENT + " mb" + "\r\n";
		String operationSysInfo = "Operation System: " + System.getProperty("os.name") + "\r\n";
		String osVerInfo = "OS Version: " +  System.getProperty("os.version") +"\r\n";
		
		String sysOut = fileSizeInfo + operationSysInfo + osVerInfo;
		
		return sysOut;
	}
	
	@SuppressWarnings("resource")
	public static void printInFile(String text, long testFileSize) {
		try {
			FileWriter writer = new FileWriter("Benchmark-results-"+ testFileSize/Benchmark.B_2_MB_COEFFICIENT + "mb-file" + ".txt");
			writer.append(text);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
