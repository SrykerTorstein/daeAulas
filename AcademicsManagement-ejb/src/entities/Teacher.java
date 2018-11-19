package entities;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "TEACHERS")
@NamedQueries({
    @NamedQuery(name = "subjects", query = "SELECT t.subjects FROM Teacher t WHERE t.username = :username")
})
public class Teacher extends UserWithSubjects {
    
    @NotNull
    private String office;

    public Teacher() {
        super();
    }

    public Teacher(String username, String password, String name, String email, String office) {
        super(username, password, name, email);
        this.office = office;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

}
