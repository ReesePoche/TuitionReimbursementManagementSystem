package daos;

import java.util.List;

import dev.reese.project1.entities.Department;

public interface DepartmentDAO {
	
	public boolean createDepartment(Department d);
	
	public Department getDepartment(String name);
	
	public Department getDepartment(int id);
	
	public List<Department> getAllDepartments();
	
	public boolean updateDepartment(Department d);
	
	public boolean deleteDepartment(int id);

}
