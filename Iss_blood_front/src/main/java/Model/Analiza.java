package Model;

public class Analiza {
    private Integer idAnaliza;
    private Boolean ALT;
    private Boolean SIF;
    private Boolean ANTIHTLV;
    private Boolean ANTIHCV;
    private Boolean ANTIHIV;
    private Boolean HB;
    private GrupaSange grupaSange;
    private RH rh;

    public Analiza(Integer idAnaliza, Boolean ALT, Boolean SIF, Boolean ANTIHTLV, Boolean ANTIHCV, Boolean ANTIHIV, Boolean HB, GrupaSange grupaSange, RH rh) {
        this.idAnaliza = idAnaliza;
        this.ALT = ALT;
        this.SIF = SIF;
        this.ANTIHTLV = ANTIHTLV;
        this.ANTIHCV = ANTIHCV;
        this.ANTIHIV = ANTIHIV;
        this.HB = HB;
        this.grupaSange = grupaSange;
        this.rh = rh;
    }

    public Integer getIdAnaliza() {
        return idAnaliza;
    }

    public void setIdAnaliza(Integer idAnaliza) {
        this.idAnaliza = idAnaliza;
    }

    public Boolean getALT() {
        return ALT;
    }

    public void setALT(Boolean ALT) {
        this.ALT = ALT;
    }

    public Boolean getSIF() {
        return SIF;
    }

    public void setSIF(Boolean SIF) {
        this.SIF = SIF;
    }

    public Boolean getANTIHTLV() {
        return ANTIHTLV;
    }

    public void setANTIHTLV(Boolean ANTIHTLV) {
        this.ANTIHTLV = ANTIHTLV;
    }

    public Boolean getANTIHCV() {
        return ANTIHCV;
    }

    public void setANTIHCV(Boolean ANTIHCV) {
        this.ANTIHCV = ANTIHCV;
    }

    public Boolean getANTIHIV() {
        return ANTIHIV;
    }

    public void setANTIHIV(Boolean ANTIHIV) {
        this.ANTIHIV = ANTIHIV;
    }

    public Boolean getHB() {
        return HB;
    }

    public void setHB(Boolean HB) {
        this.HB = HB;
    }

    public RH getRh() {
        return rh;
    }

    public GrupaSange getGrupaSange() {
        return grupaSange;
    }
}
