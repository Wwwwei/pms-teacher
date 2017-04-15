package com.pms.service;

import com.pms.entity.TeachingProfession;

import java.util.List;

public interface TeachingProfessionService {
	public TeachingProfession getTeachingProfession(String teachingProfession_name);

	public List<TeachingProfession> getAllTeachingProfession();

}
