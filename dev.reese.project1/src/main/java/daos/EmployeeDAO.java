package daos;

import java.util.List;

import dev.reese.project1.entities.Employee;

public interface EmployeeDAO {
	
	public boolean createEmployee(Employee e);
	
	public Employee getEmployee(String email);
	
	public Employee getEmployee(int id);
	
	public Employee getBenCo();
	
	public List<Employee> getAllEmployees();
	
	public boolean updateEmployee(Employee e);
	
	public boolean deleteEmployee(int id);
	

}
