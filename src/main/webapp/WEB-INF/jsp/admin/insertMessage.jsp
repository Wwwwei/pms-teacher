<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<base href="<%=basePath%>">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
</head>
<script type="text/javascript">
</script>
<script type="text/javascript" src="js/common/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="js/common/bootstrap.min.js"></script>
<script type="text/javascript" src="js/softwareCopyright.js"></script>
<script type="text/javascript" src="js/admin/insertOneTeacher.js"></script>
<script type="text/javascript" src="js/admin/insertMessage.js"></script>
<link rel="stylesheet" href="css/bootstrap.min.css" />
<link rel="stylesheet" href="css/pms.css" />
<body>
<jsp:include page="head.jsp"/>
<div class="container" style="margin-top: 5%;min-height:340px">
	<div class="row">
		<table class="table" style="border-color: #FFFFFF">
			<tr>
			<td width="20%">
				<jsp:include page="left.jsp"><jsp:param value="2" name="location" /></jsp:include>
			</td>
				<td>
		<%--<div class="col-lg-10">--%>
			<blockquote>
				<span> 信息录入 </span>
			</blockquote>
			<c:if test="${requestScope.type=='institute'}">
			<form id="insertInstitute" action="admin/insertInstitute.do" method="post" onSubmit="return check1()" class="form-horizontal">
					<div class="col-lg-12">&nbsp;</div>
				<div class="col-lg-2"><label>部门名称：</label></div>
				<div class="col-lg-4"><input type="text" name="institute_name" id="institute_name" class="form-control"></div>
				<div class="col-lg-12">&nbsp;</div>

				<div class="col-lg-2"><label>部门信息：</label></div>
				<div class="col-lg-4"><input type="text" name="institute_info" id="institute_info" class="form-control"></div>

				<div class="col-lg-12">&nbsp;</div>
				
				<div class="col-lg-2"><label>部门领导：</label></div>
				<div class="col-lg-4"><input type="text" name="institute_leader" id="institute_leader" class="form-control"></div>
                <div class="col-lg-12">&nbsp;</div>
				
					<div class="col-lg-2 col-lg-offset-2">
						<input type="submit" class="btn btn-primary" value="确认录入">
					</div>

			</form>
			</c:if>
			<c:if test="${requestScope.type=='profession'}">
			<form id="insertProfession" action="admin/insertProfession.do" method="post" onSubmit="return check2()"class="form-horizontal">
					<div class="col-lg-12">&nbsp;</div>
				<div class="col-lg-2"><label>专业名称：</label></div>
				<div class="col-lg-4"><input type="text" name="teachingProfession_name" id="teachingProfession_name" class="form-control"></div>
				<div class="col-lg-12">&nbsp;</div>

				<div class="col-lg-2"><label>专业信息：</label></div>
				<div class="col-lg-4"><input type="text" name="teachingProfession_info" id="teachingProfession_info" class="form-control"></div>

				<div class="col-lg-12">&nbsp;</div>
				
					<div class="col-lg-2 col-lg-offset-2">
						<input type="submit" class="btn btn-primary" value="确认录入">
					</div>

			</form>
			</c:if>
			<c:if test="${requestScope.type=='subject'}">
			<form id="insertSubject" action="admin/insertSubject.do" method="post" onSubmit="return check3()" class="form-horizontal">
					<div class="col-lg-12">&nbsp;</div>
				<div class="col-lg-2"><label>学科名称：</label></div>
				<div class="col-lg-4"><input type="text" name="subject_name" id="subject_name" class="form-control"></div>
				<div class="col-lg-12">&nbsp;</div>

				<div class="col-lg-2"><label>学科信息：</label></div>
				<div class="col-lg-4"><input type="text" name="subject_info" id="subject_info"  class="form-control"></div>

				<div class="col-lg-12">&nbsp;</div>
				
					<div class="col-lg-2 col-lg-offset-2">
						<input type="submit" class="btn btn-primary" value="确认录入">
					</div>

			</form>
			</c:if>
			<c:if test="${requestScope.type=='title'}">
			<form id="insertTitle" action="admin/insertTitle.do" method="post" onSubmit="return check4()" class="form-horizontal">
					<div class="col-lg-12">&nbsp;</div>
				<div class="col-lg-2"><label>职称名称：</label></div>
				<div class="col-lg-4"><input type="text" name="title_name" id="title_name" class="form-control"></div>
				<div class="col-lg-12">&nbsp;</div>

				<div class="col-lg-2"><label>职称信息：</label></div>
				<div class="col-lg-4"><input type="text" name="title_info" id="title_info"  class="form-control"></div>

				<div class="col-lg-12">&nbsp;</div>
				
					<div class="col-lg-2 col-lg-offset-2">
						<input type="submit" class="btn btn-primary" value="确认录入">
					</div>

			</form>
			</c:if>
			<div class="col-lg-12">&nbsp;</div>

				</td>
			</tr>
		</table>
		</div>
</div>

<jsp:include page="../../../layout/footer.jsp" />
</body>
</html>