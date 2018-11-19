package dtos;

import entities.Subject;

import java.util.List;

public class StudentDTO extends UserDTO {
    
    private Integer courseCode;
    private String courseName;
    private List<Subject> subjects;

    public StudentDTO() {
        
    }
    
    public StudentDTO(String username, String password, String name, String email,List<Subject> subjects, int courseCode, String courseName) {
        super(username, password, name, email);
        this.subjects = subjects;
        this.courseCode = courseCode;
        this.courseName = courseName;
    }
    
    public Integer getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(Integer courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    @Override
    public void clear() {
        super.clear();
        courseCode = null;
        courseName = null;
    }
}
