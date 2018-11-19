package ejbs;

import dtos.CourseDTO;
import entities.Course;
import javax.ejb.Stateless;

@Stateless
public class CourseBean extends BaseBean<Course, CourseDTO> {
    public CourseDTO create(int code, String name) {
       return create(new CourseDTO(code, name));
    }
}
