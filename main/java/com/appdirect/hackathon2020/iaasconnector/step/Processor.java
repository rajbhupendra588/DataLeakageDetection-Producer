package com.appdirect.hackathon2020.iaasconnector.step;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.appdirect.hackathon2020.iaasconnector.model.Invoice;


public class Processor implements ItemProcessor<Invoice, Invoice> {

	private static final Logger log = LoggerFactory.getLogger(Processor.class);

	private String threadName;
	
	@Override
	public Invoice process(Invoice customer) throws Exception {
		Random r = new Random();

		final String id = customer.getId();
		final String firstName = customer.getId().toUpperCase();
//		final String lastName = customer.getLastName().toUpperCase();
//		final String chunkId = customer.getChunkId();
		final Invoice fixedCustomer = Invoice.builder().id(customer.getId())
			.companyId(customer.getCompanyId())
			.date(customer.getDate())
			.partner(customer.getPartner())
			.invoiceNumber(customer.getInvoiceNumber())
			.total(customer.getTotal()).build();

		log.info(threadName + " processing : "+ "Converting (" + customer + ") into (" + fixedCustomer + ")");
		
		return fixedCustomer;
	}

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}
}
