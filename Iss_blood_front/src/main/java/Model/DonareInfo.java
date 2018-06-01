package Model;

public class DonareInfo {
    private Integer numarDonare;
    private String centruDonare;
    private Status status;
    private Analiza analiza;
    private String numeStaff;
    private String data;


    public DonareInfo(Integer numarDonare, String centruDonare, Status status, Analiza analiza, String numeStaff, String data) {
        this.numarDonare = numarDonare;
        this.centruDonare = centruDonare;
        this.status = status;
        this.analiza = analiza;
        this.numeStaff = numeStaff;
        this.data = data;
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
}
