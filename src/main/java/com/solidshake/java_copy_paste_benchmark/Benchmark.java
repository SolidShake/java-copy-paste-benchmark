package com.solidshake.java_copy_paste_benchmark;

import java.io.IOException;

public class Benchmark 
{
    public static void main( String[] args ) throws IOException {
    	
    	long fileSize;
    	long defaultFileSize = 1024*1024*512; //512mb
    	
    	if(args.length != 0) {
    		fileSize = Integer.getInteger(args[0]);
    	} else {
    		fileSize = defaultFileSize;
    	}
    	
        BenchmarkEngine benchmark = new BenchmarkEngine(fileSize);
        benchmark.start();
    }
}
