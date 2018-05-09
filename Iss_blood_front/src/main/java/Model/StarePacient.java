package Model;

public class StarePacient {
    String numePacient;
    GrupaSange grupaSange;
    RH rh;
    Integer numarPungiTrombocite;
    Integer numarPungiGlobuleRosii;
    Integer numarPungiPlasma;
    Integer numarCereri;
    Integer donatoriPreferentiali;
    Importanta importanta;

    public StarePacient(String numePacient, GrupaSange grupaSange, RH rh, Integer numarPungiTrombocite, Integer numarPungiGlobuleRosii, Integer numarPungiPlasma, Integer numarCereri, Integer donatoriPreferentiali, Importanta importanta) {
        this.numePacient = numePacient;
        this.grupaSange = grupaSange;
        this.rh = rh;
        this.numarPungiTrombocite = numarPungiTrombocite;
        this.numarPungiGlobuleRosii = numarPungiGlobuleRosii;
        this.numarPungiPlasma = numarPungiPlasma;
        this.numarCereri = numarCereri;
        this.donatoriPreferentiali = donatoriPreferentiali;
        this.importanta = importanta;
    }

    public String getNumePacient() {

        return numePacient;
    }

    public void setNumePacient(String numePacient) {
        this.numePacient = numePacient;
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

    public Integer getNumarPungiTrombocite() {
        return numarPungiTrombocite;
    }

    public void setNumarPungiTrombocite(Integer numarPungiTrombocite) {
        this.numarPungiTrombocite = numarPungiTrombocite;
    }

    public Integer getNumarPungiGlobuleRosii() {
        return numarPungiGlobuleRosii;
    }

    public void setNumarPungiGlobuleRosii(Integer numarPungiGlobuleRosii) {
        this.numarPungiGlobuleRosii = numarPungiGlobuleRosii;
    }

    public Integer getNumarPungiPlasma() {
        return numarPungiPlasma;
    }

    public void setNumarPungiPlasma(Integer numarPungiPlasma) {
        this.numarPungiPlasma = numarPungiPlasma;
    }

    public Integer getNumarCereri() {
        return numarCereri;
    }

    public void setNumarCereri(Integer numarCereri) {
        this.numarCereri = numarCereri;
    }

    public Integer getDonatoriPreferentiali() {
        return donatoriPreferentiali;
    }

    public void setDonatoriPreferentiali(Integer donatoriPreferentiali) {
        this.donatoriPreferentiali = donatoriPreferentiali;
    }

    public Importanta getImportanta() {
        return importanta;
    }

    public void setImportanta(Importanta importanta) {
        this.importanta = importanta;
    }
}
