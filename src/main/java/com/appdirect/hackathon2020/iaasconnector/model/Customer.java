package com.appdirect.hackathon2020.iaasconnector.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@ToString
public class Customer {
	private String id;
	private String firstName;
	private String lastName;
	private String chunkId;

	public Customer(String id, String firstName, String lastName, String chunkId) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.chunkId = chunkId;
	}
}
