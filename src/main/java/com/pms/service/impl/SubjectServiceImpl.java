package com.pms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pms.dao.SubjectMapper;
import com.pms.dao.TeacherMapper;
import com.pms.dao.TeachingProfessionMapper;
import com.pms.entity.Subject;
import com.pms.entity.Subject;
import com.pms.entity.TeachingProfession;
import com.pms.service.SubjectService;
@Service
public class SubjectServiceImpl implements SubjectService{
	@Autowired
	private SubjectMapper subjectMapper;
	@Autowired
	private TeacherMapper teacherMapper;
	
	@Override
	public List<Subject> getAllSubject()
	{
		return subjectMapper.getAllSubject();
	}
	@Override
	public Subject getSubject(String subject_name) {
		return subjectMapper.findSubjectByName(subject_name);
	}
	@Override
	public boolean insertSubject(Subject subject) {
		// TODO Auto-generated method stub
		if(subjectMapper.insertSubject(subject)!=0)
			return true;
		return false;
	}
	
	@Override
	public boolean updateSubject(Subject subject) {
		// TODO Auto-generated method stub
		if (subjectMapper.updateSubject(subject) == 1)
			return true;
		return false;
	}
	public boolean deleteSubject(int subject_id) {
		if (teacherMapper.updateTeacherSubject(subject_id)!=0&&subjectMapper.deleteSubject(subject_id)!=0)
			return true;
		return false;
	}

}
