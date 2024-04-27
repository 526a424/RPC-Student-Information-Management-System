package myRPC.common;

import java.io.Serializable;

public class Course_avg implements Serializable {
    private static final long serialVersionUID = 1L;

    private String courseID; // 课程号
    private String courseName; // 课程名称
    private int avg; // 平均分

    // 构造函数
    public Course_avg(String courseID, String courseName, int avg) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.avg = avg;
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

    // 获取平均分
    public int getAvg() {
        return avg;
    }

    // 设置平均分
    public void setAvg(int avg) {
        this.avg = avg;
    }
}
