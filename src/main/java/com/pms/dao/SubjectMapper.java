package com.pms.dao;

import java.util.List;

import com.pms.entity.Institute;
import com.pms.entity.Subject;
public interface SubjectMapper {
	public List<Subject> getAllSubject();
	
	public Subject findSubjectByName(String subject_name);
	
    public int insertSubject(Subject subject);
	
	public int updateSubject(Subject subject);
	
	public int deleteSubject(int subject_id);

}
