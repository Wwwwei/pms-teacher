package com.pms.dao;

import com.pms.entity.AuthorProxy;
import com.pms.entity.Institute;
import com.pms.entity.Teacher;

import java.util.List;

public interface InstituteMapper {
	public Institute findInstitueByName(String institute_name);
	
	public List<Institute> findAllInstitute();
	
	public int insertInstitute(Institute institute);
	
	public int updateInstitute(Institute institute);
	
	public int deleteInstitute(int institute_id);

}
