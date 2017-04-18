package com.pms.service;

import com.pms.entity.AuthorProxy;
import com.pms.entity.Institute;
import com.pms.entity.Teacher;

import java.util.List;

public interface InstituteService {

	public Institute getInstitue(String institute_name);

	public List<Institute> getAllInstitute();
	
	public boolean insertInstitute(Institute institute);
	
	public boolean updateInstitute(Institute institute);
	
	public boolean deleteInstitute(int institute_id);


}
