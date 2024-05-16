package com.abc.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	 private int customerId;
	    private String customerName;
	    private String contactName;
	    private String address;
	    private String city;
	    private String postalCode;
	    private String country;
}
