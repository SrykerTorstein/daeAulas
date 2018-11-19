package ejbs;

import dtos.SubjectDTO;
import entities.Subject;
import javax.ejb.Stateless;

@Stateless
public class SubjectBean extends BaseBean<Subject, SubjectDTO> {
    
    public SubjectDTO create(Integer code, String name, int courseCode, String courseYear, String scholarYear) {
        return create(new SubjectDTO(code, name, courseCode, null, courseYear, scholarYear));
    }
    
}
