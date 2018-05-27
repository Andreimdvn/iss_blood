package Model;

public class FormularDonare {
    private String nume;
    private String prenume;
    private Sex sex;
    private String telefon;
    private String domiciliuLocalitate;
    private String domiciliuJudet;
    private String domiciliuAdresa;
    private String resedintaLocalitate;
    private String resedintaJudet;
    private String resedintaAdresa;
    String beneficiarFullName;
    String beneficiarCNP;
    GrupaSange grupa;
    RH rh;
    private short zileDisponibil; //de ex: 00011 = luni si marti

    private Status status;


    public FormularDonare(String nume, String prenume, Sex sex, String telefon,
                          String domiciliuLocalitate, String domiciliuJudet, String domiciliuAdresa,
                          String resedintaLocalitate, String resedintaJudet, String resedintaAdresa,
                          String beneficiarFullName, String beneficiarCNP, GrupaSange grupa, RH rh,
                          short zileDisponibil) {
        this.nume = nume;
        this.prenume = prenume;
        this.sex = sex;
        this.telefon = telefon;
        this.domiciliuLocalitate = domiciliuLocalitate;
        this.domiciliuJudet = domiciliuJudet;
        this.domiciliuAdresa = domiciliuAdresa;
        this.resedintaLocalitate = resedintaLocalitate;
        this.resedintaJudet = resedintaJudet;
        this.resedintaAdresa = resedintaAdresa;
        this.beneficiarFullName = beneficiarFullName;
        this.beneficiarCNP = beneficiarCNP;
        this.grupa = grupa;
        this.rh = rh;
        this.zileDisponibil = zileDisponibil;
        this.status = Status.IN_ASTEPTARE;
    }

    public String getBeneficiarFullName() {
        return beneficiarFullName;
    }

    public String getBeneficiarCNP() {
        return beneficiarCNP;
    }

    public GrupaSange getGrupa() {
        return grupa;
    }

    public RH getRh() {
        return rh;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public Sex getSex() {
        return sex;
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

    public short getZileDisponibil() {
        return zileDisponibil;
    }

    public Status getStatus() {
        return status;
    }
}
