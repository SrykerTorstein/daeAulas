package entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CLIENTS")
public class Client extends User {

    private String companyName;
    private String address;

    public Client() {

    }

    public Client(String username, String password, String contactName, String address, String companyName) {
        super(username, password, contactName);
        this.companyName = companyName;
        this.address = address;
    }
}