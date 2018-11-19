package dtos;

public class TeacherDTO extends UserDTO {

    private String office;

    public TeacherDTO(String username, String password, String name, String email) {
        super(username, password, name, email);
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }
        
    @Override
    public void clear() {
        super.clear(); //To change body of generated methods, choose Tools | Templates.
        office = null;
    }
    
}
