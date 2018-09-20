package com.solidshake.java_copy_paste_benchmark;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;

import com.solidshake.java_copy_paste_benchmark.fileManager.FileManager;

public class BenchmarkEngine {
	
	private long fileSize;
	private static final String FILE_PATH = new File("").getAbsolutePath();
	private static final int MS_2_S_COEFFICIENT = 1000;
	
	public BenchmarkEngine(long fileSize) {
		this.fileSize = fileSize;
	}
	
	private String copyFileUsingStream1024(File source, File dest) throws IOException {
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
	    
	    double resultTime = (double)(System.currentTimeMillis() - startTime)/MS_2_S_COEFFICIENT;
	    
		return "Time to copy by Streams (1024b buffer size) : " + resultTime + "s.\r\n";
	}
	
	@SuppressWarnings("resource")
	private String copyFileUsingChannel(File source, File dest) throws IOException {
		long startTime = System.currentTimeMillis();
		
	    FileChannel sourceChannel = null;
	    FileChannel destChannel = null;
	    try {
	        sourceChannel = new FileInputStream(source).getChannel();
	        destChannel = new FileOutputStream(dest).getChannel();
	        destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
	    } finally {
	           sourceChannel.close();
	           destChannel.close();
	    }
	    
	    double resultTime = (double)(System.currentTimeMillis() - startTime)/MS_2_S_COEFFICIENT;
	    
		return "Time to copy by File Using Channel: " + resultTime + "s.\r\n";
	}
	
	private String copyFileUsingJava7Files(File source, File dest) throws IOException {
		long startTime = System.currentTimeMillis();
		
	    Files.copy(source.toPath(), dest.toPath());
	    
	    double resultTime = (double)(System.currentTimeMillis() - startTime)/MS_2_S_COEFFICIENT;
	    
		return "Time to copy by Java 8 Files class: " + resultTime + "s.\r\n";
	}
	
	public void start() throws IOException {
		
		try {
			FileManager.createTestFile(FILE_PATH, "TestFile", fileSize);
		} catch (IllegalArgumentException ex) {
			String sizeErr = "Not enough disk space or less than or equal to 0mb file \r\n";
			FileManager.printInFile(sizeErr, fileSize);
	
			System.exit(1);
		}
		
		String benchmarkOutput = "";
		String sysInfo = FileManager.printSysInfo(fileSize);
		benchmarkOutput += sysInfo;

		File source = new File(FILE_PATH + "\\" + "TestFile");
		File dest = new File(FILE_PATH + "\\" + "OutputTestFile");
		
		String copyFileUsingStream1024result = copyFileUsingStream1024(source, dest);
		benchmarkOutput += copyFileUsingStream1024result;
		FileManager.deleteTestFile(dest);
		
		String copyFileUsingChannelresult = copyFileUsingChannel(source, dest);
		benchmarkOutput += copyFileUsingChannelresult;
		FileManager.deleteTestFile(dest);

		String copyFileUsingJava7Filesresult = copyFileUsingJava7Files(source, dest);
		benchmarkOutput += copyFileUsingJava7Filesresult;
		FileManager.deleteTestFile(dest);
		
		FileManager.deleteTestFile(source);
		
		FileManager.printInFile(benchmarkOutput, fileSize);
	}
}
