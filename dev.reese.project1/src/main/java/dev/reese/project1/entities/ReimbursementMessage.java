package dev.reese.project1.entities;

import java.sql.Timestamp;

public class ReimbursementMessage {
	
	private int id;
	
	private int requestId;
	
	private String typeOfMessage;
	
	private int recieverId;
	
	private int senderId;
	
	private String senderNotes;
	
	private String status;
	
	private String response;
	
	private Timestamp createdAt;
	
	private Timestamp respondedAt;
	
	public ReimbursementMessage() {
		super();
	}
	
	public ReimbursementMessage(int id) {
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

	public String getTypeOfMessage() {
		return typeOfMessage;
	}

	public void setTypeOfMessage(String typeOfMessage) {
		this.typeOfMessage = typeOfMessage;
	}

	public int getRecieverId() {
		return recieverId;
	}

	public void setRecieverId(int recieverId) {
		this.recieverId = recieverId;
	}

	public int getSenderId() {
		return senderId;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public String getSenderNotes() {
		return senderNotes;
	}

	public void setSenderNotes(String senderNotes) {
		this.senderNotes = senderNotes;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getRespondedAt() {
		return respondedAt;
	}

	public void setRespondedAt(Timestamp respondedAt) {
		this.respondedAt = respondedAt;
	}
	
	
	
	

}
