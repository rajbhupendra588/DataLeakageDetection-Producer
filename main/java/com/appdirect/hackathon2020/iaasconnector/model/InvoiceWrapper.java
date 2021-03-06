package com.appdirect.hackathon2020.iaasconnector.model;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceWrapper {
	private static final long serialVersionUID = 7985035234194877060L;
	private List<Invoice> invoiceList;
	private String chunkId;
	private String checksum;
}
