package com.pms.service.impl;

import com.pms.dao.InstituteMapper;
import com.pms.service.InstituteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pms.entity.Institute;

import java.util.List;

@Service
public class InstituteServiceImpl implements InstituteService {

	@Autowired
	private InstituteMapper instituteMapper;


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

}
