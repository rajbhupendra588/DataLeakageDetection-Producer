package com.appdirect.hackathon2020.iaasconnector.listener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.appdirect.hackathon.hashlib.Hash;
import com.appdirect.hackathon2020.iaasconnector.model.Invoice;
import com.appdirect.hackathon2020.iaasconnector.model.InvoiceWrapper;
import com.appdirect.hackathon2020.iaasconnector.producer.AMQPProducer;

@Component
public class StepCompletionListener implements StepExecutionListener {
	@Autowired
	public JdbcTemplate jdbcTemplate;
	public Hash hash;
	InvoiceWrapper invoiceWrapper;
	@Autowired
	AMQPProducer amqpProducer;

	@Override
	public void beforeStep(StepExecution stepExecution) {
		System.out.println("Executing Step id " + stepExecution.getId());
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		System.out.println("afterStep triggered");
		String fileName = stepExecution.getExecutionContext().getString("filename");
		List<String> al = new ArrayList<>();
		List<String> al3 = new ArrayList<>();

		String strLine = null;
		String str1 = null;
		List<String> al2 = new ArrayList<>();
		List<String> al4 = new ArrayList<>();
		List<Invoice> result = null;
		try {
			String filePath = "/Users/bhupendra.singh/Downloads/DataLeakageDetection/src/main/resources/";
			BufferedReader br1 = new BufferedReader(new FileReader(filePath + stepExecution.getExecutionContext().getString("filename")));

			while ((strLine = br1.readLine()) != null) {
				String strar[] = strLine.split(",");
				al.add(strar[0]);
				str1 = strLine + "," + strar[0];
				al2.add(str1);
			}
			System.out.println("Step ArrayList is =" + al);
			System.out.println("Step ArrayList2 is =" + al2);
			al3.addAll(al);
			al4.addAll(al);
			al.add(hash.calculate(al));
			al.add(fileName);

			System.out.println("Step checksum List is =" + al);
			br1.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		System.out.println("stepExecution.getExecutionContext() -> " + stepExecution.getExecutionContext().getString("filename"));
		String inParams = String.join(",", al3.stream().map(id -> id + "").collect(Collectors.toList()));

		if (stepExecution.getStatus() == BatchStatus.COMPLETED) {
			result = jdbcTemplate.query(String.format("SELECT * FROM invoice WHERE ID IN (%s)", inParams),
				new RowMapper<Invoice>() {
					@Override
					public Invoice mapRow(ResultSet rs, int row) throws SQLException {
						return new Invoice(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getString(5), rs.getString(6));
					}
				});
			System.out.println("Step Data of Records:" + result.toString());

				InvoiceWrapper invoiceWrapper1 = new InvoiceWrapper(result, al.get(al.size() - 1), al.get(al.size() - 2));
				amqpProducer.sendMessage(invoiceWrapper1);
				System.out.println("invoiceWrapper1 Data of Records:" + invoiceWrapper1.toString());
			}
		return stepExecution.getExitStatus();
	}
}
