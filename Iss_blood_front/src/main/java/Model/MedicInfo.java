package Model;

public class MedicInfo extends UserInfo {
    private String nume;
    private String prenume;
    private Integer idLocatie;
    private String cnp;

    public MedicInfo(String username, String nume, String prenume, Integer idLocatie, String cnp) {
        super(username);
        this.nume = nume;
        this.prenume = prenume;
        this.idLocatie = idLocatie;
        this.cnp = cnp;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public Integer getIdLocatie() {
        return idLocatie;
    }

    public String getFullName(){
        return nume + " " +  prenume;
    }

    public String getCnp() {
        return cnp;
    }
}
