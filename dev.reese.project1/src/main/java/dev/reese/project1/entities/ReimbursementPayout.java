package dev.reese.project1.entities;

import java.sql.Timestamp;

public class ReimbursementPayout {
	
	private int id;
	
	private int requestId;
	
	private double amount;
	
	private int forYear;
	
	private String status;
	
	private Timestamp dateOfPayment;
	
	private String notes;

	public ReimbursementPayout() {
		super();
	}
	
	public ReimbursementPayout(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getForYear() {
		return forYear;
	}

	public void setForYear(int forYear) {
		this.forYear = forYear;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getDateOfPayment() {
		return dateOfPayment;
	}

	public void setDateOfPayment(Timestamp dateOfPayment) {
		this.dateOfPayment = dateOfPayment;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	
	
	
	

}
