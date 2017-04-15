package com.pms.service.impl;

import com.pms.entity.TeachingProfession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pms.dao.TeachingProfessionMapper;
import com.pms.service.TeachingProfessionService;

import java.util.List;

@Service
public class TeachingProfessionServiceImpl implements TeachingProfessionService {

	@Autowired
	private TeachingProfessionMapper teachingProfessionMapper;

	@Override
	public TeachingProfession getTeachingProfession(String teachingProfession_name) {
		return teachingProfessionMapper.findTeachingProfessionByName(teachingProfession_name);
	}

	@Override
	public List<TeachingProfession> getAllTeachingProfession()
	{
		return teachingProfessionMapper.getAllTeachingProfession();
	}



}
