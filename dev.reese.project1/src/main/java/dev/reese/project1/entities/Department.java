package dev.reese.project1.entities;

public class Department {

	private int id;
	
	private String departmentName;
	
	private int headOfDepartmentId;

	public Department() {
		super();
	}
	
	public Department(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public int getHeadOfDepartmentId() {
		return headOfDepartmentId;
	}

	public void setHeadOfDepartmentId(int headOfDepartmentId) {
		this.headOfDepartmentId = headOfDepartmentId;
	}
	
	
}
