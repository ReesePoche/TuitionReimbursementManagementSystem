package dev.reese.project1.entities;

public class RequestFileAttachment {
	
	private int id;
	
	private int requestId;
	
	private String fileDescription;
	
	private String fileType;
	
	private byte[] file;

	private int addedBy;
	
	private java.sql.Timestamp addedAt;
	
	private boolean isApprovalEmail;

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

	public String getFileDescription() {
		return fileDescription;
	}

	public void setFileDescription(String fileDescription) {
		this.fileDescription = fileDescription;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public int getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(int addedBy) {
		this.addedBy = addedBy;
	}

	public java.sql.Timestamp getAddedAt() {
		return addedAt;
	}

	public void setAddedAt(java.sql.Timestamp addedAt) {
		this.addedAt = addedAt;
	}

	public boolean isApprovalEmail() {
		return isApprovalEmail;
	}

	public void setApprovalEmail(boolean isApprovalEmail) {
		this.isApprovalEmail = isApprovalEmail;
	}
	
	
}
