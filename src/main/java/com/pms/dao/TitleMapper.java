package com.pms.dao;

import java.util.List;
import com.pms.entity.Title;
public interface TitleMapper {
	public List<Title> getAllTitle();
	
	public Title findTitleByName(String title_name);
	
    public int insertTitle(Title title);
	
	public int updateTitle(Title title);
	
	public int deleteTitle(int title_id);

}
