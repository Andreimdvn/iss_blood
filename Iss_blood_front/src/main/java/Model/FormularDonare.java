package Model;

public class FormularDonare {
    private int id;
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

    public FormularDonare(int id, String nume, String prenume, Sex sex, String telefon, String domiciliuLocalitate, String domiciliuJudet, String domiciliuAdresa, String resedintaLocalitate, String resedintaJudet, String resedintaAdresa, String beneficiarFullName, String beneficiarCNP, GrupaSange grupa, RH rh, Status status) {
        this.id = id;
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
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public FormularDonare(int id, String nume, String prenume, Sex sex, String telefon, String domiciliuLocalitate, String domiciliuJudet, String domiciliuAdresa, String resedintaLocalitate, String resedintaJudet, String resedintaAdresa, String beneficiarFullName, String beneficiarCNP, GrupaSange grupa, RH rh, short zileDisponibil, Status status) {
        this.id = id;
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
        this.status = status;
    }



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

    public FormularDonare(String nume, String prenume, Sex sex, String telefon, String domiciliuLocalitate, String domiciliuJudet, String domiciliuAdresa, String resedintaLocalitate, String resedintaJudet, String resedintaAdresa, String beneficiarFullName, String beneficiarCNP, GrupaSange grupa, RH rh, Status status) {
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
        this.status = status;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public FormularDonare(String nume, String prenume, Sex sex, String telefon, String domiciliuLocalitate, String domiciliuJudet, String domiciliuAdresa, String resedintaLocalitate, String resedintaJudet, String resedintaAdresa, String beneficiarFullName, String beneficiarCNP, GrupaSange grupa, RH rh, short zileDisponibil, Status status) {
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
        this.status = status;
    }

    public void setPrenume(String prenume) {

        this.prenume = prenume;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public void setDomiciliuLocalitate(String domiciliuLocalitate) {
        this.domiciliuLocalitate = domiciliuLocalitate;
    }

    public void setDomiciliuJudet(String domiciliuJudet) {
        this.domiciliuJudet = domiciliuJudet;
    }

    public void setDomiciliuAdresa(String domiciliuAdresa) {
        this.domiciliuAdresa = domiciliuAdresa;
    }

    public void setResedintaLocalitate(String resedintaLocalitate) {
        this.resedintaLocalitate = resedintaLocalitate;
    }

    public void setResedintaJudet(String resedintaJudet) {
        this.resedintaJudet = resedintaJudet;
    }

    public void setResedintaAdresa(String resedintaAdresa) {
        this.resedintaAdresa = resedintaAdresa;
    }

    public void setBeneficiarFullName(String beneficiarFullName) {
        this.beneficiarFullName = beneficiarFullName;
    }

    public void setBeneficiarCNP(String beneficiarCNP) {
        this.beneficiarCNP = beneficiarCNP;
    }

    public void setGrupa(GrupaSange grupa) {
        this.grupa = grupa;
    }

    public void setRh(RH rh) {
        this.rh = rh;
    }

    public void setZileDisponibil(short zileDisponibil) {
        this.zileDisponibil = zileDisponibil;
    }

    public void setStatus(Status status) {
        this.status = status;
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
