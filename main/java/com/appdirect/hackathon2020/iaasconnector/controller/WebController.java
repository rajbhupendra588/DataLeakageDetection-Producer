package com.appdirect.hackathon2020.iaasconnector.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appdirect.hackathon.hashlib.Hash;

@RestController
public class WebController {
	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job job;
	public Hash hash;


	@RequestMapping("/runjob")
	public String handle() throws Exception {
		Logger logger = LoggerFactory.getLogger(this.getClass());
		JobParameters jobParameters=null;

		try {
			int lines = 10;  //set this to whatever number of lines you need in each file
			int count = 0;
			String inputfile = "/Users/bhupendra.singh/Downloads/SpringBatchPartitioning/src/main/resources/files/invoice.csv";
			File file = new File(inputfile);
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {  //counting the lines in the input file
				scanner.nextLine();
				count++;
			}
			System.out.println(count);
			int files = 0;
			if ((count % lines) == 0) {
				files = (count / lines);
			} else {
				files = (count / lines) + 1;
			}
			System.out.println("Number of Files created "+(files-1)); //number of files that shall eb created

			myFunction(lines, files);
		} catch (IOException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		try {
			jobParameters = new JobParametersBuilder().addString("time", LocalDateTime.now().toString()).addString("Partner","Connector")
					.toJobParameters();
			jobLauncher.run(job, jobParameters);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return "Done! Job Started! with parameters -> "+jobParameters.toString();
	}

	private void myFunction(int lines, int files) throws FileNotFoundException, IOException, NoSuchAlgorithmException {

		String inputfile = "/Users/bhupendra.singh/Downloads/SpringBatchPartitioning/src/main/resources/files/invoice.csv";
		BufferedReader br = new BufferedReader(new FileReader(inputfile)); //reader for input file intitialized only once
		br.readLine(); // consume first line and ignore
		String strLine;
		ArrayList<String> al = new ArrayList<>();
		ArrayList<String> al1 = new ArrayList<>();
		for (int i=1;i<files;i++) {

			for(int j=0;j<lines;j++){   //iterating the reader to read only the first few lines of the csv as defined earlier
				strLine = br.readLine();
				if (strLine!= null) {

					String strar[] = strLine.split(",");
					al.add(strar[0]);
					String str1 = strLine +","+strar[0];
				}
			}
			System.out.println("ArrayList is ="+al);
			al1.add(hash.calculate(al));
			System.out.println("checksum List is ="+al1);
			al.clear();
		}

		String inputfile1 = "/Users/bhupendra.singh/Downloads/SpringBatchPartitioning/src/main/resources/files/invoice.csv";
		BufferedReader br1 = new BufferedReader(new FileReader(inputfile1)); //reader for input file intitialized only once
		br1.readLine();
		for (int i=1,k=0;i<files && k < al1.size();i++,k++) {
			FileWriter fstream1 = new FileWriter("/Users/bhupendra.singh/Downloads/SpringBatchPartitioning/src/main/resources/" +(i)+"_file.csv"); //creating a new file writer.

			BufferedWriter out = new BufferedWriter(fstream1);
			for(int j=1;j<=lines;j++){   //iterating the reader to read only the first few lines of the csv as defined earlier
				strLine = br1.readLine();
				if (strLine!= null) {

					String strar[] = strLine.split(",");
					al.add(strar[0]);
					String str1 = strLine; //+","+  (k+1); // al1.get(k);
					System.out.println("ArrayList1 is ="+ str1); //al1.get(k));
					out.write(str1);   //acquring the first column
					out.newLine();
				}
			}
			out.close();
		}

	}
}
