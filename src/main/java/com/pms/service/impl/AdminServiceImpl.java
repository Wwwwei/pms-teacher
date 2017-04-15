package com.pms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pms.dao.AdminMapper;
import com.pms.entity.Admin;
import com.pms.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminMapper adminMapper;

	@Override
	public boolean resetPassword(String teacher_no,int teacher_id) {
		// TODO Auto-generated method stub
		int flag=adminMapper.resetPassword(teacher_no);
		if(flag==1)
			return true;
		return false;
	}

	@Override
	public Admin login(Admin admin) {
		// TODO Auto-generated method stub
		return adminMapper.findAdminByLogin(admin);
	}

	
	

}
