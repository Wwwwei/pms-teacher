package com.pms.controller;

import com.pms.common.enums.PaperIssueEnum;
import com.pms.entity.*;
import com.pms.service.*;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.pms.common.enums.PaperPublishTypeEnum;
import com.pms.util.CryptoUtil;
import com.pms.util.DateUtil;

import javax.annotation.Resource;
import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
//@RequestMapping("/admin")
public class AdminHandler {
    public final static String LOGIN_ERROR_INFO = "账户或密码错误，请重新登录！";

    public final static String LOGIN_SUCCESS_INFO = "您已经登录，请勿重新登录！";

    @Resource(name = "adminServiceImpl")
    private AdminService adminService;

    @Resource(name = "paperServiceImpl")
    private PaperService paperService;

    @Resource(name = "teacherServiceImpl")
    private TeacherService teacherService;

    @Resource(name = "instituteServiceImpl")
    private InstituteService instituteService;

    @Resource(name = "authorServiceImpl")
    private AuthorService authorService;

    @Resource(name = "teachingProfessionServiceImpl")
    private TeachingProfessionService teachingProfessionService;
    
    @Resource(name = "subjectServiceImpl")
    private SubjectService subjectService;
    
    @Resource(name = "titleServiceImpl")
    private TitleService titleService;
    
    @RequestMapping(value = "/admin/login", method = RequestMethod.POST)
    public String login(Admin admin, HttpSession session, HttpServletRequest request) {
        if ((Admin) session.getAttribute("admin") != null) {
            request.setAttribute("LOGIN_SUCCESS_INFO", LOGIN_SUCCESS_INFO);
            return "admin/function";
        }
        if ((admin = adminService.login(admin)) != null) {
            session.setAttribute("admin", admin);
            return "admin/function";
        } else {
            session.setAttribute("LOGIN_ERROR_INFO", LOGIN_ERROR_INFO);
            return "redirect:/loginAdmin.jsp";
        }
    }
    /**
	 * 根据页码分页查询
	 *
	 * @param session
	 * @param request
	 * @param currentPage
	 * @return
	 */
	@RequestMapping(value = "/findAllTeacher", method = RequestMethod.GET)
	public String findAllPaper(HttpSession session, HttpServletRequest request,
							   @RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage) {
		Page page = new Page();
		page.setCurrentPage(currentPage);
		session.setAttribute("currentPage", currentPage);
		List<Teacher> teachers = teacherService.findAllTeacher(page);
		session.setAttribute("teachers", teachers);
		session.setAttribute("page", page);
		return "admin/updateTeacherInfo";

	}

    @RequestMapping(value = "/admin/deletePaperById", method = RequestMethod.GET)
    public String deletePaperById(@RequestParam(value = "paper_id") int paper_id, HttpSession session,
                                  RedirectAttributes attr) {
        if (paperService.deletePaper(paper_id)) {
            attr.addAttribute("currentPage", session.getAttribute("currentPage"));
            attr.addAttribute("column", session.getAttribute("column"));
            attr.addAttribute("order", session.getAttribute("order"));
            return "redirect:../findAllPaper.do";
        } else
            return "";
    }

    /**
     * 显示论文修改页面
     *
     * @param paper_id
     * @param session
     * @return
     */
    @RequestMapping(value = "/modify")
    public ModelAndView modify(@RequestParam("paper_id") String paper_id,
                               @RequestParam(value = "commited_paper_id", required = false, defaultValue = "0") int commited_paper_id,
                               HttpSession session) {
        System.out.println(paper_id + "------------");
        System.out.println(commited_paper_id + "*************");
        if (session.getAttribute("admin") == null)
            return new ModelAndView("redirect:/loginAdmin.jsp", null);
        Map<String, Object> model = new HashMap<String, Object>();
//        Teacher teacher = null;
//        if (session.getAttribute("teacher") != null) {
//            teacher = (Teacher) session.getAttribute("teacher");
//        } else {
//            teacher = teacherService.findTeacherById(paperService.findTeacherIdByPaperId(commited_paper_id));
//        }
//        model.put("teacher", teacher);
        model.put("paper_id", paper_id);
        System.out.println(commited_paper_id + "------------------");
        model.put("commited_paper_id", commited_paper_id);
        return new ModelAndView("admin/paper_modify", model);
    }

