package dev.reese.project1.entities;

public class Employee {
	
	private int id;
	
	private String email;
	
	private String password; 
	
	private String firstName;
	
	private String lastName;
	
	private String middleName;
	
	private String jobTitle;
	
	private int supervisorId;
	
	private int departmentNum;

	public Employee() {
		super();
	}
	
	public Employee(int id) {
		this.id = id;
	}
	
	
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public int getSupervisorId() {
		return supervisorId;
	}

	public void setSupervisorId(int supervisorId) {
		this.supervisorId = supervisorId;
	}

	public int getDepartmentNum() {
		return departmentNum;
	}

	public void setDepartmentNum(int departmentNum) {
		this.departmentNum = departmentNum;
	}

	

}
