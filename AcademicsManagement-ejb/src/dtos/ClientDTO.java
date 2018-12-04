package dtos;

public class ClientDTO extends UserDTO {

    private String companyName;
    private String address;


    public ClientDTO(String username, String password, String contactName, String address, String companyName) {
        super(username, password, contactName);
        this.companyName = companyName;
        this.address = address;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