    /**
     * 显示论文显示页面
     *
     * @param paperproxy_id
     * @param session
     * @return
     */
    @RequestMapping(value = "/paper/show", method = RequestMethod.GET)
    public ModelAndView ShowView(@RequestParam("paper_id") Integer paper_id, HttpSession session) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("paper_id", paper_id);
        model.put("teacher_no", ((Teacher) session.getAttribute("teacher")).getTeacher_no());
        return new ModelAndView("paper/paper_show", model);
    }

    /**
     * 获取论文对象
     *
     * @param paper_id
     * @param request
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPaper", method = RequestMethod.POST)
    public Paper show(@RequestParam("paper_id") Integer paper_id, HttpServletRequest request,
                      HttpSession session) {
        Paper paper = paperService.findPaperByPaperId(paper_id);
        return paper;
    }

    /**
     * 更新论文对象
     *
     * @param paper
     * @param other_includedType
     * @param request
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/paper/update", method = RequestMethod.POST)
    public ModelAndView update(Paper paper,
                               @RequestParam(value = "other_includedType", required = false) String other_includedType,
                               @RequestParam(value = "paper_conference_location1", required = false) String paper_conference_location1,
                               @RequestParam(value = "paper_conference_location2", required = false) String paper_conference_location2,
                               @RequestParam(value = "paper_journals_location1", required = false) String paper_journals_location1,
                               @RequestParam(value = "paper_journals_location2", required = false) String paper_journals_location2,
                               @RequestParam(value = "paper_journals_location3", required = false) String paper_journals_location3,
                               HttpServletRequest request,
                               HttpSession session) {
        // if ((Teacher) session.getAttribute("teacher") == null && (int)
        // commited_paper_id == 0) {
        // return new ModelAndView("redirect:/login.jsp", null);
        // }
        if (session.getAttribute("admin") == null)
            return new ModelAndView("redirect:/loginAdmin.jsp", null);
        //  System.out.println(commited_paper_id + "(((((((((((");
        // String paper_location = paper_location_issuing + "$" +
        // paper_location_volume + "$" + paper_location_pagination;
        if (null != other_includedType) {
            paper.setPaper_includedType(other_includedType);
        }
        // paper.setPaper_location(paper_location);
        //组装论文位置信息(以$分隔)
        StringBuilder stringBuilder = null;
        PaperIssueEnum paperIssue = PaperIssueEnum.getInstance(paper.getPaper_issue());
        switch (paperIssue) {
            case JOURNALS:
                stringBuilder = new StringBuilder();
                stringBuilder.append(paper_journals_location1).append("$")
                        .append(paper_journals_location2).append("$").append(paper_journals_location3);
                break;
            case CONFERENCE:
                stringBuilder = new StringBuilder();
                stringBuilder.append(paper_conference_location1).append("$")
                        .append(paper_conference_location2);
                //发表方式为会议时,出版物类型默认为国际
                paper.setPaper_publishType(PaperPublishTypeEnum.INTERNATIONAL.getType());
                break;
        }
        paper.setPaper_location(stringBuilder.toString());
        // 未更新前作者人数
        int ex_authorNum = paperService.findPaperByPaperId(paper.getPaper_id()).getPaper_authorNum();
        System.out.println("=========更新前作者人数：" + ex_authorNum);
        System.out.println("=========更新后作者人数：" + paper.getPaper_authorNum());
        paperService.updatePaper(paper);
//		List<AuthorProxy> authors = new ArrayList();
        // 作者表信息更新或者增加
        if (ex_authorNum <= paper.getPaper_authorNum()) {
            // authorProxy处理
            for (int i = 1; i <= paper.getPaper_authorNum(); i++) {
                // 输出信息====================================================
                System.out.println("=========");
                System.out.println(request.getParameter("author_id" + i));
                System.out.println(request.getParameter("authorName" + i));
                System.out.println(request.getParameter("authorID" + i));
                System.out.println(request.getParameter("authorOffice" + i));
                System.out.println(request.getParameter("authorType" + i));
                // ====================================================
                // 作者对象处理
                Author author = new Author();
                author.setAuthor_name(request.getParameter("authorName" + i));
                author.setAuthor_rank(i);
                String authorID = request.getParameter("authorID" + i);
                String authorOffice = request.getParameter("authorOffice" + i);
                int authorType = Integer.parseInt(request.getParameter("authorType" + i));
                if (authorID != null) {
                    author.setAuthor_no(authorID);
                }
                if (authorOffice != null) {
                    author.setAuthor_office(authorOffice);
                }
                author.setAuthor_paper(paper);
                author.setAuthor_type(authorType);
                // 当作者id为空时，表示新增加的作者信息
                if (request.getParameter("author_id" + i) == "" || request.getParameter("author_id" + i) == null) {
                    author.setAuthor_name(request.getParameter("authorName" + i));
                    authorService.createAuthor(author);
                } else {// 更新作者信息
                    author.setAuthor_id(Integer.valueOf(request.getParameter("author_id" + i)));
                    authorService.updateAuthor(author);
                }
                //	authors.add(authorProxy);
            }
        } else {
            // 作者表信息更新或者删除
            List<Author> ex_authors = authorService
                    .findAuthorListByPaperId(String.valueOf(paper.getPaper_id()));
            System.out.println("=========2==========" + ex_authors.size());
            // author处理
            for (int i = 1; i <= paper.getPaper_authorNum(); i++) {
                // 输出信息====================================================
                System.out.println("=========2");
                System.out.println(request.getParameter("author_id" + i));
                System.out.println(request.getParameter("authorName" + i));
                System.out.println(request.getParameter("authorID" + i));
                System.out.println(request.getParameter("authorOffice" + i));
                System.out.println(request.getParameter("authorType" + i));
                // ====================================================
                // 作者对象处理
                Author author = new Author();
                author.setAuthor_name(request.getParameter("authorName" + i));
                author.setAuthor_rank(i);
                String authorID = request.getParameter("authorID" + i);
                String authorOffice = request.getParameter("authorOffice" + i);
                int authorType = Integer.parseInt(request.getParameter("authorType" + i));
                if (authorID != null) {
                    author.setAuthor_no(authorID);
                }
                if (authorOffice != null) {
                    author.setAuthor_office(authorOffice);
                }
                author.setAuthor_paper(paper);
                author.setAuthor_type(authorType);
                author.setAuthor_id(Integer.valueOf(request.getParameter("author_id" + i)));

                for (Author author1 : ex_authors) {
                    if (author1.getAuthor_id() == author.getAuthor_id()) {
                        authorService.updateAuthor(author);
                        ex_authors.remove(author1);
                        break;
                    }
                }
                //	authors.add(authorProxy);
            }
            System.out.println("=========22==========" + ex_authors.size());
            for (Author author : ex_authors) {
                // 删除原作者表中多余信息
                authorService.deleteAuthor(author.getAuthor_id());
            }
        }
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("paper_id", paper.getPaper_id());
//
//        model.put("teacher_no", ((Teacher) session.getAttribute("teacher")).getTeacher_no());
        return new ModelAndView("redirect:/findAllPaperIndex.do", model);
    }

    @SuppressWarnings("deprecation")
    @RequestMapping(value = "/exportPaper", method = RequestMethod.GET)
    public ModelAndView findPaperById(HttpSession session, HttpServletRequest request, HttpServletResponse response) {


        @SuppressWarnings("unchecked")
        List<Paper> papers = (List<Paper>) session.getAttribute("papers");
        // 获取总列数
        int CountColumnNum = 20; //xls.size();
        // 创建Excel文档
        @SuppressWarnings("resource")
        HSSFWorkbook hwb = new HSSFWorkbook();

        // sheet 对应一个工作页
        HSSFSheet sheet = hwb.createSheet("论文表");

        //第一个工作页的内容
        HSSFRow firstrow = sheet.createRow(0); // 下标为0的行开始
        HSSFCell[] firstcell = new HSSFCell[CountColumnNum];
        String[] names = new String[CountColumnNum];
        names[0] = "论文名称";
        names[1] = "录入教师";
        names[2] = "录入教师在作者中的排名";
        names[3] = "作者总数";
        names[4] = "作者列表";
        names[5] = "收录类型";
        names[6] = "发表方式";
        names[7] = "发表状态";
        names[8] = "发表时间";
        names[9] = "影响因子";
        names[10] = "检索号";
        names[11] = "发表位置";
        names[12] = "出版物名称";
        names[13] = "出版物类型";
        names[14] = "总引用";
        names[15] = "他引用";
        names[16] = "google scholar 引用";
        names[17] = "等级";
        names[18] = "自定义期刊会议";
        names[19] = "是否zjut100期刊论文";
        for (int j = 0; j < CountColumnNum; j++) {
            firstcell[j] = firstrow.createCell((short) j);
            firstcell[j].setCellValue(new HSSFRichTextString(names[j]));
        }
        int a = 0;
        for (int i = 0; i < papers.size(); i++) {
            // 创建一行
            HSSFRow row = sheet.createRow(++a);
            Paper paper = paperService.findPaperByPaperId(papers.get(i).getPaper_id());
            List<Author> author = authorService.findAuthor(papers.get(i).getPaper_id());
            for (int colu = 0; colu <= CountColumnNum; colu++) {
                // 在一行内循环
                HSSFCell paper_name = row.createCell((short) 0);
                paper_name.setCellValue(paper.getPaper_name());

                HSSFCell paper_teacher = row.createCell((short) 1);
                paper_teacher.setCellValue(paper.getPaper_teacher().getTeacher_name());

                HSSFCell paper_rank = row.createCell((short) 2);
                paper_rank.setCellValue(paper.getPaper_rank());

                HSSFCell author_num = row.createCell((short) 3);
                author_num.setCellValue(paper.getPaper_authorNum());

                HSSFCell author_list = row.createCell((short) 4);
                String authorName = "";
                for (int k = 0; k < author.size(); k++) {
                    authorName = authorName + author.get(k).getAuthor_name() + ";";
                }
                author_list.setCellValue(authorName);

                HSSFCell paper_type = row.createCell((short) 5);
                paper_type.setCellValue(paper.getPaper_includedType());

                HSSFCell paper_issue = row.createCell((short) 6);
                String paperIssue = null;
                if (paper.getPaper_issue() == 0) {
                    paperIssue = "期刊";
                }
                if (paper.getPaper_issue() == 1) {
                    paperIssue = "会议";
                }
                paper_issue.setCellValue(paperIssue);

                HSSFCell paper_statue = row.createCell((short) 7);
                String paperStatue = null;
                if (paper.getPaper_status() == 0) {
                    paperStatue = "未发表";
                }
                if (paper.getPaper_status() == 1) {
                    paperStatue = "已发表";
                }
                paper_statue.setCellValue(paperStatue);

                HSSFCell paper_time = row.createCell((short) 8);
                paper_time.setCellValue(paper.getPaper_time());

                HSSFCell paper_if = row.createCell((short) 9);
                if (paper.getPaper_issue() == 0) {
                    paper_if.setCellValue(paper.getPaper_if());
                }

                HSSFCell paper_accNum = row.createCell((short) 10);
                paper_accNum.setCellValue(paper.getPaper_accNum());

                HSSFCell paper_location = row.createCell((short) 11);
                if (paper.getPaper_status() == 0) {
                    if (paper.getPaper_issue() == 0) {
                        String s1 = paper.getPaper_location();
                        System.out.println(s1);
                        request.setAttribute("paper_number", s1.substring(0, s1.indexOf("$")));
                        paper_location.setCellValue("期刊号:" + s1.substring(0, s1.indexOf("$")));
                    }
                    if (paper.getPaper_issue() == 1) {
                        String s1 = paper.getPaper_location();
                        System.out.println(s1);
                        request.setAttribute("meeting_place", s1.substring(s1.indexOf("$") + 1));
                        paper_location.setCellValue("会议地点:" + s1.substring(s1.indexOf("$") + 1));
                    }
                }
                if (paper.getPaper_status() == 1) {
                    if (paper.getPaper_issue() == 0) {
                        String s1 = paper.getPaper_location();
                        System.out.println(s1);
                        request.setAttribute("paper_number", s1.substring(0, s1.indexOf("$")));
                        String s2 = s1.substring(s1.indexOf("$") + 1);
                        System.out.println(s2);
                        request.setAttribute("paper_location_volume", s2.substring(0, s2.indexOf("$")));
                        String s3 = s2.substring(s2.indexOf("$") + 1);
                        System.out.println(s3);
                        request.setAttribute("paper_location_pagination", s3);
                        paper_location.setCellValue("期刊号:" + s1.substring(0, s1.indexOf("$")) + "卷期:" + s2.substring(0, s2.indexOf("$")) + "页码:" + s3);
                    }
                    if (paper.getPaper_issue() == 1) {
                        String s1 = paper.getPaper_location();
                        System.out.println(s1);
                        request.setAttribute("meeting_page", s1.substring(0, s1.indexOf("$")));
                        String s2 = s1.substring(s1.indexOf("$") + 1);
                        System.out.println(s2);
                        request.setAttribute("meeting_place", s2);
                        paper_location.setCellValue("会议页码:" + s1.substring(s1.indexOf("$") + 1) + "会议地点:" + s2);
                    }

                }
                HSSFCell paper_publishName = row.createCell((short) 12);
                paper_publishName.setCellValue(paper.getPaper_publishName());

                HSSFCell paper_publishType = row.createCell((short) 13);
                String paperpublishType = null;
                if (paper.getPaper_publishType() == 1) {
                    paperpublishType = "国内期刊";
                }
                if (paper.getPaper_publishType() == 2) {
                    paperpublishType = "国外期刊";
                }
                if (paper.getPaper_publishType() == 3) {
                    paperpublishType = "国际会议";
                }
                paper_publishType.setCellValue(paperpublishType);

                HSSFCell paper_citations = row.createCell((short) 14);
                paper_citations.setCellValue(paper.getPaper_citations());

                HSSFCell paper_citations_others = row.createCell((short) 15);
                paper_citations_others.setCellValue(paper.getPaper_citations_others());

                HSSFCell paper_citations_google = row.createCell((short) 16);
                paper_citations_google.setCellValue(paper.getPaper_citations_google());

                HSSFCell Journals_Conference = row.createCell((short) 17);
                String JournalsConference = " ";
                if (paper.getPaper_journals_conference_ZKY() != null) {
                    JournalsConference = JournalsConference + paper.getPaper_journals_conference_ZKY().getJournals_conference_name() + ";";
                }
                if (paper.getPaper_journals_conference_CCF() != null) {
                    JournalsConference = JournalsConference + paper.getPaper_journals_conference_CCF().getJournals_conference_name() + ";";
                }
                if (paper.getPaper_journals_conference_JCR() != null) {
                    JournalsConference = JournalsConference + paper.getPaper_journals_conference_JCR().getJournals_conference_name() + ";";
                }
                if (paper.getPaper_journals_conference_ESI() != null) {
                    JournalsConference = JournalsConference + paper.getPaper_journals_conference_ESI().getJournals_conference_name() + ";";
                    ;
                }
                if (paper.getPaper_journals_conference_OTHER() != null) {
                    JournalsConference = JournalsConference + paper.getPaper_journals_conference_OTHER().getJournals_conference_name();
                }
                Journals_Conference.setCellValue(JournalsConference);


                HSSFCell paper_journals_conference_CUSTOM = row.createCell((short) 18);
                paper_journals_conference_CUSTOM.setCellValue(paper.getPaper_journals_conference_CUSTOM());

                HSSFCell paper_journals_conference_isZjut100 = row.createCell((short) 19);
                String isZjut100 = null;
                if (paper.getPaper_journals_conference_isZjut100() == 0) {
                    isZjut100 = "否";
                }
                if (paper.getPaper_journals_conference_isZjut100() == 1) {
                    isZjut100 = "是";
                }
                paper_journals_conference_isZjut100.setCellValue(isZjut100);


            }
        }
        try {
            //OutputStream out = new FileOutputStream("E:论文.xls");
            //OutputStream out = new FileOutputStream("/Users/zhaogx/Downloads/论文.xls");
            OutputStream out = new FileOutputStream("/paper/download/paper.xls");
            hwb.write(out);
            out.close();
        } catch (Exception ex) {


        }
//		File file = new File("E:论文.xls");
//		File file = new File("/Users/zhaogx/Downloads/论文.xls");
        File file = new File("/paper/download/paper.xls");
        if (file.exists()) {
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition", "attachment;fileName=file.xls");// 设置文件名
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }

        return null;

    }
    
    @SuppressWarnings("deprecation")
    @RequestMapping(value = "/exportTeacher", method = RequestMethod.GET)
    public ModelAndView exportTeacher(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        @SuppressWarnings("unchecked")
        List<Teacher> teachers =teacherService.findAllTeacherMessage();
        // 获取总列数
        int CountColumnNum = 21; //xls.size();
        // 创建Excel文档
        @SuppressWarnings("resource")
        HSSFWorkbook hwb = new HSSFWorkbook();

        // sheet 对应一个工作页
        HSSFSheet sheet = hwb.createSheet("教师表");

        //第一个工作页的内容
        HSSFRow firstrow = sheet.createRow(0); // 下标为0的行开始
        HSSFCell[] firstcell = new HSSFCell[CountColumnNum];
        String[] names = new String[CountColumnNum];
        names[0] = "教师工号";
        names[1] = "财务工号";
        names[2] = "教师姓名";
        names[3] = "教师性别";
        names[4] = "教师邮箱";
        names[5] = "手机号码";
        names[6] = "办公电话";
        names[7] = "教师职称";
        names[8] = "教师生日";
        names[9] = "QQ号码";
        names[10] = "身份证号码";
        names[11] = "来工大时间";
        names[12] = "毕业院校";
        names[13] = "教师评价";
        names[14] = "个人主页";
        names[15] = "谷歌主页";
        names[16] = "所属学科";
        names[17] = "教学科目";
        names[18] = "研究方向";
        names[19] = "所属部门";
        names[20] = "是教学专业";
        for (int j = 0; j < CountColumnNum; j++) {
            firstcell[j] = firstrow.createCell((short) j);
            firstcell[j].setCellValue(new HSSFRichTextString(names[j]));
        }
        int a = 0;
        for (int i = 0; i < teachers.size(); i++) {
            // 创建一行
            HSSFRow row = sheet.createRow(++a);
            for (int colu = 0; colu <= CountColumnNum; colu++) {
                // 在一行内循环
                HSSFCell teacher_no = row.createCell((short) 0);
                teacher_no.setCellValue(teachers.get(i).getTeacher_no());

                HSSFCell teacher_finance_no = row.createCell((short) 1);
                teacher_finance_no.setCellValue(teachers.get(i).getTeacher_finance_no());

                HSSFCell teacher_name = row.createCell((short) 2);
                teacher_name.setCellValue(teachers.get(i).getTeacher_name());

                HSSFCell teacher_sex = row.createCell((short) 3);
                String sex = null;
                if (teachers.get(i).getTeacher_sex() == 0) {
                    sex = "女";
                }
                if (teachers.get(i).getTeacher_sex()== 1) {
                    sex = "男";
                }
                teacher_sex.setCellValue(sex);
                
                HSSFCell teacher_email = row.createCell((short) 4);
                teacher_email.setCellValue(teachers.get(i).getTeacher_email());
                
                HSSFCell teacher_phoneNumber = row.createCell((short) 5);
                teacher_phoneNumber.setCellValue(teachers.get(i).getTeacher_phoneNumber());
                
                HSSFCell teacher_officeNumber = row.createCell((short) 6);
                teacher_officeNumber.setCellValue(teachers.get(i).getTeacher_officeNumber());

                HSSFCell teacher_title= row.createCell((short) 7);
                teacher_title.setCellValue(teachers.get(i).getTeacher_title().getTitle_name());

                HSSFCell teacher_birth = row.createCell((short) 8);
                teacher_birth.setCellValue(teachers.get(i).getTeacher_birth());
                
                HSSFCell teacher_QQ = row.createCell((short) 9);
                teacher_QQ.setCellValue(teachers.get(i).getTeacher_qq());
                
                HSSFCell teacher_idCard = row.createCell((short) 10);
                teacher_idCard.setCellValue(teachers.get(i).getTeacher_idCard());
                
                HSSFCell teacher_comeTime = row.createCell((short) 11);
                teacher_comeTime.setCellValue(teachers.get(i).getTeacher_comeTime());
                
                HSSFCell teacher_graUniversity = row.createCell((short) 12);
                teacher_graUniversity.setCellValue(teachers.get(i).getTeacher_graUniversity());
                
                HSSFCell teacher_info = row.createCell((short) 13);
                teacher_info.setCellValue(teachers.get(i).getTeacher_info());
                
                HSSFCell teacher_info_url = row.createCell((short) 14);
                teacher_info_url.setCellValue(teachers.get(i).getTeacher_info_url());
                
                HSSFCell teacher_google_scolar_url = row.createCell((short) 15);
                teacher_google_scolar_url.setCellValue(teachers.get(i).getTeacher_google_scolar_url());
                
                HSSFCell teacher_belong_subject = row.createCell((short) 16);
                teacher_belong_subject.setCellValue(teachers.get(i).getTeacher_belong_subject().getSubject_name());

                HSSFCell teacher_subject = row.createCell((short) 17);
                teacher_subject.setCellValue(teachers.get(i).getTeacher_subject());
        
                HSSFCell teacher_subject_study = row.createCell((short) 18);
                teacher_subject_study.setCellValue(teachers.get(i).getTeacher_subject_study());
                
                HSSFCell teacher_institute = row.createCell((short) 19);
                teacher_institute.setCellValue(teachers.get(i).getTeacher_institute().getInstitute_name());
                
                HSSFCell teacher_teachingProfession = row.createCell((short) 20);
                teacher_teachingProfession.setCellValue(teachers.get(i).getTeacher_teachingProfession().getTeachingProfession_name());
            }   
        }
        try {
//            OutputStream out = new FileOutputStream("E:teacher.xls");
            //OutputStream out = new FileOutputStream("/Users/zhaogx/Downloads/论文.xls");
            OutputStream out = new FileOutputStream("/paper/download/teacher.xls");
            hwb.write(out);
            out.close();
        } catch (Exception ex) {


        }
//		File file = new File("E:teacher.xls");
//		File file = new File("/Users/zhaogx/Downloads/论文.xls");
        File file = new File("/paper/download/teacher.xls");
        if (file.exists()) {
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition", "attachment;fileName=file.xls");// 设置文件名
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }

        return null;

    }

    @ResponseBody
    @RequestMapping(value = "/admin/insertTeacher", method = RequestMethod.POST)
    public Map<String, String> insertTeacher(@RequestParam(value = "file", required = false) MultipartFile file,
                                             HttpServletRequest request) {
        Map<String, String> result = new HashMap<String, String>();
        if (file.isEmpty()) {
            result.put("status", "文件为空，请检查上传！");
            return result;
        }
        try {
            List<Teacher> teachers = teacherService.getTeacherFromExcel(file.getInputStream());
            for(int i=0;i<teachers.size();i++)
            {
            	Teacher teacher=teachers.get(i);
            	if(teacher.getTeacher_belong_subject()==null)
            	{
            		result.put("status", teacher.getTeacher_name()+"教师的所属学科信息有误！请检查重新输入！");
            		break;
            	}
            	else if(teacher.getTeacher_institute()==null)
            	{
            		result.put("status", teacher.getTeacher_name()+"教师的所属部门信息有误！请检查重新输入！");
            		break;
            	}
            	else if(teacher.getTeacher_title()==null)
            	{
            		result.put("status", teacher.getTeacher_name()+"教师的职称信息有误！请检查重新输入！");
            		break;
            	}
            	else if(teacher.getTeacher_teachingProfession()==null)
            	{
            		result.put("status", teacher.getTeacher_name()+"教师的教学专业信息有误！请检查重新输入！");
            		break;
            	}
            	else if(teacher.getTeacher_birth()==null)
            	{
            		result.put("status", teacher.getTeacher_name()+"教师的生日信息有误！请检查重新输入！");
            		break;
            	}
            	else if(teacher.getTeacher_comeTime()==null)
                	{
            		result.put("status", teacher.getTeacher_name()+"教师的来工大时间信息有误！请检查重新输入！");
            		break;
                }
            	else
            	{
            		 if (teacherService.insertTeacher(teachers, true))
                         result.put("status", "录入教师信息成功！");
                     else
                         result.put("status", "录入教师信息失败，请重新尝试！");
            	}
              }
            	
            
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result.put("status", "录入教师信息失败，请重新尝试！");
        }
        return result;
    }

    @RequestMapping(value = "/admin/insertOneTeacher", method = RequestMethod.GET)
    public String insertOneTeacher(HttpSession session, HttpServletRequest request) {
        if (session.getAttribute("admin") == null)
            return "redirect:/loginAdmin.jsp";
        List<Institute> institutes = instituteService.getAllInstitute();
        List<TeachingProfession> teachingProfession = teachingProfessionService.getAllTeachingProfession();
        List<Subject>subject=subjectService.getAllSubject();
        List<Title>title=titleService.getAllTitle();
        request.setAttribute("institutes", institutes);
        request.setAttribute("teachingProfession", teachingProfession);
        request.setAttribute("subject", subject);
        request.setAttribute("title", title);
        return "admin/insertOneTeacher";
    }

    @RequestMapping(value = "/admin/insertNTeacher", method = RequestMethod.GET)
    public String insertNTeacher(HttpSession session, HttpServletRequest request) {
        if (session.getAttribute("admin") == null)
            return "redirect:/loginAdmin.jsp";
//		List<Institute> institutes = instituteService.getAllInstitute();
//		request.setAttribute("institutes", institutes);
        return "admin/insertNTeacher";
    }

    @RequestMapping(value = "/admin/insertOneTeacher", method = RequestMethod.POST)
    public String insertOneTeacher(HttpSession session, Teacher teacher) {
        if (session.getAttribute("admin") == null)
            return "redirect:/loginAdmin.jsp";
        teacherService.insertTeacher(teacher);
        return "admin/insertOneTeacher";
    }

    @RequestMapping(value = "/admin/adminFunction", method = RequestMethod.GET)
    public String adminFunction(HttpSession session) {
        if (session.getAttribute("admin") == null)
            return "redirect:/loginAdmin.jsp";
        return "admin/function";
    }

    @RequestMapping(value = "/admin/updateTeacherInfo", method = RequestMethod.GET)
    public String updateTeacherInfo(HttpSession session) {
        if (session.getAttribute("admin") == null)
            return "redirect:/loginAdmin.jsp";
		Page page = new Page();
		page.setCurrentPage(1);
		List<Teacher> teachers = teacherService.findAllTeacher(page);
		session.setAttribute("page", page);
		session.setAttribute("teachers", teachers);
        return "admin/updateTeacherInfo";
    }

    @RequestMapping(value = "/admin/findTeacherInfo", method = RequestMethod.POST)
    public String findTeacherInfo(@RequestParam(value = "find_string") String find_string, HttpServletRequest request, HttpSession session) {
        if (session.getAttribute("admin") == null)
            return "redirect:/loginAdmin.jsp";
        Teacher teacher = teacherService.findTeacher(find_string);
        List<Institute> institutes = instituteService.getAllInstitute();
        List<TeachingProfession> teachingProfession = teachingProfessionService.getAllTeachingProfession();
        List<Subject> subject = subjectService.getAllSubject();
        List<Title> title=titleService.getAllTitle();
        request.setAttribute("teachingProfession", teachingProfession);
        request.setAttribute("institutes", institutes);
        request.setAttribute("find_string", find_string);
        request.setAttribute("isFindTeacher", true);
        request.setAttribute("teacher", teacher);
        request.setAttribute("subject", subject);
        request.setAttribute("title", title);
        return "admin/updateTeacherInfo";
    }
    
    @RequestMapping(value = "/admin/TeacherInfo", method = RequestMethod.GET)
    public String TeacherInfo(@RequestParam(value = "teacher_no") String teacher_no, HttpServletRequest request, HttpSession session) {
        if (session.getAttribute("admin") == null)
            return "redirect:/loginAdmin.jsp";
        Teacher teacher = teacherService.findTeacher(teacher_no);
        List<Institute> institutes = instituteService.getAllInstitute();
        List<TeachingProfession> teachingProfession = teachingProfessionService.getAllTeachingProfession();
        List<Subject> subject = subjectService.getAllSubject();
        List<Title> title=titleService.getAllTitle();
        request.setAttribute("teachingProfession", teachingProfession);
        request.setAttribute("institutes", institutes);
        request.setAttribute("teacher_no", teacher_no);
        request.setAttribute("isFindTeacher", true);
        request.setAttribute("teacher", teacher);
        request.setAttribute("subject", subject);
        request.setAttribute("title", title);
        return "admin/updateTeacherInfo";
    }

    @RequestMapping(value = "/admin/updateTeacher", method = RequestMethod.POST)
    public String updateTeacher(Teacher teacher, HttpSession session, HttpServletRequest request) {
        if (session.getAttribute("admin") == null)
            return "redirect:/loginAdmin.jsp";
        if (teacherService.updateTeacher(teacher)) {
            teacher = teacherService.findTeacher(teacher.getTeacher_no());
            List<Institute> institutes = instituteService.getAllInstitute();
            List<TeachingProfession> teachingProfession = teachingProfessionService.getAllTeachingProfession();
            List<Subject> subject = subjectService.getAllSubject();
            List<Title>title=titleService.getAllTitle();
            request.setAttribute("teachingProfession", teachingProfession);
            request.setAttribute("teacher", teacher);
            request.setAttribute("find_string", teacher.getTeacher_no());
            request.setAttribute("isFindTeacher", true);
            request.setAttribute("institutes", institutes);
            request.setAttribute("subject", subject);
            request.setAttribute("title", title);
            request.setAttribute("updateTeacherResult", "修改教师信息成功！");
        } else {
            request.setAttribute("updateTeacherResult", "修改教师信息失败，请重新尝试！");
        }
        return "admin/updateTeacherInfo";
    }

    @ResponseBody
    @RequestMapping(value = "/admin/resetPassword", method = RequestMethod.POST)
    public String resetPassword(@RequestParam(value = "teacher_id") String teacher_id,
                                @RequestParam(value = "teacher_no") String teacher_no, HttpSession session) {
        String result = null;
        if (session.getAttribute("admin") == null) {
            result = "admin_not_login";
            return result;
        }
        String sha1_password = CryptoUtil.SHA1("123456");
        if (teacherService.updatePassword(teacher_id, sha1_password))
            result = "success";
        else
            result = "resetPassword_error";
        return result;
    }
    
    @RequestMapping(value = "/admin/deleteTeacher", method = RequestMethod.GET)
    public String resetPassword(@RequestParam(value = "teacher_id") int teacher_id,
                                HttpSession session,HttpServletRequest request) {
    	if (session.getAttribute("admin") == null)
    	return "redirect:/loginAdmin.jsp";	
		teacherService.deleteTeacher(teacher_id);
	 return "redirect:/admin/updateTeacherInfo.do";
    }

    @ResponseBody
    @RequestMapping(value = "/admin/findTeacherNo", method = RequestMethod.POST)
    public String findTeacherNo(@RequestParam(value = "teacher_no") String teacher_no) {
        String result = "false";
        if (teacherService.findTeacher(teacher_no) != null)
            result = "true";
        return result;
    }
    @RequestMapping(value = "/admin/findInstitute", method = RequestMethod.GET)
	public String findInstitute(HttpSession session,HttpServletRequest request){
		if (session.getAttribute("admin") == null)
			return "redirect:/loginAdmin.jsp";
		List<Institute> institutes = instituteService.getAllInstitute();
		request.setAttribute("institutes", institutes);
		request.setAttribute("type", "institute");
		return "admin/infoConfig";
	}
    @RequestMapping(value = "/admin/addMessagePage", method = RequestMethod.GET)
	public String addMessage(@RequestParam("type") String type,
			                 HttpSession session,HttpServletRequest request) throws UnsupportedEncodingException{
    	request.setAttribute("type", type);
    	return "admin/insertMessage";
	}
	@RequestMapping(value = "/admin/insertInstitute", method = RequestMethod.POST)
	public String addMessage(Institute institute,
			HttpSession session,HttpServletRequest request) throws UnsupportedEncodingException{
		if (session.getAttribute("admin") == null)
			return "redirect:/loginAdmin.jsp";	
			instituteService.insertInstitute(institute);
		 return "redirect:/admin/findInstitute.do";
	}
	
	@RequestMapping(value = "/admin/updateInstitute", method = RequestMethod.POST)
	public String updateInstitute(Institute institute,HttpSession session,HttpServletRequest request) throws UnsupportedEncodingException{
		if (session.getAttribute("admin") == null)
			return "redirect:/loginAdmin.jsp";
		instituteService.updateInstitute(institute);
		 return "redirect:/admin/findInstitute.do";
	}
	@RequestMapping(value = "/admin/deleteInstitute", method = RequestMethod.GET)
	public String deleteInstitute(@RequestParam("institute_id") int institute_id,
			HttpSession session,HttpServletRequest request) throws UnsupportedEncodingException{
		if (session.getAttribute("admin") == null)
			return "redirect:/loginAdmin.jsp";
		instituteService.deleteInstitute(institute_id);
		 return "redirect:/admin/findInstitute.do";
	}
	@RequestMapping(value = "/admin/findProfession", method = RequestMethod.GET)
	public String findProfession(HttpSession session,HttpServletRequest request){
		if (session.getAttribute("admin") == null)
			return "redirect:/loginAdmin.jsp";
		  List<TeachingProfession> teachingProfession = teachingProfessionService.getAllTeachingProfession();
		request.setAttribute("teachingProfession", teachingProfession);
		request.setAttribute("type", "profession");
		return "admin/infoConfig";
	}
	@RequestMapping(value = "/admin/insertProfession", method = RequestMethod.POST)
	public String addMessage(TeachingProfession teachingProfession,
			HttpSession session,HttpServletRequest request) throws UnsupportedEncodingException{
		if (session.getAttribute("admin") == null)
			return "redirect:/loginAdmin.jsp";	
		teachingProfessionService.insertProfession(teachingProfession);
		 return "redirect:/admin/findProfession.do";
	}
	@RequestMapping(value = "/admin/updateProfession", method = RequestMethod.POST)
	public String updateProfession(TeachingProfession teachingProfession,HttpSession session,HttpServletRequest request) throws UnsupportedEncodingException{
		if (session.getAttribute("admin") == null)
			return "redirect:/loginAdmin.jsp";
		teachingProfessionService.updateProfession(teachingProfession);
		 return "redirect:/admin/findProfession.do";
	}
	@RequestMapping(value = "/admin/deleteProfession", method = RequestMethod.GET)
	public String deleteProfession(@RequestParam("teachingProfession_id") int teachingProfession_id,
			HttpSession session,HttpServletRequest request) throws UnsupportedEncodingException{
		if (session.getAttribute("admin") == null)
			return "redirect:/loginAdmin.jsp";
		teachingProfessionService.deleteProfession(teachingProfession_id);
		 return "redirect:/admin/findProfession.do";
	}
	@RequestMapping(value = "/admin/findSubject", method = RequestMethod.GET)
	public String findSubject(HttpSession session,HttpServletRequest request){
		if (session.getAttribute("admin") == null)
			return "redirect:/loginAdmin.jsp";
		  List<Subject> subject = subjectService.getAllSubject();
		request.setAttribute("subject", subject);
		request.setAttribute("type", "subject");
		return "admin/infoConfig";
	}
	@RequestMapping(value = "/admin/insertSubject", method = RequestMethod.POST)
	public String addMessage(Subject subject,
			HttpSession session,HttpServletRequest request) throws UnsupportedEncodingException{
		if (session.getAttribute("admin") == null)
			return "redirect:/loginAdmin.jsp";	
		subjectService.insertSubject(subject);
		 return "redirect:/admin/findSubject.do";
	}
	@RequestMapping(value = "/admin/updateSubject", method = RequestMethod.POST)
	public String updateSubject(Subject subject,HttpSession session,HttpServletRequest request) throws UnsupportedEncodingException{
		if (session.getAttribute("admin") == null)
			return "redirect:/loginAdmin.jsp";
		 subjectService.updateSubject(subject);
		 return "redirect:/admin/findSubject.do";
	}
	@RequestMapping(value = "/admin/deleteSubject", method = RequestMethod.GET)
	public String deleteSubject(@RequestParam("subject_id") int subject_id,
			HttpSession session,HttpServletRequest request) throws UnsupportedEncodingException{
		if (session.getAttribute("admin") == null)
			return "redirect:/loginAdmin.jsp";
		subjectService.deleteSubject(subject_id);
		 return "redirect:/admin/findSubject.do";
	}
	@RequestMapping(value = "/admin/deleteTitle", method = RequestMethod.GET)
	public String deleteTitle(@RequestParam("title_id") int title_id,
			HttpSession session,HttpServletRequest request) throws UnsupportedEncodingException{
		if (session.getAttribute("admin") == null)
			return "redirect:/loginAdmin.jsp";
		titleService.deleteTitle(title_id);
		 return "redirect:/admin/findTitle.do";
	}
	@RequestMapping(value = "/admin/findTitle", method = RequestMethod.GET)
	public String findTitle(HttpSession session,HttpServletRequest request){
		if (session.getAttribute("admin") == null)
			return "redirect:/loginAdmin.jsp";
		  List<Title> title = titleService.getAllTitle();
		request.setAttribute("title", title);
		request.setAttribute("type", "title");
		return "admin/infoConfig";
	}
	@RequestMapping(value = "/admin/insertTitle", method = RequestMethod.POST)
	public String addMessage(Title title,
			HttpSession session,HttpServletRequest request) throws UnsupportedEncodingException{
		if (session.getAttribute("admin") == null)
			return "redirect:/loginAdmin.jsp";	
		titleService.insertTitle(title);
		 return "redirect:/admin/findTitle.do";
	}
	@RequestMapping(value = "/admin/updateTitle", method = RequestMethod.POST)
	public String updateTitle(Title title,HttpSession session,HttpServletRequest request) throws UnsupportedEncodingException{
		if (session.getAttribute("admin") == null)
			return "redirect:/loginAdmin.jsp";
		 titleService.updateTitle(title);
		 return "redirect:/admin/findTitle.do";
	}

}
