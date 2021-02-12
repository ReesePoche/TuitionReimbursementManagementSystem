package services;

import daos.DBImpDepartmentDAO;
import daos.DBImpEmployeeDAO;
import daos.DepartmentDAO;
import daos.EmployeeDAO;
import dev.reese.project1.entities.Department;
import dev.reese.project1.entities.Employee;
import serviceexceptions.EmailNotFoundException;
import serviceexceptions.PasswordIncorrectException;

public class LoginService {
	
	private EmployeeDAO eDAO = new DBImpEmployeeDAO();
	private DepartmentDAO dDAO = new DBImpDepartmentDAO();
	
	public Employee attemptLogin(String email, String password) throws EmailNotFoundException, PasswordIncorrectException{
		Employee e = eDAO.getEmployee(email);
		if(e.getId() == 0)
			throw new EmailNotFoundException("The email entered does not exist in the database");
		if(!password.equals(e.getPassword()))
			throw new PasswordIncorrectException("The password entered was incorrect");
		return e;
	}
	
	
	public Employee getEmployee(int employeeId){
		return eDAO.getEmployee(employeeId);
	}
	
	/**
	 * 
	 * @param employeeId
	 * @return if the resulting employee has id of 0 that means employee has no supervisor
	 * @throws EmailNotFoundException
	 */
	public Employee getEmployeeSupervisor(int employeeId){
		Employee e = eDAO.getEmployee(employeeId);
		if(e.getSupervisorId() == 0)
			return new Employee(0);
		return this.eDAO.getEmployee(e.getSupervisorId());
	}
	
	/**
	 * 
	 * @param employeeId
	 * @return returns employee with 0  id if employee is in no department or department has no DH, can return the same employee
	 */
	public Employee getEmployeeDepartmentHead(int employeeId) {
		Employee e = eDAO.getEmployee(employeeId);
		if(e.getDepartmentNum() == 0 || e.getDepartmentNum() == -1)
			return new Employee(0);
		Department d = this.dDAO.getDepartment(e.getDepartmentNum());
		if(d.getHeadOfDepartmentId() == 0 || d.getHeadOfDepartmentId() == -1)
			return new Employee(0);
		if(d.getHeadOfDepartmentId() == e.getId())
			return e;
		return this.eDAO.getEmployee(d.getHeadOfDepartmentId());
	}
	
	public Employee getBenCo() {
		Employee e = this.eDAO.getBenCo();
		return e;
	}
	

}
