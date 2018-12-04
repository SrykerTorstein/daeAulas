package web;

import dtos.*;
import ejbs.AdministratorBean;
import ejbs.ClientBean;
import ejbs.CourseBean;
import ejbs.StudentBean;
import entities.Subject;
import entities.User;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;
import java.util.List;
import java.util.logging.Logger;

@ManagedBean(name = "administratorManager")
@SessionScoped
public class AdministratorManager {
    
    @EJB
    private AdministratorBean administratorBean;
    @EJB
    private ClientBean clientBean;
    
    private static final Logger logger = Logger.getLogger("web.AdministratorManager");
    
    private UIComponent component;
    
    private UserDTO newUser;
    private UserDTO currentUser;

    //Problem: Admin manager not being instantiated!
    public AdministratorManager() {
        newUser = new UserDTO();
        currentUser = new UserDTO();
    }

    /*
    /////////////// STUDENTS /////////////////
    public String createStudent() {
        try {
            studentBean.create(newStudent);
            //studentBean.create(new StudentDTO("foo new", "foo", "Foo New", "foonew@ipleiria.pt", 1,null));
            newStudent.clear();
        } catch (EJBException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), component, logger);
            return "admin_students_create";
        } catch (Exception e) {
            logger.warning("Unexpected error. Try again latter!");
            return "admin_students_create";
        }
        return "index?faces-redirect=true";
    }

    public List<StudentDTO> getAllStudents() {
        try {
            return studentBean.getAll();
        } catch (EJBException e) {
            logger.warning(e.getMessage());
            return null;
        } catch (Exception e) {
            logger.warning("Unexpected error. Try again latter!");
            return null;
        }
    }
    
    public List<Subject> getSubjectsOfStudent() {
        return studentBean.getSubjectsOfStudent(currentStudent.getUsername());
    }

    public String updateStudent() {
        try {
            studentBean.update(currentStudent);
            currentStudent.clear();
            return "index?faces-redirect=true";
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
        return "admin_students_update";
    }

    public void removeStudent(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("deleteStudentId");
            String id = param.getValue().toString();
            studentBean.remove(id);
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }

    public List<CourseDTO> getAllCourses() {
        try {
            return courseBean.getAll();
        } catch (Exception e) {
            logger.warning(e.getMessage());
            return null;
        }
    }
    
    public void removeCourse(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("deleteCourseId");
            Integer id = (Integer) param.getValue();
            courseBean.remove(id);
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }

    public StudentBean getStudentBean() {
        return studentBean;
    }

    public void setStudentBean(StudentBean studentBean) {
        this.studentBean = studentBean;
    }

    public StudentDTO getNewStudent() {
        return newStudent;
    }

    public void setNewStudent(StudentDTO newStudent) {
        this.newStudent = newStudent;
    }

    public StudentDTO getCurrentStudent() {
        return currentStudent;
    }

    public void setCurrentStudent(StudentDTO currentStudent) {
        this.currentStudent = currentStudent;
    }

    */

    //CLIENTS
    public String createClient() {
        try {
            clientBean.create((ClientDTO) newUser);
            newUser.clear();
        } catch (EJBException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), component, logger);
            return "admin_clients_create";
        } catch (Exception e) {
            logger.warning("Unexpected error. Try again latter!");
            return "admin_clients_create";
        }
        return "index?faces-redirect=true";
    }

    public List<ClientDTO> getAllClients() {
        try {
            return clientBean.getAll();
        } catch (EJBException e) {
            logger.warning(e.getMessage());
            return null;
        } catch (Exception e) {
            logger.warning("Unexpected error. Try again latter!");
            return null;
        }
    }

    public String updateClient() {
        try {
            clientBean.update((ClientDTO)currentUser);
            currentUser.clear();
            return "index?faces-redirect=true";
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
        return "admin_clients_update";
    }

    public void removeClient(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("deleteClientId");
            String id = param.getValue().toString();
            clientBean.remove(id);
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }

    public UIComponent getComponent() {
        return component;
    }

    public void setComponent(UIComponent component) {
        this.component = component;
    }
}
