package Model;

public class Analiza {
    Integer idAnaliza;
    Integer IdSangeBrut;
    Boolean ALT;
    Boolean SIF;
    Boolean ANTIHTLV;
    Boolean ANTIHCV;
    Boolean ANTIHIV;
    Boolean HB;

    public Analiza(Integer idAnaliza, Integer idSangeBrut, Boolean ALT, Boolean SIF, Boolean ANTIHTLV, Boolean ANTIHCV, Boolean ANTIHIV, Boolean HB) {
        this.idAnaliza = idAnaliza;
        IdSangeBrut = idSangeBrut;
        this.ALT = ALT;
        this.SIF = SIF;
        this.ANTIHTLV = ANTIHTLV;
        this.ANTIHCV = ANTIHCV;
        this.ANTIHIV = ANTIHIV;
        this.HB = HB;
    }

    public Integer getIdAnaliza() {
        return idAnaliza;
    }

    public void setIdAnaliza(Integer idAnaliza) {
        this.idAnaliza = idAnaliza;
    }

    public Integer getIdSangeBrut() {
        return IdSangeBrut;
    }

    public void setIdSangeBrut(Integer idSangeBrut) {
        IdSangeBrut = idSangeBrut;
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
}
