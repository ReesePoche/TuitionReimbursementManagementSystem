package daos;

import java.util.List;

import dev.reese.project1.entities.RequestNote;

public interface RequestNoteDAO {
	
	public boolean createRequestNote(RequestNote rn);
	
	public RequestNote getRequestNote(int id);
	
	public RequestNote getRequestNote(int requestId, int addedBy, java.sql.Timestamp time);
	
	public List<RequestNote> getAllRequestNote();
	
	public List<RequestNote> getAllRequestsRequestNote(int requestId);
	
	public boolean updateRequestNote(RequestNote rn);
	
	public boolean deleteRequestNote(int id);
	

}
