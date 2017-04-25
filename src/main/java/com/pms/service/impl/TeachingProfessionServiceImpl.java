package com.pms.service.impl;

import com.pms.entity.Institute;
import com.pms.entity.TeachingProfession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pms.dao.TeacherMapper;
import com.pms.dao.TeachingProfessionMapper;
import com.pms.service.TeachingProfessionService;

import java.util.List;

@Service
public class TeachingProfessionServiceImpl implements TeachingProfessionService {

	@Autowired
	private TeachingProfessionMapper teachingProfessionMapper;
	
	@Autowired
	private TeacherMapper teacherMapper;

	@Override
	public TeachingProfession getTeachingProfession(String teachingProfession_name) {
		return teachingProfessionMapper.findTeachingProfessionByName(teachingProfession_name);
	}

	@Override
	public List<TeachingProfession> getAllTeachingProfession()
	{
		return teachingProfessionMapper.getAllTeachingProfession();
	}
	@Override
	public boolean insertProfession(TeachingProfession teachingProfession) {
		// TODO Auto-generated method stub
		if(teachingProfessionMapper.insertProfession(teachingProfession)!=0)
			return true;
		return false;
	}
	
	@Override
	public boolean updateProfession(TeachingProfession teachingProfession) {
		// TODO Auto-generated method stub
		if (teachingProfessionMapper.updateProfession(teachingProfession) == 1)
			return true;
		return false;
	}
	public boolean deleteProfession(int teachingProfession_id) {
		if (teachingProfessionMapper.deleteProfession(teachingProfession_id)!=0)
		{
			teacherMapper.updateTeacherProfession(teachingProfession_id);
			return true;
		}
		return false;
	}



}
