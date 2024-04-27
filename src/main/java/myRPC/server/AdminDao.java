package myRPC.server;

import lombok.SneakyThrows;
import myRPC.Dao.*;
import myRPC.common.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminDao extends HttpServlet implements Remote {
    private static final long serialVersionUID = 1L;
    private Connection connection;
    private Map<String, Object> serviceMap = new HashMap<>();

    private String action;//存储操作描述
    //接收请求

    @SneakyThrows
    public AdminDao() throws RemoteException {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/RPCdemo",
                    "root", "Ga526424...?");
            // 在构造方法中初始化serviceMap，将接口名和对应的实现类对象放入map中
            serviceMap.put("UserService", new UserDAO(connection));
            serviceMap.put("DepartmentService", new DepartmentDAO(connection));
            serviceMap.put("StudentService", new StudentDAO(connection));
            serviceMap.put("Class1Service", new Class1DAO(connection));
            serviceMap.put("CourseService", new CourseDAO(connection));
            serviceMap.put("CourseRegistrationService", new CourseRegistrationDAO(connection));
            serviceMap.put("StudentGradeService", new StudentGradeDAO(connection));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        action = request.getParameter("action");
        //判断所执行操作
        switch (action) {
            //用户操作
            case "query_all_user":
                query_all_user(request, response);break;
            case "insert_user":
                insert_user(request,response);break;
            case "delete_user":
                delete_user(request, response);break;
            case "alter_user":
                alter_user(request, response);break;
            //院系操作
            case "query_all_department":
                query_all_department(request, response);break;
            case "insert_department":
                insert_department(request, response);break;
            case "delete_department":
                delete_department(request, response);break;
            case "alter_department":
                alter_department(request, response);break;
            //班级操作
            case "query_all_class":
                query_all_class(request, response);break;
            case "insert_class":
                insert_class(request, response);break;
            case "delete_class":
                delete_class(request, response);break;
            case "alter_class":
                alter_class(request, response);break;
            //学生操作
            case "query_all_student":
                query_all_student(request, response);break;
            case "insert_student":
                insert_student(request, response);break;
            case "delete_student":
                delete_student(request, response);break;
            case "alter_student":
                alter_student(request, response);break;
            //课程操作
            case "course_avg":
                course_avg(request, response);break;
            case "fail_rate":
                fail_rate(request, response);break;
            case "course_ranking":
                course_ranking(request, response);break;
            case "query_all_course":
                query_all_course(request, response);break;
            case "insert_course":
                insert_course(request, response);break;
            case "delete_course":
                delete_course(request, response);break;
            case "alter_course":
                alter_course(request, response);break;
            //成绩操作
            case "query_all_sc":
                query_all_sc(request, response);break;
            case "insert_sc":
                insert_sc(request, response);break;
            case "delete_sc":
                delete_sc(request, response);break;
            case "alter_sc":
                alter_sc(request, response);break;
            default:
                break;
        }
    }
    /*-------------------------------- 用户 -----------------------------------*/
    //查询所有用户
    protected void query_all_user(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        UserDAO userDao =  (UserDAO) serviceMap.get("UserService");

        ArrayList<User> results = userDao.getAllUsers();
        PrintWriter out = response.getWriter();
        //输出结果
        if(results != null){
            out.write("<div class='all'>");
            out.write("<div><span>用户名</span><span>密码</span><span>权限级别</span></div>");
            for(User i: results){
                out.write("<div>");
                out.write("<span>"+i.getUsername()+"</span>");
                out.write("<span>"+i.getPassword()+"</span>");
                out.write("<span>"+i.getRole()+"</span>");
                out.write("</div>");
            }
            out.write("</div>");
        }

        out.flush();
        out.close();
    }
    //插入用户
    protected void insert_user(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String level = request.getParameter("level");

        UserDAO userDao =  (UserDAO) serviceMap.get("UserService");
        int flag =  userDao.addUser(username, password, level);
        String info = null;
        PrintWriter out =  response.getWriter();
        if(flag == 1){
            info = "用户插入成功！";
        }else{
            info = "错误：用户插入失败！";
        }
        out.write("<div class='error'>");
        out.write("<div>"+info+"</div>");
        out.write("</div>");
        out.flush();
        out.close();
    }
    //删除用户
    protected void delete_user(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String username = request.getParameter("username");

        UserDAO userDao =  (UserDAO) serviceMap.get("UserService");
        int flag =  userDao.deleteUser(username);
        String info = null;
        PrintWriter out =  response.getWriter();
        if(flag == 1){
            info = "成功删除名为"+username+"用户！";
        }else{
            info = "错误：删除用户失败！";
        }
        out.write("<div class='error'>");
        out.write("<div>"+info+"</div>");
        out.write("</div>");
        out.flush();
        out.close();
    }
    //修改用户
    protected void alter_user(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String username = request.getParameter("username");
        String after_username = request.getParameter("after_username");
        String after_password = request.getParameter("after_password");
        String after_level = request.getParameter("after_level");

        UserDAO userDao =  (UserDAO) serviceMap.get("UserService");
        int flag =  userDao.updateUser(username,after_username,after_password,after_level);
        String info = null;
        PrintWriter out =  response.getWriter();
        if(flag == 1){
            info = "名为"+username+"用户信息修改成功！";
        }else{
            info = "错误：修改用户失败!";
        }
        out.write("<div class='error'>");
        out.write("<div>"+info+"</div>");
        out.write("</div>");
        out.flush();
        out.close();
    }

    /*-------------------------------- 院系-----------------------------------*/
    // 查询所有院系
    protected void query_all_department(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");

        DepartmentDAO departmentDAO = (DepartmentDAO) serviceMap.get("DepartmentService");
        ArrayList<Department> results = departmentDAO.getAllDepartments();
        PrintWriter out = response.getWriter();
        // 输出结果
        if (results != null) {
            out.write("<div class='all'>");
            out.write("<div><span>系编号</span><span>系名</span></div>");
            for (Department i : results) {
                out.write("<div>");
                out.write("<span>" + i.getDepartmentId() + "</span>");
                out.write("<span>" + i.getDepartmentName() + "</span>");
                out.write("</div>");
            }
            out.write("</div>");
        }
        out.flush();
        out.close();
    }
    // 插入院系
    protected void insert_department(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String dno = request.getParameter("dno");
        String dname = request.getParameter("dname");
        DepartmentDAO departmentDAO = (DepartmentDAO) serviceMap.get("DepartmentService");
        int flag = departmentDAO.addDepartment(dno, dname);
        String info = null;
        PrintWriter out = response.getWriter();
        if (flag == 1) {
            info = "院系"+dname+"插入成功！";
        } else {
            info = "错误：院系插入失败！";
        }
        out.write("<div class='error'>");
        out.write("<div>" + info + "</div>");
        out.write("</div>");
        out.flush();
        out.close();
    }
    // 删除院系
    protected void delete_department(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String dno = request.getParameter("dno");
        DepartmentDAO departmentDAO = (DepartmentDAO) serviceMap.get("DepartmentService");
        int flag = departmentDAO.deleteDepartment(dno);
        String info = null;
        PrintWriter out = response.getWriter();
        if (flag == 1) {
            info = "成功删除" + dno + "号院系！";
        } else {
            info = "错误：删除院系失败！";
        }
        out.write("<div class='error'>");
        out.write("<div>" + info + "</div>");
        out.write("</div>");
        out.flush();
        out.close();
    }
    // 修改院系
    protected void alter_department(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String dno = request.getParameter("dno");
        String after_dno = request.getParameter("after_dno");
        String after_dname = request.getParameter("after_dname");
        DepartmentDAO departmentDAO = (DepartmentDAO) serviceMap.get("DepartmentService");
        int flag = departmentDAO.updateDepartment(dno, after_dno, after_dname);
        String info = null;
        PrintWriter out = response.getWriter();
        if (flag == 1) {
            info = dno + "号系修改成功！";
        } else {
            info = "错误：修改院系失败!";
        }
        out.write("<div class='error'>");
        out.write("<div>" + info + "</div>");
        out.write("</div>");
        out.flush();
        out.close();
    }

    /*-------------------------------- 班级-----------------------------------*/
    // 查询所有班级
    protected void query_all_class(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        Class1DAO class1DAO = (Class1DAO) serviceMap.get("Class1Service");
        List<Class1> results = class1DAO.getAllClasses();
        PrintWriter out = response.getWriter();
        // 输出结果
        if (results != null) {
            out.write("<div class='all'>");
            out.write("<div><span>班级编号</span><span>班级名</span><span>所属院系</span></div>");
            for (Class1 i : results) {
                out.write("<div>");
                out.write("<span>" + i.getClassId() + "</span>");
                out.write("<span>" + i.getClassName() + "</span>");
                out.write("<span>" + i.getDepartmentId() + "</span>");
                out.write("</div>");
            }
            out.write("</div>");
        }
        out.flush();
        out.close();
    }
    // 插入班级
    protected void insert_class(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String clno = request.getParameter("clno");
        String clname = request.getParameter("clname");
        String dno = request.getParameter("dno");
        Class1DAO class1DAO = (Class1DAO) serviceMap.get("Class1Service");
        int flag = class1DAO.addClass(clno, clname, dno);
        String info = null;
        PrintWriter out = response.getWriter();
        if (flag == 1) {
            info = "班级"+clname+"插入成功！";
        } else {
            info = "错误：班级插入失败！";
        }
        out.write("<div class='error'>");
        out.write("<div>" + info + "</div>");
        out.write("</div>");
        out.flush();
        out.close();
    }
    // 删除班级
    protected void delete_class(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String clno = request.getParameter("clno");
        Class1DAO class1DAO = (Class1DAO) serviceMap.get("Class1Service");
        int flag = class1DAO.deleteClass(clno);
        String info = null;
        PrintWriter out = response.getWriter();
        if (flag == 1) {
            info = "成功删除" + clno + "班级！";
        } else {
            info = "错误：删除班级失败！";
        }
        out.write("<div class='error'>");
        out.write("<div>" + info + "</div>");
        out.write("</div>");
        out.flush();
        out.close();
    }
    // 修改班级
    protected void alter_class(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String clno = request.getParameter("clno");
        String after_clno = request.getParameter("after_clno");
        String after_clname = request.getParameter("after_clname");
        String after_dno = request.getParameter("after_dno");
        Class1DAO class1DAO = (Class1DAO) serviceMap.get("Class1Service");
        int flag = class1DAO.updateClass(clno, after_clno, after_clname, after_dno);
        String info = null;
        PrintWriter out = response.getWriter();
        if (flag == 1) {
            info = "班级"+clno+"修改成功！";
        } else {
            info = "错误：修改班级失败!";
        }
        out.write("<div class='error'>");
        out.write("<div>" + info + "</div>");
        out.write("</div>");
        out.flush();
        out.close();
    }

    /*-------------------------------- 学生-----------------------------------*/
    // 查询所有学生
    protected void query_all_student(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        StudentDAO studentDAO = (StudentDAO) serviceMap.get("StudentService");
        List<Student> results = studentDAO.getAllStudents();
        PrintWriter out = response.getWriter();
        // 输出结果
        if (results != null) {
            out.write("<div class='all'>");
            out.write("<div><span>学号</span><span>姓名</span><span>性别</span><span>年龄</span><span>所在班级编号</span></div>");
            for (Student i : results) {
                out.write("<div>");
                out.write("<span>" + i.getStudentId() + "</span>");
                out.write("<span>" + i.getName() + "</span>");
                out.write("<span>" + i.getGender() + "</span>");
                out.write("<span>" + i.getAge() + "</span>");
                out.write("<span>" + i.getClassId() + "</span>");
                out.write("</div>");
            }
            out.write("</div>");
        }
        out.flush();
        out.close();
    }
    // 插入学生
    protected void insert_student(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String sno = request.getParameter("sno");
        String sname = request.getParameter("sname");
        String ssex = request.getParameter("ssex");
        int sage = Integer.parseInt(request.getParameter("sage"));
        String clno = request.getParameter("clno");
        StudentDAO studentDAO = (StudentDAO) serviceMap.get("StudentService");
        int flag = studentDAO.addStudent(sno, sname, ssex, sage, clno);
        String info = null;
        PrintWriter out = response.getWriter();
        if (flag == 1) {
            info = "学生"+sname+"插入成功！";
        } else {
            info = "错误：学生插入失败！";
        }
        out.write("<div class='error'>");
        out.write("<div>" + info + "</div>");
        out.write("</div>");
        out.flush();
        out.close();
    }
    // 删除学生
    protected void delete_student(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String sno = request.getParameter("sno");
        StudentDAO studentDAO = (StudentDAO) serviceMap.get("StudentService");
        int flag = studentDAO.deleteStudent(sno);
        String info = null;
        PrintWriter out = response.getWriter();
        if (flag == 1) {
            info = "成功删除" + sno + "号学生！";
        } else {
            info = "错误：删除学生失败！";
        }
        out.write("<div class='error'>");
        out.write("<div>" + info + "</div>");
        out.write("</div>");
        out.flush();
        out.close();
    }
    // 修改学生信息
    protected void alter_student(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String sno = request.getParameter("sno");
        String after_sno = request.getParameter("after_sno");
        String after_sname = request.getParameter("after_sname");
        String after_ssex = request.getParameter("after_ssex");
        int after_sage = Integer.parseInt(request.getParameter("after_sage"));
        String after_clno = request.getParameter("after_clno");
        StudentDAO studentDAO = (StudentDAO) serviceMap.get("StudentService");
        int flag = studentDAO.updateStudent(sno, after_sno, after_sname, after_ssex, after_sage, after_clno);
        String info = null;
        PrintWriter out = response.getWriter();
        if (flag == 1) {
            info = "学生"+sno+"信息修改成功！";
        } else {
            info = "错误：修改学生信息失败!";
        }
        out.write("<div class='error'>");
        out.write("<div>" + info + "</div>");
        out.write("</div>");
        out.flush();
        out.close();
    }

    /*-------------------------------- 课程 -----------------------------------*/
    //查询课程平均分
    protected void course_avg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");
        CourseDAO courseDAO = (CourseDAO) serviceMap.get("CourseService");
        ArrayList<Course_avg> results = courseDAO.course_avg();
        PrintWriter out = response.getWriter();
        if(results != null){
            //输出结果
            if(results != null){
                out.write("<div class='all'>");
                out.write("<div><span>课程号</span><span>课程名称</span><span>平均分</span></div>");
                for(Course_avg i:results) {
                    out.write("<div>");
                    out.write("<span>"+i.getCourseID()+"</span>");
                    out.write("<span>"+i.getCourseName()+"</span>");
                    out.write("<span>"+i.getAvg()+"</span>");
                    out.write("</div>");
                }
                out.write("</div>");
            }
        }
        out.flush();
        out.close();
    }
    //查询课程不及格率
    protected void fail_rate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        CourseDAO courseDAO = (CourseDAO) serviceMap.get("CourseService");
        ArrayList<Course_fail_rate> results = courseDAO.fail_rate();
        PrintWriter out = response.getWriter();
        // 输出结果
        if (results != null) {
            out.write("<div class='all'>");
            out.write("<div><span>课程号</span><span>课程名称</span><span>不及格人数</span><span>不及格率</span></div>");
            for (Course_fail_rate i : results) {
                out.write("<div>");
                out.write("<span>" + i.getCourseID() + "</span>");
                out.write("<span>" + i.getCourseName() + "</span>");
                out.write("<span>" + i.getFailCount() + "</span>");
                out.write("<span>" + i.getFail_rate() + "%</span>");
                out.write("</div>");
            }
            out.write("</div>");
        }
        out.flush();
        out.close();
    }
    // 查询课程排名
    protected void course_ranking(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String Cno = request.getParameter("cno");
        CourseDAO courseDAO = (CourseDAO) serviceMap.get("CourseService");
        ArrayList<Course_ranking> results = courseDAO.course_ranking(Cno);
        PrintWriter out = response.getWriter();
        // 输出结果
        if (results != null) {
            out.write("<div class='all'>");
            out.write("<div><span>学号</span><span>所在系</span><span>班级名称</span><span>姓名</span><span>性别</span><span>年龄</span><span>成绩</span><span>排名</span></div>");
            for (Course_ranking i : results) {
                out.write("<div>");
                out.write("<span>" + i.getStudentId() + "</span>");
                out.write("<span>" + i.getDepartmentName() + "</span>");
                out.write("<span>" + i.getCourseName() + "</span>");
                out.write("<span>" + i.getName() + "</span>");
                out.write("<span>" + i.getGender() + "</span>");
                out.write("<span>" + i.getAge() + "</span>");
                out.write("<span>" + i.getGrade() + "</span>");
                out.write("<span>" + i.getRanking()+"</span>");
                out.write("</div>");
            }
            out.write("</div>");
        }
        out.flush();
        out.close();
    }
    //查询所有课程
    protected void query_all_course(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        CourseDAO courseDAO = (CourseDAO) serviceMap.get("CourseService");
        List<Course> results = courseDAO.getAllCourses();
        PrintWriter out = response.getWriter();
        if(results != null){
            //输出结果
            if(results != null){
                out.write("<div class='all'>");
                out.write("<div><span>课程号</span><span>课程名称</span><span>执教老师</span><span>学分</span></div>");
                for(Course i:results) {
                    out.write("<div>");
                    out.write("<span>"+i.getCourseId()+"</span>");
                    out.write("<span>"+i.getCourseName()+"</span>");
                    out.write("<span>"+i.getTeacherName()+"</span>");
                    out.write("<span>"+i.getCredit()+"</span>");
                    out.write("</div>");
                }
                out.write("</div>");
            }
        }
        out.flush();
        out.close();
    }
    //插入课程
    protected void insert_course(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String Cno = request.getParameter("cno");
        String Cname = request.getParameter("cname");
        String Cteacher = request.getParameter("cteacher");
        int Ccredit = Integer.parseInt(request.getParameter("ccredit"));
        CourseDAO courseDAO = (CourseDAO) serviceMap.get("CourseService");
        int flag =  courseDAO.addCourse(Cno, Cname, Cteacher, Ccredit);
        String info = null;
        PrintWriter out =  response.getWriter();
        if(flag == 1){
            info = "课程"+Cname+"插入成功！";
        }else{
            info = "错误：课程插入失败！";
        }
        out.write("<div class='error'>");
        out.write("<div>"+info+"</div>");
        out.write("</div>");
        out.flush();
        out.close();
    }
    //删除课程
    protected void delete_course(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String cno = request.getParameter("cno");
        CourseDAO courseDAO = (CourseDAO) serviceMap.get("CourseService");
        int flag =  courseDAO.deleteCourseById(cno);
        String info = null;
        PrintWriter out =  response.getWriter();
        if(flag == 1){
            info = "成功删除"+cno+"课程！";
        }else{
            info = "错误：删除课程失败！";
        }
        out.write("<div class='error'>");
        out.write("<div>"+info+"</div>");
        out.write("</div>");
        out.flush();
        out.close();
    }
    //修改课程信息
    protected void alter_course(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");

        String cno = request.getParameter("cno");
        String after_cno = request.getParameter("after_cno");
        String after_cname = request.getParameter("after_cname");
        String after_cteacher = request.getParameter("after_cteacher");
        int after_ccredit = Integer.parseInt(request.getParameter("after_ccredit"));
        CourseDAO courseDAO = (CourseDAO) serviceMap.get("CourseService");
        int flag = courseDAO.updateCourse(cno, after_cno, after_cname, after_cteacher, after_ccredit);
        String info = null;
        PrintWriter out = response.getWriter();
        if (flag == 1) {
            info = cno + "号课程修改成功！";
        } else {
            info = "错误：修改课程信息失败!";
        }
        out.write("<div class='error'>");
        out.write("<div>" + info + "</div>");
        out.write("</div>");
        out.flush();
        out.close();
    }

    /*-------------------------------- 成绩-----------------------------------*/
    // 查询所有成绩表
    protected void query_all_sc(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        StudentGradeDAO studentGradeDAO = (StudentGradeDAO) serviceMap.get("StudentGradeService");
        List<StudentGrade> results = studentGradeDAO.getAllStudentGrades();
        PrintWriter out = response.getWriter();
        // 输出结果
        if (results != null) {
            out.write("<div id='all_sc' class='all'>");
            out.write("<div><span>学号</span><span>姓名</span><span>课程号</span><span>课程名称</span><span>分数</span></div>");
            for (StudentGrade i : results) {
                out.write("<div>");
                out.write("<span>" + i.getStudentId() + "</span>");
                out.write("<span>" + i.getName() + "</span>");
                out.write("<span>" + i.getCourseId() + "</span>");
                out.write("<span>" + i.getCourseName() + "</span>");
                out.write("<span>" + i.getGrade() + "</span>");
                out.write("</div>");
            }
            out.write("</div>");
        }
        out.flush();
        out.close();
    }
    // 插入一条成绩记录
    protected void insert_sc(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String sno = request.getParameter("studentId");
        String cno = request.getParameter("courseId");
        int grade = Integer.parseInt(request.getParameter("grade"));
        StudentGradeDAO studentGradeDAO = (StudentGradeDAO) serviceMap.get("StudentGradeService");
        int flag = studentGradeDAO.addStudentGrade(sno, cno, grade);
        String info = null;
        PrintWriter out = response.getWriter();
        if (flag == 1) {
            info = sno + "号学生" + cno + "号课程成绩"+grade+"插入成功！";
        } else {
            info = "错误：成绩信息插入失败！";
        }
        out.write("<div class='error'>");
        out.write("<div>" + info + "</div>");
        out.write("</div>");
        out.flush();
        out.close();
    }
    // 删除成绩记录
    protected void delete_sc(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String sno = request.getParameter("studentId");
        String cno = request.getParameter("courseId");
        StudentGradeDAO studentGradeDAO = (StudentGradeDAO) serviceMap.get("StudentGradeService");
        int flag = studentGradeDAO.deleteStudentGrade(sno, cno);
        String info = null;
        PrintWriter out = response.getWriter();
        if (flag == 1) {
            info = "成功删除" + sno + "号学生"+cno+"号课程成绩！";
        } else {
            info = "错误：删除成绩信息失败！";
        }
        out.write("<div class='error'>");
        out.write("<div>" + info + "</div>");
        out.write("</div>");
        out.flush();
        out.close();
    }
    // 修改成绩信息
    protected void alter_sc(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String sno = request.getParameter("studentId");
        String cno = request.getParameter("courseId");
        int after_grade = Integer.parseInt(request.getParameter("after_grade"));
        StudentGradeDAO studentGradeDAO = (StudentGradeDAO) serviceMap.get("StudentGradeService");
        int flag = studentGradeDAO.updateStudentGrade(sno, cno, after_grade);
        String info = null;
        PrintWriter out = response.getWriter();
        if (flag == 1) {
            info = sno + "号学生" + cno + "号课程成绩修改成功！";
        } else {
            info = "错误：修改学生成绩失败!";
        }
        out.write("<div class='error'>");
        out.write("<div>" + info + "</div>");
        out.write("</div>");
        out.flush();
        out.close();
    }

}
