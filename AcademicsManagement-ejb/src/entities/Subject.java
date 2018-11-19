package entities;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "SUBJECTS", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "NAME", "COURSE_CODE", "SCHOLARYEAR" })
})
@NamedQueries({
    @NamedQuery(name = "getAllSubjects", query = "SELECT s "
            +                                    "FROM Subject s "
            +                                    "ORDER BY s.course.name,"
            +                                    " s.scholarYear DESC,"
            +                                    " s.courseYear,"
            +                                    " s.name")
})
public class Subject implements Serializable {
    
    @Id
    @NotNull
    private int code;

    @NotNull
    @Column(nullable = false)
    private String name;
    
    @NotNull
    private String courseYear;
    
    @NotNull
    private String scholarYear;
    
    @ManyToOne
    private Course course;
    
    @ManyToMany(mappedBy = "subjects", cascade = CascadeType.ALL)
    private List<Teacher> teachers;
    
    @ManyToMany(mappedBy = "subjects", cascade = CascadeType.ALL)
    private List<Student> students;
    
    
    public Subject() {
        students = new LinkedList<>();
        teachers = new LinkedList<>();
    }

    public Subject(int code, String name, Course course, String courseYear, String scholarYear) {
        this();
        
        this.code = code;
        this.name = name;
        this.course = course;
        this.courseYear = courseYear;
        this.scholarYear = scholarYear;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Course getCourse() {
        return course;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
    
    public void setCourse(Course course) {
        this.course = course;
    }

    public String getCourseYear() {
        return courseYear;
    }

    public void setCourseYear(String courseYear) {
        this.courseYear = courseYear;
    }

    public String getScholarYear() {
        return scholarYear;
    }

    public void setScholarYear(String scholarYear) {
        this.scholarYear = scholarYear;
    }
    
    public void addStudent(Student student) {
        if (! students.contains(student)) {
            students.add(student);
        }
    }
    
    public void removeStudent(Student student) {
        students.remove(student);
    }
    
    public void addTeacher(Teacher teacher) {
        if (! teachers.contains(teacher)) {
            teachers.add(teacher);
        }
    }
    
    public void removeTeacher(Teacher teacher) {
        teachers.remove(teacher);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Subject && ((Subject) obj).code == code;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.code;
        return hash;
    }
}
