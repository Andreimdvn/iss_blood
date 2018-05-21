package Model;

public class DonatorInfo extends UserInfo {
    private String nume;
    private String prenume;
    private String cnp;
    private String telefon;
    private String domiciliuLocalitate;
    private String domiciliuJudet;
    private String domiciliuAdresa;
    private String resedintaLocalitate;
    private String resedintaJudet;
    private String resedintaAdresa;

    public DonatorInfo(String username, String nume, String prenume, String cnp, String telefon, String domiciliuLocalitate, String domiciliuJudet, String domiciliuAdresa, String resedintaLocalitate, String resedintaJudet, String resedintaAdresa) {
        super(username);
        this.nume = nume;
        this.prenume = prenume;
        this.cnp = cnp;
        this.telefon = telefon;
        this.domiciliuLocalitate = domiciliuLocalitate;
        this.domiciliuJudet = domiciliuJudet;
        this.domiciliuAdresa = domiciliuAdresa;
        this.resedintaLocalitate = resedintaLocalitate;
        this.resedintaJudet = resedintaJudet;
        this.resedintaAdresa = resedintaAdresa;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getCnp() {
        return cnp;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getDomiciliuLocalitate() {
        return domiciliuLocalitate;
    }

    public String getDomiciliuJudet() {
        return domiciliuJudet;
    }

    public String getDomiciliuAdresa() {
        return domiciliuAdresa;
    }

    public String getResedintaLocalitate() {
        return resedintaLocalitate;
    }

    public String getResedintaJudet() {
        return resedintaJudet;
    }

    public String getResedintaAdresa() {
        return resedintaAdresa;
    }
}
