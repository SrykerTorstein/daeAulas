package entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ADMINISTRATORS")
public class Administrator extends User {

    String role;

    public Administrator() {

    }
    
    public Administrator(String username, String password, String name, String role, String email) {
        super(username, password, name, email);
        this.role = role;
    }
}
