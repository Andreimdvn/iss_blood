package Model;

public class Pacient {

    private int idMedic;

    private String nume;

    private String cnp;

    private GrupaSange grupaSange;

    private RH rh;

    public Pacient(String nume, String cnp, GrupaSange grupaSange, RH rh) {
        this.nume = nume;
        this.cnp = cnp;
        this.grupaSange = grupaSange;
        this.rh = rh;
    }

    public int getIdMedic() {
        return idMedic;
    }

    public void setIdMedic(int idMedic) {
        this.idMedic = idMedic;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public GrupaSange getGrupaSange() {
        return grupaSange;
    }

    public void setGrupaSange(GrupaSange grupaSange) {
        this.grupaSange = grupaSange;
    }

    public RH getRh() {
        return rh;
    }

    public void setRh(RH rh) {
        this.rh = rh;
    }
}
