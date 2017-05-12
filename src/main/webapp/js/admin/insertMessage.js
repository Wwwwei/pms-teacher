function check1()
{
	var institute_name=$("[name=institute_name]").val();
	if(institute_name=="")
	{
		alert("研究院名称不能为空！")
		return false;
	}
	var institute_info=$("[name=institute_info]").val();
	if(institute_info=="")
	{
		alert("研究院信息不能为空！")
		return false;
	}
	var institute_leader=$("[name=institute_leader]").val();
	if(institute_leader=="")
	{
		alert("研究院领导不能为空！")
		return false;
	}
	return true;
}
function check2()
{
	var teachingProfession_name=$("[name=teachingProfession_name]").val();
	if(teachingProfession_name=="")
	{
		alert("专业名称不能为空！")
		return false;
	}
	var teachingProfession_info=$("[name=teachingProfession_info]").val();
	if(teachingProfession_info=="")
	{
		alert("专业信息不能为空！")
		return false;
	}
	return true;
}
function check3()
{
	var subject_name=$("[name=subject_name]").val();
	if(subject_name=="")
	{
		alert("学科名称不能为空！")
		return false;
	}
	var subject_info=$("[name=subject_info]").val();
	if(subject_info=="")
	{
		alert("学科信息不能为空！")
		return false;
	}
	
	return true;
}
function check4()
{
	var title_name=$("[name=title_name]").val();
	if(title_name=="")
	{
		alert("职称名称不能为空！")
		return false;
	}
	var title_info=$("[name=title_info]").val();
	if(title_info=="")
	{
		alert("职称信息不能为空！")
		return false;
	}
	
	return true;
}