package com.appdirect.hackathon2020.iaasconnector.step;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;

import com.appdirect.hackathon2020.iaasconnector.model.Invoice;


public class Reader{

	public static FlatFileItemReader<Invoice> reader(String path) {

		FlatFileItemReader<Invoice> reader = new FlatFileItemReader<Invoice>();

		reader.setResource(new ClassPathResource(path));
		reader.setLineMapper(new DefaultLineMapper<Invoice>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames(new String[] { "id", "companyid", "date", "invoiceNumber", "partner", "total" });
					}
				});
				setFieldSetMapper(new BeanWrapperFieldSetMapper<Invoice>() {
					{
						setTargetType(Invoice.class);
					}
				});
			}
		});
		return reader;
	}
}
