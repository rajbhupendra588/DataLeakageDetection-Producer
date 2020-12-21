package com.appdirect.hackathon2020.iaasconnector.listener;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.appdirect.hackathon.hashlib.Hash;
import com.appdirect.hackathon2020.iaasconnector.model.Invoice;
import com.appdirect.hackathon2020.iaasconnector.producer.JobAMQPProducer;

@Component
public class JobCompletionListener implements JobExecutionListener {
	@Autowired
	public JdbcTemplate jdbcTemplate;
	public Hash hash;
	@Autowired
	JobAMQPProducer jobAMQPProducer ;


	@Override
	public void beforeJob(JobExecution jobExecution) {
		System.out.println("Executing job id " + jobExecution.getId());
		jdbcTemplate.update("delete from invoice");
		System.out.println("Data of table invoice has been deleted during job Initialization");
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		System.out.println("afterJob triggered");
		;
		System.out.println("JobExecution.getExecutionContext()"+ jobExecution.getExecutionContext().toString());
//		stepExecution.getExecutionContext().
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			List<Invoice> result = jdbcTemplate.query("SELECT * FROM invoice",
				new RowMapper<Invoice>() {
					@Override
					public Invoice mapRow(ResultSet rs, int row) throws SQLException {
						return new Invoice(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getString(5), rs.getString(6));
					}
				});
			System.out.println("Number of Records:" + result.size());
			System.out.println("Data of Records:" + result.toString());
			//jobAMQPProducer.sendJobMessage("Job has been completed");
		}
	}
}
