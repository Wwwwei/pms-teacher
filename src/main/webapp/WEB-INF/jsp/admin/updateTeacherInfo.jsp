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
	$(function() {
		$("#resetPassword").click(function() {
			var teacher_no = $("#teacher_no").val();
			var teacher_id = $("#teacher_id").val();
			$.ajax({
				url : "admin/resetPassword.do",
				type : 'POST',
				data : {
					teacher_id : teacher_id,
					teacher_no : teacher_no
				},
				datatype : "json",
				success : function(result) {
					if (result == "success") {
						alert("重置密码成功！")
					}
					else if(result == "admin_not_login"){
						window.location.href="login.jsp";
					}
// 					else {
// 						alert("重置密码失败，请重新尝试！");
// 					}
				},
				error : function(data) {
					alert("网络错误，请重新尝试！");
				}
			});
		});		

		var teacher_sex_value = "${teacher.teacher_sex}";
		$("#teacher_sex").val(teacher_sex_value);
		
		var teacher_institute_id_value = "${teacher.teacher_institute.institute_id}";
		$("#teacher_institute").val(teacher_institute_id_value);

		var teacher_teachingProfession_id_value = "${teacher.teacher_teachingProfession.teachingProfession_id}";
		$("#teacher_teachingProfession").val(teacher_teachingProfession_id_value);
		
		var teacher_belong_subject_id_value = "${teacher.teacher_belong_subject.subject_id}";
		$("#teacher_belong_subject").val(teacher_belong_subject_id_value);

		var teacher_title_id_value = "${teacher.teacher_title.title_id}";
		$("#teacher_title").val(teacher_title_id_value);

		var updateTeacherResult_value = "${updateTeacherResult}";
		if (updateTeacherResult_value != "")
			alert(updateTeacherResult_value);
	});
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
            <span> 查询结果  </span>
            <span style="margin-right:100px;">		</span>
            <input id="exportLocation" type="hidden" name="exportLocation" value=""/>
            <c:if test="${requestScope.teacher==null}">
            <span><a href="exportTeacher.do">导出教师信息</a></span>
            </c:if>
        </blockquote>
        <form action="admin/findTeacherInfo.do" method="post" class="form-inline">
				<div class="col-lg-6">
					<div class="input-group" style="width:100%" style="hight:100%">
						<input type="text" name="find_string" value="${find_string}" class="form-control" placeholder="请输入教师人事工号或姓名">
	      					<span class="input-group-btn">
	        					<button type="submit" class="btn btn-primary"style="font-size: 22px"><span class="glyphicon glyphicon-search"></span></button>
	      					</span>
					</div><!-- /input-group -->
				</div>
			</form>
			<br><br><br>
			<c:if test="${requestScope.teacher==null&&requestScope.isFindTeacher == true}">
				<div class="col-lg-12">&nbsp;</div>
				<div class="col-lg-6">
					<div class="alert alert-danger alert-dismissible" role="alert">
						<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
						没有该教师信息，请检查输入！
					</div>
				</div>
			</c:if>
		<c:if test="${requestScope.teacher==null}">
        <div class="table-responsive">
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th width="50px">编号</th>
                    <th width="100px">教师姓名</th>
                    <th width="100px">人事工号</th>
                    <th width="100px">财务工号</th>
                    <th width="50px">性别</th>
                    <th width="50px">职称</th>
                    <th width="120px">所属部门</th>
                    <th width="100px">所属学科</th>
                    <th width="100px">教学专业</th>
                    <th>功能</th>
                </tr>
                </thead>
                <c:forEach var="teacher" begin="0" end="10" step="1"
                            items="${teachers}" varStatus="status">
                     <tr> 
                        <td>${status.count+(page.currentPage-1)*page.pageNumber}</td> 

                       <td>${teacher.teacher_name}</td> 
                       <td>${teacher.teacher_no}</td> 
                       <td>${teacher.teacher_finance_no}</td> 
                       <td>
                           <c:if test="${teacher.teacher_sex == 0}"> 女 </c:if>
                           <c:if test="${teacher.teacher_sex == 1}"> 男</c:if>
                       </td>
                       <td>${teacher.teacher_title.title_name}</td> 
                       <td>${teacher.teacher_institute.institute_name}</td>
                       <td>${teacher.teacher_belong_subject.subject_name}</td> 
                       <td>${teacher.teacher_teachingProfession.teachingProfession_name}</td>
                       <td><a href="admin/TeacherInfo.do?teacher_no=${teacher.teacher_no}"> 修改</a>
                           <a href="admin/deleteTeacher.do?teacher_id=${teacher.teacher_id}"> 删除</a></td>
                </c:forEach>
            </table>
       
        <!--查询结果显示结束-->
        <!--分页开始-->

        <ul class="pager">
            <p class="text-primary">第 ${page.currentPage} 页，共
                ${page.totalPage} 页</p>
            <li><a href="findAllTeacher.do?currentPage=1"><span
                    class="glyphicon glyphicon-step-backward"></span>首页</a></li>
            <c:choose>
                <c:when test="${page.currentPage - 1 > 0}">
                    <li><a
                            href="findAllTeacher.do?currentPage=${page.currentPage - 1}"><span
                            class="glyphicon glyphicon-triangle-left"></span>上一页</a></li>
                </c:when>
                <c:when test="${page.currentPage - 1 <= 0}">
                    <li><a><span
                            class="glyphicon glyphicon-triangle-left"></span>上一页</a></li>
                </c:when>
            </c:choose>
            <c:choose>
                <c:when test="${page.currentPage + 1 <= page.totalPage}">
                    <li><a
                            href="findAllTeacher.do?currentPage=${page.currentPage + 1}">下一页<span
                            class="glyphicon glyphicon-triangle-right"></span></a></li>
                </c:when>
                <c:when test="${page.currentPage + 1 > page.totalPage}">
                    <li><a>下一页
                        <span class="glyphicon glyphicon-triangle-right"></span>
                    </a></li>
                </c:when>
            </c:choose>
            <li><a href="findAllTeacher.do?currentPage=${page.totalPage}">尾页<span
                    class="glyphicon glyphicon-step-forward"></span></a></li>
        </ul>
        </div>
        </c:if>
			<c:if test="${requestScope.teacher!=null}">
				<form id="updateTeacher" action="admin/updateTeacher.do" onSubmit="return check()" method="post">
					<!--  <form id="updateTeacher"> -->
					<div class="col-lg-12">&nbsp;</div>
					<input type="hidden" id="teacher_id" name="teacher_id"
						   value="${teacher.teacher_id}">
					<div class="col-lg-2"><label>教师工号：</label></div>
					<div class="col-lg-4"><input type="text" name="teacher_no" id="teacher_no"
												 value="${teacher.teacher_no}" readonly="readonly"class="form-control"> </div>
					<div class="col-lg-2"><label>财务工号：</label></div>
					<div class="col-lg-4"><input type="text" name="teacher_finance_no" id="teacher_finance_no"
												 value="${teacher.teacher_finance_no}" readonly="readonly"class="form-control"> </div>

					<div class="col-lg-12">&nbsp;</div>
					<div class="col-lg-2"><label>教师姓名：</label></div>
					<div class="col-lg-4"><input type="text"
												 name="teacher_name" value="${teacher.teacher_name}" class="form-control"></div>

					<div class="col-lg-2"><label>教师性别：</label></div>
					<div class="col-lg-4"><select
							id="teacher_sex" name="teacher_sex" class="form-control">
						<option value="0">女</option>
						<option value="1">男</option>
					</select></div>
					<div class="col-lg-12">&nbsp;</div>
					<div class="col-lg-2"><label>教师职称：</label></div>
					<div class="col-lg-4">
					<select id="teacher_title" name="teacher_title.title_id" class="form-control">
						<option value="0" selected="selected">----请选择----</option>
							<c:forEach var="title" begin="0" step="1"
					         items="${title}">
							<c:if test="${title.title_id!=1}">
								<option value="${title.title_id}">${title.title_name}</option>
							</c:if>
							</c:forEach>
					</select>
					</div>


					<div class="col-lg-2"><label>出生年月：</label></div>
					<div class="col-lg-4"><input type="date" name="teacher_birth"
												 value="${teacher.teacher_birth}" min="1900-01-01" max="2019-09-26" class="form-control"/></div>
					<div class="col-lg-12">&nbsp;</div>
					<div class="col-lg-2"><label>教师手机号：</label></div>
					<div class="col-lg-4"><input type="text" name="teacher_phoneNumber"
												 id="teacher_phoneNumber" value="${teacher.teacher_phoneNumber}"class="form-control"/></div>


					<div class="col-lg-2"><label>教师办公电话：</label></div>
					<div class="col-lg-4"><input type="text" name="teacher_officeNumber"
												 id="teacher_officeNumber" value="${teacher.teacher_officeNumber}"class="form-control"/></div>
					<div class="col-lg-12">&nbsp;</div>
					<div class="col-lg-2"><label>教师QQ：</label></div>
					<div class="col-lg-4"><input type="text" name="teacher_qq" id="teacher_qq"
												 value="${teacher.teacher_qq}" class="form-control"/></div>


					<div class="col-lg-2"><label>教师身份证：</label></div>
					<div class="col-lg-4"><input type="text"
												 name="teacher_idCard" id="teacher_idCard"
												 value="${teacher.teacher_idCard}" class="form-control"/></div>
					<div class="col-lg-12">&nbsp;</div>
					<div class="col-lg-2"><label>教师邮箱：</label></div>
					<div class="col-lg-4"><input type="text"
												 name="teacher_email" value="${teacher.teacher_email}" class="form-control"/></div>


					<div class="col-lg-2"><label>教师毕业院校：</label></div>
					<div class="col-lg-4"><input type="text" name="teacher_graUniversity"
												 value="${teacher.teacher_graUniversity}" class="form-control"/></div>
					<div class="col-lg-12">&nbsp;</div>
					<div class="col-lg-2"><label>来工大时间：</label></div>
					<div class="col-lg-4"><input
							type="date" name="teacher_comeTime"
							value="${teacher.teacher_comeTime}" min="1900-01-01"
							max="2019-09-26" class="form-control"/></div>


					<div class="col-lg-2"><label>教师部门：</label></div>
					<div class="col-lg-4"><select
							id="teacher_institute" name="teacher_institute.institute_id"
							class="form-control">
						<option selected="selected" value="0">选择部门</option>
						<c:forEach var="institute" begin="0" step="1"
								   items="${requestScope.institutes}">
							<c:if test="${institute.institute_id!=0}">
							<option value="${institute.institute_id}">${institute.institute_name}</option>
							</c:if>
						</c:forEach>
					</select></div>
					<div class="col-lg-12">&nbsp;</div>
					<div class="col-lg-2"><label>所属学科：</label></div>
				   <div class="col-lg-4">
						<select id="teacher_belong_subject" name="teacher_belong_subject.subject_id" class="form-control">
							<option selected="selected" value="0">选择所属学科</option>
							<c:forEach var="Subject" begin="0" step="1"
									   items="${requestScope.subject}">
								<c:if test="${Subject.subject_id!=0}">
								<option value="${Subject.subject_id}">${Subject.subject_name}</option>
								</c:if>
							</c:forEach>
						</select>
					</div>
					<div class="col-lg-2"><label>教学科目：</label></div>
					<div class="col-lg-4"><input type="text" name="teacher_subject" value="${teacher.teacher_subject}" class="form-control"/></div>
					<div class="col-lg-12">&nbsp;</div>
					<div class="col-lg-2"><label>研究方向：</label></div>
					<div class="col-lg-4"><input type="text" name="teacher_subject_study" value="${teacher.teacher_subject_study}" class="form-control"/></div>
					<div class="col-lg-2"><label>教学专业：</label></div>
					<div class="col-lg-4">
						<select id="teacher_teachingProfession" name="teacher_teachingProfession.teachingProfession_id" class="form-control">
							<option selected="selected" value="0">选择教学专业</option>
							<c:forEach var="teachingProfession" begin="0" step="1"
									   items="${requestScope.teachingProfession}">
								<c:if test="${teachingProfession.teachingProfession_id!=0}">
								<option value="${teachingProfession.teachingProfession_id}">${teachingProfession.teachingProfession_name}</option>
								</c:if>
							</c:forEach>
						</select>
					</div>
					<div class="col-lg-12">&nbsp;</div>
					<div class="col-lg-2"><label>个人主页：</label></div>
					<div class="col-lg-4"><input type="text" name="teacher_info_url" value="${teacher.teacher_info_url}" class="form-control"/></div>
					<div class="col-lg-2"><label>谷歌个人主页：</label></div>
					<div class="col-lg-4"><input type="text" name="teacher_google_scolar_url" value="${teacher.teacher_google_scolar_url}" class="form-control"/></div>
					<div class="col-lg-12">&nbsp;</div>
					<div class="col-lg-2"><label>教师简介：</label></div>
					<div class="col-lg-10"><textarea name="teacher_info"
													 class="form-control" style="height:80px">${teacher.teacher_info}</textarea></div>
					<div class="col-lg-12">&nbsp;</div>
					<div class="col-lg-3 col-lg-offset-3">
						<input type="submit" class="btn btn-primary" value="确认修改">
					</div>
				</form>
				<div class="col-lg-3">
					<button  class="btn btn-danger" id="resetPassword">重置密码</button>
				</div>				
				<div class="col-lg-3">
					<a href="admin/deleteTeacher.do?teacher_id=${teacher.teacher_id}" class="btn btn-danger">确认删除</a>
				</div>

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