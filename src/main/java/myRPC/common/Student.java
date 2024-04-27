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
public class Student implements Serializable {
    private String studentId;
    private String name;
    private String gender;
    private int age; // 将age属性改为String类型
    private String classId; // 将classId属性改为String类型

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) { // 将参数和返回类型改为String
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() { // 将返回类型改为String
        return age;
    }

    public void setAge(int age) { // 将参数和返回类型改为String
        this.age = age;
    }

    public String getClassId() { // 将返回类型改为String
        return classId;
    }

    public void setClassId(String classId) { // 将参数和返回类型改为String
        this.classId = classId;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", age='" + age + '\'' +
                ", classId='" + classId + '\'' +
                '}';
    }
}
