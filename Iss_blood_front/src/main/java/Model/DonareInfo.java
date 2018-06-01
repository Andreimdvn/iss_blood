package Model;

public class DonareInfo {
    private Integer numarDonare;
    private String centruDonare;
    private Status status;
    private Analiza analiza;
    private String numeStaff;
    private String data;
    private RH rh;
    private GrupaSange grupaSange;

    public DonareInfo(Integer numarDonare, String centruDonare, Status status, Analiza analiza, String numeStaff, String data, GrupaSange grupaSange, RH rh) {
        this.numarDonare = numarDonare;
        this.centruDonare = centruDonare;
        this.status = status;
        this.analiza = analiza;
        this.numeStaff = numeStaff;
        this.data = data;
        this.rh = rh;
        this.grupaSange = grupaSange;
    }

    public Integer getNumarDonare() {
        return numarDonare;
    }

    public String getCentruDonare() {
        return centruDonare;
    }

    public Status getStatus() {
        return status;
    }

    public Analiza getAnaliza() {
        return analiza;
    }

    public String getNumeStaff() {
        return numeStaff;
    }

    public String getData() {
        return data;
    }

    public RH getRh() {
        return rh;
    }

    public GrupaSange getGrupaSange() {
        return grupaSange;
    }
}
