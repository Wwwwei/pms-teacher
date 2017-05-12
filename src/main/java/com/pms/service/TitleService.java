package com.pms.service;

import java.util.List;

import com.pms.entity.Title;
public interface TitleService {
	
	public List<Title> getAllTitle();
	
	public Title getTitle(String title_name);
	
    public boolean insertTitle(Title title);
		
	public boolean updateTitle(Title title);
		
	public boolean deleteTitle(int title_id);

}
