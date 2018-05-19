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
    private short daysAvailable;

    public FormularDonare(String nume, String prenume, Sex sex, String telefon, String domiciliuLocalitate, String domiciliuJudet, String domiciliuAdresa, String resedintaLocalitate, String resedintaJudet, String resedintaAdresa, short daysAvailable) {
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
        this.daysAvailable = daysAvailable;
    }
}
