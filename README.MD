# Java copy-paste benchmark

This benchmark tests the copying of files by several methods in different operating systems:
- Input/OutputStream
- FileChannel
- Files

# Build

	mvn package

Default test (with 512mb file):
	java -cp target\java-copy-paste-benchmark-0.0.1-SNAPSHOT.jar com.solidshake.java_copy_paste_benchmark.Benchmark
Custom test (with 64mb or any other sizes):
	java -cp target\java-copy-paste-benchmark-0.0.1-SNAPSHOT.jar com.solidshake.java_copy_paste_benchmark.Benchmark 64
