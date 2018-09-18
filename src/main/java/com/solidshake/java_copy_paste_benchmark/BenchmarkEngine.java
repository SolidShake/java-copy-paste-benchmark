package com.solidshake.java_copy_paste_benchmark;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;

import com.solidshake.java_copy_paste_benchmark.fileManager.FileManager;

public class BenchmarkEngine {
	
	private long fileSize;
	private static final String FILEPATH = new File("").getAbsolutePath();
	
	public BenchmarkEngine(long fileSize) {
		 this.fileSize = fileSize;
	}
	
	private static String copyFileUsingStream1024(File source, File dest) throws IOException {
		long startTime = System.currentTimeMillis();
		
	    InputStream is = null;
	    OutputStream os = null;
	    try {
	        is = new FileInputStream(source);
	        os = new FileOutputStream(dest);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) > 0) {
	            os.write(buffer, 0, length);
	        }
	    } finally {
	        is.close();
	        os.close();
	    }
	    
	    double resultTime = (double)(System.currentTimeMillis() - startTime);
	    
		return "Time to copy by Streams (1024b buffer size) is: " + resultTime/1000 + "s.\n";
	}
	
	@SuppressWarnings("resource")
	private static void copyFileUsingChannel(File source, File dest) throws IOException {
	    FileChannel sourceChannel = null;
	    FileChannel destChannel = null;
	    try {
	        sourceChannel = new FileInputStream(source).getChannel();
	        destChannel = new FileOutputStream(dest).getChannel();
	        destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
	       }finally{
	           sourceChannel.close();
	           destChannel.close();
	       }
	}
	
	private static void copyFileUsingJava7Files(File source, File dest) throws IOException {
	    Files.copy(source.toPath(), dest.toPath());
	}
	
	private void printSysInfo() {
		String fileSizeInfo = "Test file size: " + this.fileSize/1024/1024 + " mb\n";
		String operationSysInfo = "Operation System: " + System.getProperty("os.name");
		String osVerInfo = "OS Version: " +  System.getProperty("os.version");
		
		System.out.print(fileSizeInfo);
		System.out.println(operationSysInfo);
		System.out.println(osVerInfo);
	}
	
	@SuppressWarnings("resource")
	private static void printInFile(String text) {
		try {
			FileWriter writer = new FileWriter("output.txt");
			writer.append(text);
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void start() throws IOException {
		FileManager.createTestFile(FILEPATH, "myTestFile", fileSize);
		printSysInfo();

		File source = new File(FILEPATH + "\\" + "myTestFile");
		File dest = new File(FILEPATH + "\\" + "myOutputTestFile");
		
		
		long startTime = System.currentTimeMillis();
		copyFileUsingStream1024(source, dest);
		double resultTime = (double)(System.currentTimeMillis() - startTime);
		System.out.println("Time to copy by Streams (1024b buffer size) is: " + resultTime/1000 + "s.");
		FileManager.deleteTestFile(dest);
		
		startTime = System.currentTimeMillis();
		copyFileUsingChannel(source, dest);
		resultTime = (double)(System.currentTimeMillis() - startTime);
		System.out.println("Time to copy by File Using Channel: " + resultTime/1000 + "s.");
		FileManager.deleteTestFile(dest);
		
		startTime = System.currentTimeMillis();
		copyFileUsingJava7Files(source, dest);
		resultTime = (double)(System.currentTimeMillis() - startTime);
		System.out.println("Time to copy by Java 8 Files class: " + resultTime/1000 + "s.");
		FileManager.deleteTestFile(dest);
		
		FileManager.deleteTestFile(source);
		
		//File result = new File("benchmarkResult.txt", "rw");	
	}
}
