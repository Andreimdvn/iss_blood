package Model;

public class RegisterInfo {
    private String username;
    private String password;
    private String email;
    private String fullname;
    private String address;
    private String phone;
    private UserType userType;

    public RegisterInfo(String username, String password, String email, String fullname, String address, String phone, String userTypeString) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullname = fullname;
        this.address = address;
        this.phone = phone;

        UserType userType;
        userTypeString = userTypeString.toLowerCase();

        if(userTypeString.contains("donator"))
            userType = UserType.Donator;
        else if(userTypeString.contains("medic"))
            userType = UserType.Medic;
        else if(userTypeString.contains("recoltare"))
            userType = UserType.StaffRecoltare;
        else
            userType = UserType.StaffTransfuzie;

        this.userType = userType;
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

    public UserType getUserType() {
        return userType;
    }
}
