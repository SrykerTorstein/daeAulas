package ejbs;

import dtos.TeacherDTO;
import entities.Subject;
import entities.Teacher;
import java.util.List;
import javax.ejb.Stateless;

@Stateless
public class TeacherBean extends BaseBean<Teacher, TeacherDTO> {
    
    public Teacher create(String username, String password, String name, String email, String office) {
        Teacher teacher = new Teacher(username, password, name, email, office);
        
        em.persist(teacher);
        
        return teacher;
    }

    public List<Subject> getSubjectsOfTeacher(String username) {
        return em.createNamedQuery("subjects", Subject.class).setParameter("username", username).getResultList();
    }
}
