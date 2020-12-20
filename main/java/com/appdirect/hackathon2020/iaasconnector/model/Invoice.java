package com.appdirect.hackathon2020.iaasconnector.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "invoice")
public class Invoice implements Serializable {
	private static final long serialVersionUID = -1932657871853938402L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	private String partner;
	private String companyId;
	private Double total;
	private String invoiceNumber;
	private String date;
}
