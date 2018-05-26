package Model;

public class FormularDonare {
    private String nume;
    private String prenume;
    private Sex sex;
    private String phone;
    private String domiciliuLocalitate;
    private String domiciliuJudet;
    private String domiciliuAdresa;
    private String resedintaLocalitate;
    private String resedintaJudet;
    private String resedintaAdresa;
    private GrupaSange grupaSange;
    private RH rh;
    private Status status;

    public FormularDonare(String nume, String prenume, GrupaSange grupaSange, RH rh, Status status) {
        this.nume = nume;
        this.prenume = prenume;
        this.grupaSange = grupaSange;
        this.rh = rh;
        this.status = status;
    }


    public FormularDonare(String nume, String prenume, Sex sex, String phone, String domiciliuLocalitate, String domiciliuJudet, String domiciliuAdresa, String resedintaLocalitate, String resedintaJudet, String resedintaAdresa, Status status) {
        this.nume = nume;
        this.prenume = prenume;
        this.sex = sex;
        this.phone = phone;
        this.domiciliuLocalitate = domiciliuLocalitate;
        this.domiciliuJudet = domiciliuJudet;
        this.domiciliuAdresa = domiciliuAdresa;
        this.resedintaLocalitate = resedintaLocalitate;
        this.resedintaJudet = resedintaJudet;
        this.resedintaAdresa = resedintaAdresa;
        this.rh = RH.UNKNOWN;
        this.grupaSange = GrupaSange.UNKNOWN;
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public FormularDonare(String nume, String prenume, Sex sex, String phone, String domiciliuLocalitate, String domiciliuJudet, String domiciliuAdresa, String resedintaLocalitate, String resedintaJudet, String resedintaAdresa, GrupaSange grupaSange, RH rh, Status status) {

        this.nume = nume;
        this.prenume = prenume;
        this.sex = sex;
        this.phone = phone;
        this.domiciliuLocalitate = domiciliuLocalitate;
        this.domiciliuJudet = domiciliuJudet;
        this.domiciliuAdresa = domiciliuAdresa;
        this.resedintaLocalitate = resedintaLocalitate;
        this.resedintaJudet = resedintaJudet;
        this.resedintaAdresa = resedintaAdresa;
        this.grupaSange = grupaSange;
        this.rh = rh;
        this.status = status;

    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDomiciliuLocalitate() {
        return domiciliuLocalitate;
    }

    public void setDomiciliuLocalitate(String domiciliuLocalitate) {
        this.domiciliuLocalitate = domiciliuLocalitate;
    }

    public String getDomiciliuJudet() {
        return domiciliuJudet;
    }

    public void setDomiciliuJudet(String domiciliuJudet) {
        this.domiciliuJudet = domiciliuJudet;
    }

    public String getDomiciliuAdresa() {
        return domiciliuAdresa;
    }

    public void setDomiciliuAdresa(String domiciliuAdresa) {
        this.domiciliuAdresa = domiciliuAdresa;
    }

    public String getResedintaLocalitate() {
        return resedintaLocalitate;
    }

    public void setResedintaLocalitate(String resedintaLocalitate) {
        this.resedintaLocalitate = resedintaLocalitate;
    }

    public String getResedintaJudet() {
        return resedintaJudet;
    }

    public void setResedintaJudet(String resedintaJudet) {
        this.resedintaJudet = resedintaJudet;
    }

    public String getResedintaAdresa() {
        return resedintaAdresa;
    }

    public void setResedintaAdresa(String resedintaAdresa) {
        this.resedintaAdresa = resedintaAdresa;
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
