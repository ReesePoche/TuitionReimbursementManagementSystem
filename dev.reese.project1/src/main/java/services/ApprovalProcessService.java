package services;

import daos.DBImpEmployeeDAO;
import daos.DBImpRequestNoteDAO;
import daos.DBImpTuitionReimbursementRequestDAO;
import daos.EmployeeDAO;
import daos.RequestNoteDAO;
import daos.TuitionReimbursementRequestDAO;

public class ApprovalProcessService {
	
	private TuitionReimbursementRequestDAO trrDAO = new DBImpTuitionReimbursementRequestDAO();
	private RequestNoteDAO rnDAO = new DBImpRequestNoteDAO();
	private EmployeeDAO eDAO = new DBImpEmployeeDAO();
	
	
	
	
	

}
