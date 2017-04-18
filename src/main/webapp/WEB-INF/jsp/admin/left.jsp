<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link rel="stylesheet" href="css/pms.css" />
<body>
	<%
		String location = request.getParameter("location");
	%>
	<ul class="nav nav-pills nav-stacked" data-spy="affix">
		<%
			if (location.equals("1")) {
		%>
		<li><a href="admin/updateTeacherInfo.do">查询教师信息</a></li>
		<li class="active"><a href="admin/insertOneTeacher.do">录入教师信息</a></li>
		<li class="active"><a href="admin/insertNTeacher.do">批量录入</a></li>
		<li><a href="admin/insertOneTeacher.do">单个录入</a></li>
		<div class="btn-group">
			<button type="button" class="btn btn-pri dropdown-toggle"
				data-toggle="dropdown">
				信息配置<span class="caret"></span>
			</button>
			<ul class="dropdown-menu" role="menu">
				<li><a href="admin/findInstitute.do?">研究院信息</a></li>
				<li><a href="admin/findProfession.do?">专业信息</a></li>
				<li><a href="admin/findSubject.do?">学科信息</a></li>
			</ul>
		</div>


		<%
			} else if (location.equals("2")) {
		%>
		<li><a href="admin/updateTeacherInfo.do">查询教师信息</a></li>
		<li class="active"><a href="admin/insertOneTeacher.do">录入教师信息</a></li>
		<li><a href="admin/insertNTeacher.do">批量录入</a></li>
		<li class="active"><a href="admin/insertOneTeacher.do">单个录入</a></li>
		<div class="btn-group">
			<button type="button" class="btn btn-pri dropdown-toggle"
				data-toggle="dropdown">
				信息配置<span class="caret"></span>
			</button>
			<ul class="dropdown-menu" role="menu">
				<li><a href="admin/findInstitute.do?">研究院信息</a></li>
				<li><a href="admin/findProfession.do?">专业信息</a></li>
				<li><a href="admin/findSubject.do?">学科信息</a></li>
			</ul>
		</div>


		<%
			} else if (location.equals("3")) {
		%>
		<li class="active"><a href="admin/updateTeacherInfo.do">查询教师信息</a></li>
		<li><a href="admin/insertOneTeacher.do">录入教师信息</a></li>
		<li><a href="admin/insertNTeacher.do">批量录入</a></li>
		<li><a href="admin/insertOneTeacher.do">单个录入</a></li>
		<div class="btn-group">
			<button type="button"  class="btn btn-pri dropdown-toggle"
				data-toggle="dropdown">
				信息配置<span class="caret"></span>
			</button>
			<ul class="dropdown-menu" role="menu">
			<li><a href="admin/findInstitute.do?">研究院信息</a></li>
				<li><a href="admin/findProfession.do?">专业信息</a></li>
				<li><a href="admin/findSubject.do?">学科信息</a></li>
			</ul>
		</div>
		<%
			}
		%>
	</ul>

</body>
</html>