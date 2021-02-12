package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dev.reese.project1.util.JDBCConnection;
import dev.reese.project1.entities.Employee;

public class DBImpEmployeeDAO implements EmployeeDAO{

	public static Connection conn = JDBCConnection.getConnection();
	
	@Override
	public boolean createEmployee(Employee e) {
		try {
			String sql = "INSERT INTO employees "
					+ "			(email, password, first_name, last_name, middle_name, job_title, supervisor, department)"
					+ "			VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, e.getEmail());
			ps.setString(2, e.getPassword());
			ps.setString(3, e.getFirstName());
			ps.setString(4, e.getLastName());
			ps.setString(5, (e.getMiddleName() != null ? e.getMiddleName() : "NULL"));
			ps.setString(6, e.getJobTitle());
			ps.setString(7, Integer.toString(e.getSupervisorId()));
			ps.setString(8, Integer.toString(e.getDepartmentNum()));
			ps.executeQuery();
			return true;
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return false;
	}

	@Override
	public Employee getEmployee(String email) {
		try {
			String sql = "SELECT * FROM employees WHERE email = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				Employee e = new Employee();
				e.setId(rs.getInt("ID"));
				e.setEmail(rs.getString("EMAIL"));
				e.setPassword(rs.getString("PASSWORD"));
				e.setFirstName(rs.getString("FIRST_NAME"));
				e.setLastName(rs.getString("LAST_NAME"));
				e.setMiddleName(rs.getString("MIDDLE_NAME"));
				e.setJobTitle(rs.getString("JOB_TITLE"));
				e.setSupervisorId(rs.getInt("SUPERVISOR"));
				e.setDepartmentNum(rs.getInt("DEPARTMENT"));

				return e;
			}
			return new Employee(0);
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return null;
	}

	@Override
	public Employee getEmployee(int id) {
		try {
			String sql = "SELECT * FROM employees WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(id));
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				Employee e = new Employee();
				e.setId(rs.getInt("ID"));
				e.setEmail(rs.getString("EMAIL"));
				e.setPassword(rs.getString("PASSWORD"));
				e.setFirstName(rs.getString("FIRST_NAME"));
				e.setLastName(rs.getString("LAST_NAME"));
				e.setMiddleName(rs.getString("MIDDLE_NAME"));
				e.setJobTitle(rs.getString("JOB_TITLE"));
				e.setSupervisorId(rs.getInt("SUPERVISOR"));
				e.setDepartmentNum(rs.getInt("DEPARTMENT"));
				return e;
			}
			return new Employee(0);
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return null;
	}
	
	public Employee getBenCo() {
		try {
			String sql = "SELECT * FROM employees WHERE job_title = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, "Benefits Coordinator");
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				Employee e = new Employee();
				e.setId(rs.getInt("ID"));
				e.setEmail(rs.getString("EMAIL"));
				e.setPassword(rs.getString("PASSWORD"));
				e.setFirstName(rs.getString("FIRST_NAME"));
				e.setLastName(rs.getString("LAST_NAME"));
				e.setMiddleName(rs.getString("MIDDLE_NAME"));
				e.setJobTitle(rs.getString("JOB_TITLE"));
				e.setSupervisorId(rs.getInt("SUPERVISOR"));
				e.setDepartmentNum(rs.getInt("DEPARTMENT"));
				return e;
			}
			return new Employee(0);
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return null;
		
		
	}

	@Override
	public List<Employee> getAllEmployees() {
		List<Employee> employees = new ArrayList<Employee>();
		try {
			String sql = "SELECT * FROM employees";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Employee e = new Employee();
				e.setId(rs.getInt("ID"));
				e.setEmail(rs.getString("EMAIL"));
				e.setPassword(rs.getString("PASSWORD"));
				e.setFirstName(rs.getString("FIRST_NAME"));
				e.setLastName(rs.getString("LAST_NAME"));
				e.setMiddleName(rs.getString("MIDDLE_NAME"));
				e.setJobTitle(rs.getString("JOB_TITLE"));
				e.setSupervisorId(rs.getInt("SUPERVISOR"));
				e.setDepartmentNum(rs.getInt("DEPARTMENT"));
				employees.add(e);
			}
			
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return employees;
	}

	@Override
	public boolean updateEmployee(Employee e) {
		try {
			String sql = "UPDATE employees SET "
					+ "			email = ?, password = ?, first_name = ?, "
					+ "			last_name = ?, middle_name = ?, job_title = ?, supervisor = ?, department = ?"
					+ "			WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, e.getEmail());
			ps.setString(2, e.getPassword());
			ps.setString(3, e.getFirstName());
			ps.setString(4, e.getLastName());
			ps.setString(5, e.getMiddleName());
			ps.setString(6, e.getJobTitle());
			ps.setString(7, Integer.toString(e.getSupervisorId()));
			ps.setString(8, Integer.toString(e.getDepartmentNum()));
			ps.setString(9, Integer.toString(e.getId()));
			ps.executeQuery();
			return true;
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteEmployee(int id) {
		try {
			String sql = "DELETE FROM employees WHERE id = ?";

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeQuery();
			return true;
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
