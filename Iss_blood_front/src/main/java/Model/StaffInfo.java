package Model;

public class StaffInfo extends UserInfo {
    private String nume;
    private String prenume;
    private Integer idLocatie;
    private String numeLocatie;
    private String numeJudet;
    private Integer idJudet;

    public StaffInfo(String username, String nume, String prenume, Integer idLocatie, String numeLocatie,
                     String numeJudet, Integer idJudet) {
        super(username);
        this.nume = nume;
        this.prenume = prenume;
        this.idLocatie = idLocatie;
        this.numeLocatie = numeLocatie;
        this.numeJudet = numeJudet;
        this.idJudet = idJudet;
    }

    public String getNumeJudet() {
        return numeJudet;
    }

    public Integer getIdJudet() {
        return idJudet;
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

    public String getNumeLocatie() {
        return numeLocatie;
    }
}
