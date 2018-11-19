package ejbs;

import dtos.StudentDTO;
import entities.Student;
import entities.Subject;
import exceptions.InvalidOperationException;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;

@Stateless
public class StudentBean extends BaseBean<Student, StudentDTO> {
    
    public StudentDTO create(String username, String password, String name, String email, Integer courseCode) {
        return create(new StudentDTO(username, password, name, email ,getSubjectsOfStudent(username), courseCode, null));
    }
    
    public List<Subject> getSubjectsOfStudent(String username) {
        return em.createNamedQuery("getSubjectsByStudent", Subject.class)
                .setParameter("username", username)
                .getResultList();
    }
    
    public void enrollStudentInSubject(@NotNull String studentUsername, @NotNull Integer subjectCode) {
        try {
            Student student = findOrFail(studentUsername);
            Subject subject = findOrFail(Subject.class, subjectCode);

            if (subject.getCourse().getCode() != student.getCourse().getCode()) {
                throw new InvalidOperationException("Student course isn't equal to Subject course");
            }

            student.addSubject(subject);
            subject.addStudent(student);
        } catch (InvalidOperationException | EntityNotFoundException e) {
            throw new EJBException(e.getMessage(), e);
        }
    }
}
