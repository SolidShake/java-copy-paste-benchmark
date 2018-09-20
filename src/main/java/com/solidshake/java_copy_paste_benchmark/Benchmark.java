package com.solidshake.java_copy_paste_benchmark;

import java.io.IOException;

public class Benchmark 
{
	public static final long B_2_MB_COEFFICIENT = 1048576; 

	public static void main( String[] args ) throws IOException {
    	
    	long fileSize;
    	long defaultFileSize = B_2_MB_COEFFICIENT * 512; //512mb default file
    	
    	try {
    		if(args.length != 0) {
        		fileSize = B_2_MB_COEFFICIENT * Long.valueOf(args[0]) ;
        		
        	} else {
        		fileSize = defaultFileSize;
        	}
        	
            BenchmarkEngine benchmark = new BenchmarkEngine(fileSize);
            benchmark.start();
            
    	} catch (NumberFormatException ex) {
    		System.err.println("Incorrect number format");
    	}
    }
}
