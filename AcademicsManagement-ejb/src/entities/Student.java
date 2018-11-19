package entities;

import java.util.LinkedList;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "STUDENTS")
@NamedQueries(value = {
    @NamedQuery(name = "getAllStudents",
        query = "SELECT s FROM Student s ORDER BY s.name"),
    
    @NamedQuery(name = "getSubjectsByStudent",
        query = "SELECT s.subjects FROM Student s WHERE s.username = :username")
    })
public class Student extends UserWithSubjects {
    
    @ManyToOne
    @JoinColumn(name="COURSE_CODE")
    @NotNull
    private Course course;
    
    protected Student() {
        
    }

    public Student(String username, String password, String name, String email, Course course) {
        super(username, password, name, email);
        
        this.subjects = new LinkedList<>();
        this.course = course;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
