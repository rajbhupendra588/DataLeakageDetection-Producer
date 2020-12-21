package com.appdirect.hackathon2020.iaasconnector.partitioner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;


public class SamplePartitioner implements Partitioner{

	Logger logger = LoggerFactory.getLogger(this.getClass());

	List<String> fileNameList1 = Files.list(Paths.get("/Users/bhupendra.singh/Downloads/DataLeakageDetection/src/main/resources/"))
		.filter(s -> s.toString().endsWith(".csv"))
		.filter(Files::isRegularFile)
		.sorted()
		.map(p -> p.getFileName().toString())
		.collect(Collectors.toList());

	public SamplePartitioner() throws IOException {
//		logger.info("File Names Are: " + fileNameList1.toString());
		//fileNameList2.addAll(fileNameList1);
	}


	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		
		Map<String, ExecutionContext> partitionData = new HashMap<String, ExecutionContext>();
		logger.info("File Names Are of fileNameList1: " + fileNameList1.toString());
		for (int i = 0; i < fileNameList1.size(); i++) {
			ExecutionContext executionContext = new ExecutionContext();
			logger.info("Starting : Thread" + (i+1));
			logger.info("File Name is: " + fileNameList1.get(i));
			
			// give fileName for ExecutionContext
			executionContext.putString("filename", fileNameList1.get(i));
			// give a thread name for ExecutionContext
			executionContext.putString("name", "Thread" + i);
			
			partitionData.put("partition: " + i, executionContext);
		}
		
		return partitionData;
	}

}
