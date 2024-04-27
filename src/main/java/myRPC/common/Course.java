package myRPC.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course implements Serializable {
    private String courseId;
    private String courseName;
    private String teacherName;
    private int credit;

    public String getCourseId() { // 修改返回类型为String
        return courseId;
    }

    public void setCourseId(String courseId) { // 修改参数类型为String
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacherName() { // 添加获取teacherName的方法
        return teacherName;
    }

    public void setTeacherName(String teacherName) { // 添加设置teacherName的方法
        this.teacherName = teacherName;
    }

    public int getCredit() { // 修改返回类型为int
        return credit;
    }

    public void setCredit(int credit) { // 修改参数类型为int
        this.credit = credit;
    }
}
