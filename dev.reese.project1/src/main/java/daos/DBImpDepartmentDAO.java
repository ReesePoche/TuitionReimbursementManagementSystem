package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dev.reese.project1.entities.Department;
import dev.reese.project1.util.JDBCConnection;

public class DBImpDepartmentDAO implements DepartmentDAO{
	
	public static Connection conn = JDBCConnection.getConnection();

	@Override
	public boolean createDepartment(Department d) {
		try {
			String sql = "INSERT INTO departments "
					+ "			(department_name, head_of_department)"
					+ "			VALUES (?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, d.getDepartmentName());
			ps.setString(2, Integer.toString(d.getHeadOfDepartmentId()));
			ps.executeQuery();
			return true;
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return false;
	}

	@Override
	public Department getDepartment(String name) {
		try {
			String sql = "SELECT * FROM departments WHERE department_name = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				Department d = new Department();
				d.setId(rs.getInt("ID"));
				d.setDepartmentName(rs.getString("DEPARTMENT_NAME"));
				d.setHeadOfDepartmentId(rs.getInt("HEAD_OF_DEPARTMENT"));
				return d;
			}
			return new Department(0);
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return null;
	}

	@Override
	public Department getDepartment(int id) {
		try {
			String sql = "SELECT * FROM departments WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(id));
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				Department d = new Department();
				d.setId(rs.getInt("ID"));
				d.setDepartmentName(rs.getString("DEPARTMENT_NAME"));
				d.setHeadOfDepartmentId(rs.getInt("HEAD_OF_DEPARTMENT"));
				return d;
			}
			return new Department(0);
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Department> getAllDepartments() {
		List<Department> departments = new ArrayList<Department>();
		try {
			String sql = "SELECT * FROM departments ";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Department d = new Department();
				d.setId(rs.getInt("ID"));
				d.setDepartmentName(rs.getString("DEPARTMENT_NAME"));
				d.setHeadOfDepartmentId(rs.getInt("HEAD_OF_DEPARTMENT"));
				departments.add(d);
			}
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return departments;
	}

	@Override
	public boolean updateDepartment(Department d) {
		try {
			String sql = "UPDATE departments SET "
					+ "			department_name = ?, head_of_department = ?"
					+ "			WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, d.getDepartmentName());
			ps.setString(2, Integer.toString(d.getHeadOfDepartmentId()));
			ps.setString(3, Integer.toString(d.getId()));
			ps.executeQuery();
			return true;
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteDepartment(int id) {
		try {
			String sql = "DELETE FROM departments WHERE id = ?";

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
