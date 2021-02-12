package dev.reese.project1.entities;

import java.sql.Timestamp;
import java.util.List;

public class TuitionReimbursementRequest {
	
	//required info first
	private int id;
	
	private int forEmployee;
	
	private String eventType;
	
	private double totalCost;
	
	private String eventDescription;
	
	private String eventLocation;
	
	private Timestamp startDateTimeOfEvent;
	
	private String workRelatedJustification;
	
	private int gradeFormat; // 0 => out of 100, 1 => p/f, 2 => presenation
	
	private int scoreRequired; //
	
	// Optional for user to incluse: 
	
	private double hoursOfWorkMissed; // if < 0 => null or not filled out
	
	private boolean hasSupervisorApproval;
	
	private boolean hasDepartmentHeadApproval;
	
	private List<RequestNote> notes;
	
		//add notes and attachments lists here
	
	//system changed and added values
	
	private Timestamp dateSubmitted;
	
	private String statusOfRequest;
	
	private int waitingOnMessageId;
	
	private boolean hasBenCoApproval;

	
	public TuitionReimbursementRequest() {
		super();
		this.hoursOfWorkMissed = -1.0;
	}
	
	public TuitionReimbursementRequest(int id) {
		this.id = id;
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getForEmployee() {
		return forEmployee;
	}

	public void setForEmployee(int forEmployee) {
		this.forEmployee = forEmployee;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}

	public String getEventLocation() {
		return eventLocation;
	}

	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}

	public Timestamp getStartDateTimeOfEvent() {
		return startDateTimeOfEvent;
	}

	public void setStartDateTimeOfEvent(Timestamp startDateTimeOfEvent) {
		this.startDateTimeOfEvent = startDateTimeOfEvent;
	}

	public String getWorkRelatedJustification() {
		return workRelatedJustification;
	}

	public void setWorkRelatedJustification(String workRelatedJustification) {
		this.workRelatedJustification = workRelatedJustification;
	}

	public int getGradeFormat() {
		return gradeFormat;
	}

	public void setGradeFormat(int gradeFormat) {
		this.gradeFormat = gradeFormat;
	}

	public int getScoreRequired() {
		return scoreRequired;
	}

	public void setScoreRequired(int scoreRequired) {
		this.scoreRequired = scoreRequired;
	}

	public double getHoursOfWorkMissed() {
		return hoursOfWorkMissed;
	}

	public void setHoursOfWorkMissed(double hoursOfWorkMissed) {
		this.hoursOfWorkMissed = hoursOfWorkMissed;
	}

	public boolean isHasSupervisorApproval() {
		return hasSupervisorApproval;
	}

	public void setHasSupervisorApproval(boolean hasSupervisorApproval) {
		this.hasSupervisorApproval = hasSupervisorApproval;
	}

	public boolean isHasDepartmentHeadApproval() {
		return hasDepartmentHeadApproval;
	}

	public void setHasDepartmentHeadApproval(boolean hasDepartmentHeadApproval) {
		this.hasDepartmentHeadApproval = hasDepartmentHeadApproval;
	}

	public List<RequestNote> getNotes() {
		return notes;
	}

	public void setNotes(List<RequestNote> notes) {
		this.notes = notes;
	}

	public Timestamp getDateSubmitted() {
		return dateSubmitted;
	}

	public void setDateSubmitted(Timestamp dateSubmitted) {
		this.dateSubmitted = dateSubmitted;
	}

	public String getstatusOfRequest() {
		return statusOfRequest;
	}

	public void setstatusOfRequest(String status_of_request) {
		this.statusOfRequest = status_of_request;
	}

	public int getWaitingOnMessageId() {
		return waitingOnMessageId;
	}

	public void setWaitingOnMessageId(int waitingOnMessageId) {
		this.waitingOnMessageId = waitingOnMessageId;
	}

	public boolean isHasBenCoApproval() {
		return hasBenCoApproval;
	}

	public void setHasBenCoApproval(boolean hasBenCoApproval) {
		this.hasBenCoApproval = hasBenCoApproval;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	


}
