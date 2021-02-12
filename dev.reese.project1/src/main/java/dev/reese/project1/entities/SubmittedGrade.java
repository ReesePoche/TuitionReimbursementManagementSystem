package dev.reese.project1.entities;

import java.sql.Timestamp;
import java.util.List;

public class SubmittedGrade {
	
	private int id;
	
	private int requestId;
	
	private int employeeSelfReportedScore;
	
	
	private List<GradeNote> notes;
	
	private Timestamp submittedAt;
	
	// feilds minipulated after review
	
	private int reviewedBy;
	
	private Timestamp reviewedAt;
	
	private boolean passed;
	
	public SubmittedGrade() {
		super();
	}
	
	public SubmittedGrade(int id) {
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

	public int getEmployeeSelfReportedScore() {
		return employeeSelfReportedScore;
	}

	public void setEmployeeSelfReportedScore(int employeeSelfReportedScore) {
		this.employeeSelfReportedScore = employeeSelfReportedScore;
	}

	public List<GradeNote> getNotes() {
		return notes;
	}

	public void setNotes(List<GradeNote> notes) {
		this.notes = notes;
	}

	public Timestamp getSubmittedAt() {
		return submittedAt;
	}

	public void setSubmittedAt(Timestamp submittedAt) {
		this.submittedAt = submittedAt;
	}

	public int getReviewedBy() {
		return reviewedBy;
	}

	public void setReviewedBy(int reviewedBy) {
		this.reviewedBy = reviewedBy;
	}

	public Timestamp getReviewedAt() {
		return reviewedAt;
	}

	public void setReviewedAt(Timestamp reviewedAt) {
		this.reviewedAt = reviewedAt;
	}

	public boolean isPassed() {
		return passed;
	}

	public void setPassed(boolean passed) {
		this.passed = passed;
	}
	
	
	
	
	
	
	
	

}
