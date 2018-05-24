package Model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StarePacient {
    private String numePacient;
    private String cnpPacient;
    private GrupaSange grupaSange;
    private RH rh;
    private Integer numarCereri;
    private Integer donatoriPreferentiali;


    private Logger logger = LogManager.getLogger(StarePacient.class.getName());

    @Override
    public String toString() {
        return "StarePacient{" +
                "numePacient='" + numePacient + '\'' +
                ", cnpPacient='" + cnpPacient + '\'' +
                ", grupaSange=" + grupaSange +
                ", rh=" + rh +
                ", numarCereri=" + numarCereri +
                ", donatoriPreferentiali=" + donatoriPreferentiali +
                '}';
    }

    public String getNumePacient() {
        return numePacient;
    }

    public void setNumePacient(String numePacient) {
        this.numePacient = numePacient;
    }

    public String getCnpPacient() {
        return cnpPacient;
    }

    public void setCnpPacient(String cnpPacient) {
        this.cnpPacient = cnpPacient;
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

    public StarePacient(String numePacient, String cnpPacient, GrupaSange grupaSange, RH rh, Integer numarCereri, Integer donatoriPreferentiali) {
        this.numePacient = numePacient;
        this.cnpPacient = cnpPacient;
        this.grupaSange = grupaSange;
        this.rh = rh;
        this.numarCereri = numarCereri;
        this.donatoriPreferentiali = donatoriPreferentiali;
    }
}
