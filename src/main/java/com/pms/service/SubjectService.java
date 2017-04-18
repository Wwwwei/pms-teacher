package com.pms.service;

import java.util.List;

import com.pms.entity.Subject;
import com.pms.entity.TeachingProfession;

public interface SubjectService {
	
	public List<Subject> getAllSubject();
	
	public Subject getSubject(String subject_name);
	
    public boolean insertSubject(Subject subject);
		
	public boolean updateSubject(Subject subject);
		
	public boolean deleteSubject(int subject_id);

}
