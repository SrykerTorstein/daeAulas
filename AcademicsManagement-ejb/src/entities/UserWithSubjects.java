package entities;

import java.util.LinkedList;
import java.util.List;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class UserWithSubjects extends User {
    
    @ManyToMany
    protected List<Subject> subjects;
    
    public UserWithSubjects() {
        subjects = new LinkedList<>();
    }

    public UserWithSubjects(String username, String password, String name, String email) {
        super(username, password, name, email);
        subjects = new LinkedList<>();
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public void addSubject(Subject subject) {
        if (!subjects.contains(subject)) {
            subjects.add(subject);
        }
    }

    public void removeSubject(Subject subject) {
        subjects.remove(subject);
    }
    
}
