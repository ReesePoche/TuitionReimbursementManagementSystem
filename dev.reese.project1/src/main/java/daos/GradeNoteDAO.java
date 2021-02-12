package daos;

import java.util.List;

import dev.reese.project1.entities.GradeNote;

public interface GradeNoteDAO {
	
	public boolean createGradeNote(GradeNote gn);
	
	public GradeNote getGradeNote(int id);
	
	public GradeNote getGradeNote(int grade_submittion_id, java.sql.Timestamp time);
	
	public List<GradeNote> getAllGradeNotes();
	
	public List<GradeNote> getAllNotesForGrade(int gradeId);
	
	public boolean updateGradeNote(GradeNote gn);
	
	public boolean deleteGradeNote(int id);
	
	

}
