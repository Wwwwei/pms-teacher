package com.pms.dao;

import com.pms.entity.Institute;

import java.util.List;

public interface InstituteMapper {
	public Institute findInstitueByName(String institute_name);
	
	public List<Institute> findAllInstitute();

}
