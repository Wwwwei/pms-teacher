package com.pms.dao;


import com.pms.entity.Paper;
import com.pms.entity.Teacher;

import java.util.List;
import java.util.Map;

public interface TeacherMapper {
	
	/**
	 * 修改教师密码
	 * @param param
	 * @return
	 */
	public int updatePassword(Map<String, Object> params);
	
	/**
	 * 根据id加载teacher对象
	 * @param teacher_id
	 * @return
	 */
	public Teacher findTeacherById(int teacher_id);
	
	
	/**
	 * 查询所有老师
	 * @return  返回Teacher对象的List列表
	 */
	public List<Teacher> findAllTeacher();
	
	public int insertTeacher(Teacher teacher);
	
	public Teacher findTeacher(Map<String, Object> params);
	
	public String findTeacherSaltByTeacherNo(String teacher_no);
	
	public String findTeacherPasswordByTeacherNo(String teacher_no);
	
	public int updateTeacher(Teacher teacher);
	
	public List<String> findAllTeacherNo();
	
	public int findTeacherIdByTeacherNo(String teacher_no);
	
	public int updateTeacherInstitute(int institute_id);
	
	public int updateTeacherProfession(int teachingProfession_id);
	
	public int updateTeacherSubject(int subject_id);
	
	public int updateTeacherTitle(int title_id);
	
	public List<Teacher> findAllTeacherByPage(Map<String, Object> params);

	public List<Teacher> findAllTeacherMessage();
}
