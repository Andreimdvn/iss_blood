package Model;

public class RegisterInfo {
    private String username;
    private String password;
    private String email;
    private String fullname;
    private String address;
    private String phone;

    public RegisterInfo(String username, String password, String email, String fullname, String address, String phone) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullname = fullname;
        this.address = address;
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFullname() {
        return fullname;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
}
