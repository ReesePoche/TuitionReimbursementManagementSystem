package daos;

import java.util.List;

import dev.reese.project1.entities.SubmittedGrade;

public interface SubmittedGradeDAO {

	public boolean createSubmittedGrade(SubmittedGrade sg);
	
	public SubmittedGrade getSubmittedGrade(int id);
	
	public SubmittedGrade getRequestsSubmittedGrade(int requestId);
	
	public List<SubmittedGrade> getAllSubmittedGrade();
	
	public boolean updateSubmittedGrade(SubmittedGrade sg);
	
	public boolean deleteSubmittedGrade(int id);
	
}
