package Model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;

public class CerereSange{
    private Integer id;
    private String numePacient;

    public CerereSange(Integer id, String numePacient, String cnpPacient, GrupaSange grupaSange, RH rh, Integer numarPungiTrombocite, Integer numarPungiGlobuleRosii, Integer numarPungiPlasma, Date date, Importanta importanta, String numeMedic, String spital) {
        this.id = id;
        this.numePacient = numePacient;
        this.cnpPacient = cnpPacient;
        this.grupaSange = grupaSange;
        this.rh = rh;
        this.numarPungiTrombocite = numarPungiTrombocite;
        this.numarPungiGlobuleRosii = numarPungiGlobuleRosii;
        this.numarPungiPlasma = numarPungiPlasma;
        this.date = date;
        this.importanta = importanta;
        this.numeMedic = numeMedic;
        this.spital = spital;
    }

    private String cnpPacient;
    private GrupaSange grupaSange;
    private RH rh;
    private Integer numarPungiTrombocite;
    private Integer numarPungiGlobuleRosii;
    private Integer numarPungiPlasma;

    public CerereSange(Integer id, String numePacient, String cnpPacient, GrupaSange grupaSange, RH rh, Integer numarPungiTrombocite, Integer numarPungiGlobuleRosii, Integer numarPungiPlasma, Date date, Importanta importanta, String numeMedic, String spital, String status) {
        this.id = id;
        this.numePacient = numePacient;
        this.cnpPacient = cnpPacient;
        this.grupaSange = grupaSange;
        this.rh = rh;
        this.numarPungiTrombocite = numarPungiTrombocite;
        this.numarPungiGlobuleRosii = numarPungiGlobuleRosii;
        this.numarPungiPlasma = numarPungiPlasma;
        this.date = date;
        this.importanta = importanta;
        this.numeMedic = numeMedic;
        this.spital = spital;
        this.status = status;
    }

    private Date date;
    private Importanta importanta;
    private String numeMedic;
    private String spital;
    private String status;

    private Logger logger = LogManager.getLogger(CerereSange.class.getName());

    public CerereSange(String numePacient, String cnpPacient, GrupaSange grupaSange, RH rh, Integer numarPungiTrombocite, Integer numarPungiGlobuleRosii, Integer numarPungiPlasma, Date date, Importanta importanta, String numeMedic, String spital) {
        this.cnpPacient = cnpPacient;
        this.grupaSange = grupaSange;
        this.numePacient = numePacient;
        this.rh = rh;
        this.numarPungiTrombocite = numarPungiTrombocite;
        this.numarPungiGlobuleRosii = numarPungiGlobuleRosii;
        this.numarPungiPlasma = numarPungiPlasma;
        this.date = date;
        this.importanta = importanta;
        this.numeMedic = numeMedic;
        this.spital = spital;
        this.status = "pl";
    }

    public String getNumeMedic() {

        return numeMedic;
    }

    public void setNumeMedic(String numeMedic) {
        this.numeMedic = numeMedic;
    }

    public String getSpital() {
        return spital;
    }

    public void setSpital(String spital) {
        this.spital = spital;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public Importanta getImportanta() {
        return importanta;
    }

    public void setImportanta(Importanta importanta) {
        this.importanta = importanta;
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

    public CerereSange(String numePacient, String cnpPacient, GrupaSange grupaSange, RH rh, Integer numarPungiTrombocite, Integer numarPungiGlobuleRosii, Integer numarPungiPlasma, Importanta importanta) {
        this.numePacient = numePacient;
        this.cnpPacient = cnpPacient;
        this.grupaSange = grupaSange;
        this.rh = rh;
        this.numarPungiTrombocite = numarPungiTrombocite;
        this.numarPungiGlobuleRosii = numarPungiGlobuleRosii;
        this.numarPungiPlasma = numarPungiPlasma;
        this.importanta = importanta;
        this.status="pl";

        this.logger.debug(toString());
    }

    @Override
    public String toString() {
        return "CerereSange{" +
                "numePacient='" + numePacient + '\'' +
                ", cnpPacient='" + cnpPacient + '\'' +
                ", grupaSange=" + grupaSange +
                ", rh=" + rh +
                ", numarPungiTrombocite=" + numarPungiTrombocite +
                ", numarPungiGlobuleRosii=" + numarPungiGlobuleRosii +
                ", numarPungiPlasma=" + numarPungiPlasma +
                ", date=" + date +
                ", importanta=" +
                '}';
    }

    public CerereSange(String numePacient, String cnpPacient, GrupaSange grupaSange, RH rh, Integer numarPungiTrombocite, Integer numarPungiGlobuleRosii, Integer numarPungiPlasma, Date date, Importanta importanta) {

        this.numePacient = numePacient;
        this.cnpPacient = cnpPacient;
        this.grupaSange = grupaSange;
        this.rh = rh;
        this.numarPungiTrombocite = numarPungiTrombocite;
        this.numarPungiGlobuleRosii = numarPungiGlobuleRosii;
        this.numarPungiPlasma = numarPungiPlasma;
        this.date = date;
        this.importanta = importanta;
    }

    public Integer getId() {
        return id;
    }
}
