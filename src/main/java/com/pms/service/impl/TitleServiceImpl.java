package com.pms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pms.dao.TitleMapper;
import com.pms.dao.TeacherMapper;
import com.pms.dao.TeachingProfessionMapper;
import com.pms.entity.Title;
import com.pms.entity.Title;
import com.pms.entity.TeachingProfession;
import com.pms.service.TitleService;
@Service
public class TitleServiceImpl implements TitleService{
	@Autowired
	private TitleMapper TitleMapper;
	@Autowired
	private TeacherMapper teacherMapper;
	
	@Override
	public List<Title> getAllTitle()
	{
		return TitleMapper.getAllTitle();
	}
	public Title getTitle(String title_name) {
		return TitleMapper.findTitleByName(title_name);
	}
	@Override
	public boolean insertTitle(Title title) {
		// TODO Auto-generated method stub
		if(TitleMapper.insertTitle(title)!=0)
			return true;
		return false;
	}
	
	@Override
	public boolean updateTitle(Title title) {
		// TODO Auto-generated method stub
		if (TitleMapper.updateTitle(title) == 1)
			return true;
		return false;
	}
	public boolean deleteTitle(int title_id) {
		if (TitleMapper.deleteTitle(title_id)!=0)
		{
			teacherMapper.updateTeacherTitle(title_id);
			return true;
		}
		return false;
	}

}
