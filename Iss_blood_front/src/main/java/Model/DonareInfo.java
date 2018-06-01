package Model;

public class DonareInfo {
    private Integer numarDonare;
    private String centruDonare;
    private Status status;
    private Analiza analiza;

    public DonareInfo(Integer numarDonare, String centruDonare, Status status, Analiza analiza) {
        this.numarDonare = numarDonare;
        this.centruDonare = centruDonare;
        this.status = status;
        this.analiza = analiza;
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
}
