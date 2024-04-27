package myRPC.common;

import java.io.Serializable;

public class Course_ranking implements Serializable {
    private static final long serialVersionUID = 1L;

    private String studentId; // 学号
    private String departmentName; // 系名
    private String courseName; // 课程名
    private String name; // 学生姓名
    private String gender; // 学生性别
    private int age; // 学生年龄
    private int grade; // 成绩
    private int ranking;

    // 构造函数
    public Course_ranking(String studentId, String departmentName, String courseName, String name, String gender, int age, int grade,int ranking) {
        this.studentId = studentId;
        this.departmentName = departmentName;
        this.courseName = courseName;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.grade = grade;
        this.ranking = ranking;
    }

    // 获取学号
    public String getStudentId() {
        return studentId;
    }

    // 设置学号
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    // 获取系名
    public String getDepartmentName() {
        return departmentName;
    }

    // 设置系名
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    // 获取课程名
    public String getCourseName() {
        return courseName;
    }

    // 设置课程名
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    // 获取学生姓名
    public String getName() {
        return name;
    }

    // 设置学生姓名
    public void setName(String name) {
        this.name = name;
    }

    // 获取学生性别
    public String getGender() {
        return gender;
    }

    // 设置学生性别
    public void setGender(String gender) {
        this.gender = gender;
    }

    // 获取学生年龄
    public int getAge() {
        return age;
    }

    // 设置学生年龄
    public void setAge(int age) {
        this.age = age;
    }

    // 获取成绩
    public int getGrade() {
        return grade;
    }

    // 设置成绩
    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int Ranking) {
        this.ranking = Ranking;
    }
}
