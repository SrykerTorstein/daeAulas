package ejbs;

import dtos.AdministratorDTO;
import entities.Administrator;
import javax.ejb.Stateless;

@Stateless
public class AdministratorBean extends BaseBean<Administrator, AdministratorDTO> {

    public Administrator create(String username, String password, String name, String email) {
        Administrator admin = new Administrator(username, password, name, email);
        
        em.persist(admin);
        
        return admin;
    }    
}
