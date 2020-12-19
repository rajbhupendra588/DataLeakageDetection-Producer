package com.appdirect.hackathon2020.iaasconnector.step;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

import com.appdirect.hackathon2020.iaasconnector.dao.InvoiceDao;
import com.appdirect.hackathon2020.iaasconnector.model.Invoice;


public class Writer implements ItemWriter<Invoice> {

	private final InvoiceDao invoiceDao;

	public Writer(InvoiceDao invoiceDao) {
		this.invoiceDao = invoiceDao;
	}

	@Override
	public void write(List<? extends Invoice> customers) throws Exception {
		invoiceDao.insert(customers);
	}

}
