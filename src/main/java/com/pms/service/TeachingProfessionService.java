package com.pms.service;

import com.pms.entity.Institute;
import com.pms.entity.TeachingProfession;

import java.util.List;

public interface TeachingProfessionService {
	public TeachingProfession getTeachingProfession(String teachingProfession_name);

	public List<TeachingProfession> getAllTeachingProfession();
	
    public boolean insertProfession(TeachingProfession teachingProfession);
	
	public boolean updateProfession(TeachingProfession teachingProfession);
	
	public boolean deleteProfession(int teachingProfession_id);

}
