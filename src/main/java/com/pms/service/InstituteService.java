package com.pms.service;

import com.pms.entity.Institute;

import java.util.List;

public interface InstituteService {

	public Institute getInstitue(String institute_name);

	public List<Institute> getAllInstitute();


}
