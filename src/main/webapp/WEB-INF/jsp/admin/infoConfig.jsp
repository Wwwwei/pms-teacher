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
<script type="text/javascript" src="js/common/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="js/ajaxfileupload.js"></script>
<script type="text/javascript" src="js/common/bootstrap.min.js"></script>
<script type="text/javascript" src="js/softwareCopyright.js"></script>
<script type="text/javascript" src="js/admin/insertOneTeacher.js"></script>
<link rel="stylesheet" href="css/bootstrap.min.css" />
<link rel="stylesheet" href="css/pms.css" />
<script type="text/javascript">
</script>
<body>

<!--导航条开始-->
<jsp:include page="head.jsp"/>

<div class="container" style="margin-top: 5%;min-height:450px">
	<div class="row">
		<table class="table" style="border-color: #FFFFFF">
			<tr>
				<td width="20%">
			<jsp:include page="left.jsp"><jsp:param value="3" name="location" /></jsp:include>
				</td>
				<td>
			<blockquote>
				<span> 配置信息 </span>
			</blockquote>
<c:if test="${requestScope.type=='institute'}">
	<div class="col-lg-12">&nbsp;</div>
		<div class="col-lg-3">
		   <label>部门名称</label>
		</div>
		<div class="col-lg-3">
		   <label>部门信息</label>
		</div>
		<div class="col-lg-3">
		   <label>部门领导</label>
		</div>
		   <div class="col-lg-1">
		    <label>操作</label>
		</div>
		<div class="col-lg-12">&nbsp;</div>
	<c:forEach var="institute" begin="0" step="1" items="${requestScope.institutes}">
	<c:if test="${institute.institute_name!='无'}">
	<form id="updateInstitute" action="admin/updateInstitute.do" method="post" class="form-horizontal">
		<input id="institute_id" name="institute_id" type="hidden" value="${institute.institute_id}">
		<div class="col-lg-3">
			<input id="institute_name" name="institute_name" width="100" value="${institute.institute_name}">
		</div>
		<div class="col-lg-3">
			<input id="institute_info" name="institute_info" width="100" value="${institute.institute_info}">
		</div>
		<div class="col-lg-3">
			<input id="institute_leader" name="institute_leader" width="100" value="${institute.institute_leader}">
		</div>
		<div class="col-lg-2">
				<input type="submit" class="btn bnt-button"  value=" 修改">				
			</form>
			<a id="a2" href="admin/deleteInstitute.do?institute_id=${institute.institute_id}" class="btn bnt-button">删除</a>
		</div>
		</c:if>
		<div class="col-lg-12">&nbsp;</div>
	</c:forEach>
	<div class="col-lg-3 col-lg-offset-3">
	<a id="a3" class='btn btn-primary' href="admin/addMessagePage.do?type=${requestScope.type}">录入</a> </span>
	</div>			
	</c:if>
	<c:if test="${requestScope.type=='profession'}">
	<div class="col-lg-12">&nbsp;</div>
		<div class="col-lg-3">
		   <label>专业名称</label>
		</div>
		<div class="col-lg-3">
		   <label>专业信息</label>
		</div>
		   <div class="col-lg-1">
		    <label>操作</label>
		</div>
		<div class="col-lg-12">&nbsp;</div>
	<c:forEach var="teachingProfession" begin="0" step="1" items="${requestScope.teachingProfession}">
	<c:if test="${teachingProfession.teachingProfession_name!='无'}">
	<form id="updateProfession" action="admin/updateProfession.do" method="post" class="form-horizontal">
		<input id="teachingProfession_id" name="teachingProfession_id" type="hidden" value="${teachingProfession.teachingProfession_id}">
		<div class="col-lg-3">
			<input id="teachingProfession_name" name="teachingProfession_name" width="100" value="${teachingProfession.teachingProfession_name}">
		</div>
		<div class="col-lg-3">
			<input id="teachingProfession_info" name="teachingProfession_info" width="100" value="${teachingProfession.teachingProfession_info}">
		</div>
		<div class="col-lg-2">
				<input type="submit" class="btn bnt-button"  value=" 修改">				
			</form>
			<a id="a2" href="admin/deleteProfession.do?teachingProfession_id=${teachingProfession.teachingProfession_id}" class="btn bnt-button">删除</a>
		</div>
		</c:if>
		<div class="col-lg-12">&nbsp;</div>
	</c:forEach>
	<div class="col-lg-3 col-lg-offset-3">
	<a id="a3" class='btn btn-primary' href="admin/addMessagePage.do?type=${requestScope.type}">录入</a> </span>
	</div>			
	</c:if>
	<c:if test="${requestScope.type=='subject'}">
	<div class="col-lg-12">&nbsp;</div>
		<div class="col-lg-3">
		   <label>学科名称</label>
		</div>
		<div class="col-lg-3">
		   <label>学科信息</label>
		</div>
		   <div class="col-lg-1">
		    <label>操作</label>
		</div>
		<div class="col-lg-12">&nbsp;</div>
	<c:forEach var="subject" begin="0" step="1" items="${requestScope.subject}">
	<c:if test="${subject.subject_name!='无'}">
	<form id="updateSubject" action="admin/updateSubject.do" method="post" class="form-horizontal">
		<input id="subject_id" name="subject_id" type="hidden" value="${subject.subject_id}">
		<div class="col-lg-3">
			<input id="subject_name" name="subject_name" width="100" value="${subject.subject_name}">
		</div>
		<div class="col-lg-3">
			<input id="subject_info" name="subject_info" width="100" value="${subject.subject_info}">
		</div>
		<div class="col-lg-2">
				<input type="submit" class="btn bnt-button"  value=" 修改">				
			</form>
			<a id="a2" href="admin/deleteSubject.do?subject_id=${subject.subject_id}" class="btn bnt-button">删除</a>
		</div>
		</c:if>
		<div class="col-lg-12">&nbsp;</div>
	</c:forEach>
	<div class="col-lg-3 col-lg-offset-3">
	<a id="a3" class='btn btn-primary' href="admin/addMessagePage.do?type=${requestScope.type}">录入</a> </span>
	</div>			
	</c:if>
	<c:if test="${requestScope.type=='title'}">
	<div class="col-lg-12">&nbsp;</div>
		<div class="col-lg-3">
		   <label>职称名称</label>
		</div>
		<div class="col-lg-3">
		   <label>职称信息</label>
		</div>
		   <div class="col-lg-1">
		    <label>操作</label>
		</div>
		<div class="col-lg-12">&nbsp;</div>
	<c:forEach var="title" begin="0" step="1" items="${requestScope.title}">
	<c:if test="${title.title_name!='无'}">
	<form id="updateTitle" action="admin/updateTitle.do" method="post" class="form-horizontal">
		<input id="title_id" name="title_id" type="hidden" value="${title.title_id}">
		<div class="col-lg-3">
			<input id="title_name" name="title_name" width="100" value="${title.title_name}">
		</div>
		<div class="col-lg-3">
			<input id="title_info" name="title_info" width="100" value="${title.title_info}">
		</div>
		<div class="col-lg-2">
				<input type="submit" class="btn bnt-button"  value=" 修改">				
			</form>
			<a id="a2" href="admin/deleteTitle.do?title_id=${title.title_id}" class="btn bnt-button">删除</a>
		</div>
		</c:if>
		<div class="col-lg-12">&nbsp;</div>
	</c:forEach>
	<div class="col-lg-3 col-lg-offset-3">
	<a id="a3" class='btn btn-primary' href="admin/addMessagePage.do?type=${requestScope.type}">录入</a> </span>
	</div>			
	</c:if>
		</table>
	</div>

</div>
<jsp:include page="../../../layout/footer.jsp" />
</body>
</html>