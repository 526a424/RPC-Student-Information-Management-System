package myRPC.common;

import java.io.Serializable;

public class Course_fail_rate implements Serializable {
    private static final long serialVersionUID = 1L;

    private String courseID; // 课程号
    private String courseName; // 课程名称
    private int failCount; // 不及格数
    private double fail_rate; // 不及格率

    // 构造函数
    public Course_fail_rate(String courseID, String courseName, int failCount, double fail_rate) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.failCount = failCount;
        this.fail_rate = fail_rate;
    }

    // 获取课程号
    public String getCourseID() {
        return courseID;
    }

    // 设置课程号
    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    // 获取课程名称
    public String getCourseName() {
        return courseName;
    }

    // 设置课程名称
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    // 获取不及格数
    public int getFailCount() {
        return failCount;
    }

    // 设置不及格数
    public void setFailCount(int failCount) {
        this.failCount = failCount;
    }

    // 获取不及格率
    public double getFail_rate() {
        return fail_rate;
    }

    // 设置不及格率
    public void setFail_rate(double fail_rate) {
        this.fail_rate = fail_rate;
    }
}
