package com.appdirect.hackathon2020.iaasconnector.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.appdirect.hackathon2020.iaasconnector.model.Invoice;


@Repository
public class InvoiceDao extends JdbcDaoSupport {

	@Autowired
	DataSource dataSource;

	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
	}

	public void insert(List<? extends Invoice> Invoice) {
		String sql = "INSERT INTO invoice " + "(id, companyid, date , invoiceNumber, partner, total) VALUES ( ?, ?, ?, ?, ?, ?)";
		getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Invoice invoice = Invoice.get(i);
				ps.setString(1, invoice.getId());
				ps.setString(2, invoice.getCompanyId());
				ps.setString(3, invoice.getDate());
				ps.setString(4, invoice.getInvoiceNumber());
				ps.setString(5, invoice.getPartner());
				ps.setDouble(6, invoice.getTotal());
			}

			public int getBatchSize() {
				return Invoice.size();
			}
		});

	}
}
