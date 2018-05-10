package Model;

public class RegisterInfo {
    private String username;
    private String password;
    private String email;
    private String nume;
    private String prenume;
    private String cnp;
    private String judet;
    private String localitate;
    private String address;
    private String phone;
    private AccountType accountType;
    private String licence;

    public RegisterInfo(String username, String password, String email, String nume, String prenume, String cnp, String judet, String localitate, String address, String phone, String accountTypeString, String licence) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nume = nume;
        this.prenume = prenume;
        this.cnp = cnp;
        this.judet = judet;
        this.localitate = localitate;
        this.address = address;
        this.phone = phone;
        this.licence = licence;

        AccountType accountType;
        accountTypeString = accountTypeString.toLowerCase();

        if(accountTypeString.contains("donator"))
            accountType = AccountType.Donator;
        else if(accountTypeString.contains("medic"))
            accountType = AccountType.Medic;
        else
            accountType = AccountType.StaffTransfuzie;

        this.accountType = accountType;
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

    public String getNume() {
        return nume;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public String getLicence() {
        return licence;
    }

    public String getLocalitate() {
        return localitate;
    }

    public String getJudet() {
        return judet;
    }

    public String getCnp() {
        return cnp;
    }
}
