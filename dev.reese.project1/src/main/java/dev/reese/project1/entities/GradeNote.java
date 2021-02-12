package dev.reese.project1.entities;

import java.sql.Timestamp;

public class GradeNote {
	
	private int id;
	
	private int gradeSubmissionId;
	
	private String subject;
	
	private String note;
	
	private int addedBy;
	
	private Timestamp addedAt;
	
	public GradeNote() {
		super();
	}
	
	public GradeNote(int id) {
		this.id = id;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGradeSubmissionId() {
		return gradeSubmissionId;
	}

	public void setGradeSubmissionId(int gradeSubmissionId) {
		this.gradeSubmissionId = gradeSubmissionId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(int addedBy) {
		this.addedBy = addedBy;
	}

	public Timestamp getAddedAt() {
		return addedAt;
	}

	public void setAddedAt(Timestamp addedAt) {
		this.addedAt = addedAt;
	}
	
	
	
	

}
