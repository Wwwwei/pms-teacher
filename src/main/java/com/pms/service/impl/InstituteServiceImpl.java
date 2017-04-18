package com.pms.service.impl;

import com.pms.dao.InstituteMapper;
import com.pms.dao.TeacherMapper;
import com.pms.service.InstituteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pms.entity.AuthorProxy;
import com.pms.entity.Institute;
import com.pms.entity.Teacher;

import java.util.List;

@Service
public class InstituteServiceImpl implements InstituteService {

	@Autowired
	private InstituteMapper instituteMapper;
	@Autowired
	private TeacherMapper teacherMapper;


	@Override
	public Institute getInstitue(String institute_name) {
		// TODO Auto-generated method stub
		return instituteMapper.findInstitueByName(institute_name);
	}


	@Override
	public List<Institute> getAllInstitute() {
		// TODO Auto-generated method stub
		System.out.println("-------");
		return instituteMapper.findAllInstitute();
	}
	
	@Override
	public boolean insertInstitute(Institute institute) {
		// TODO Auto-generated method stub
		if(instituteMapper.insertInstitute(institute)!=0)
			return true;
		return false;
	}
	
	@Override
	public boolean updateInstitute(Institute institute) {
		// TODO Auto-generated method stub
		if (instituteMapper.updateInstitute(institute) == 1)
			return true;
		return false;
	}
	public boolean deleteInstitute(int institute_id) {
		if (teacherMapper.updateTeacherInstitute(institute_id)!=0&&instituteMapper.deleteInstitute(institute_id)!=0)
			return true;
		return false;
	}

}
