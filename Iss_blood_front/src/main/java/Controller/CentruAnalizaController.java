package Controller;

import Model.*;
import Utils.CustomMessageBox;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CentruAnalizaController extends ControlledScreen{


    @FXML
    private ToggleButton rhNegativToggleButton;
    @FXML
    private ToggleButton rhPozitivToggleButton;

    @FXML
    private ToggleButton altNegativToggleButton;
    @FXML
    private ToggleButton altPozitivToggleButton;


    @FXML
    private ToggleButton sifNegativToggleButton;
    @FXML
    private ToggleButton sifPozitivToggleButton;

    @FXML
    private ToggleButton hltvNegativToggleButton;
    @FXML
    private ToggleButton hltvPozitivToggleButton;

    @FXML
    private ToggleButton hivNegativToggleButton;
    @FXML
    private ToggleButton hivPozitivToggleButton;

    @FXML
    private ToggleButton hcvNegativToggleButton;
    @FXML
    private ToggleButton hcvPozitivToggleButton;

    @FXML
    private ToggleButton hbNegativToggleButton;
    @FXML
    private ToggleButton hbPozitivToggleButton;



    @FXML
    private void initialize(){

    }

    private FormularDonare cerereDonare;

    public void setFormularDonare(FormularDonare cerereDonare) {
        this.cerereDonare = cerereDonare;
    }

    private final String VALID = "VALID";
    private final String INVALID = "INVALID";
    private final String MESSAGE_ERROR="ERROR";

    private void setGrupaAndRH(GrupaSange gs, RH rh){
        cerereDonare.setGrupa(gs);
        cerereDonare.setRh(rh);
    }

    private GrupaSange getGrupaSange(){

        GrupaSange grupaSange;

        if(grupa1.isSelected())
            grupaSange = GrupaSange.O1;
        else if(grupa2.isSelected())
            grupaSange = GrupaSange.A2;
        else if(grupa3.isSelected())
            grupaSange = GrupaSange.B3;
        else
            grupaSange =GrupaSange.AB4;

        return grupaSange;
    }

    private RH getRHAnaliza(){
        if(rhNegativToggleButton.isSelected())
            return RH.NEGATIV;
        return RH.POZITIV;
    }
    private StaffInfo getInfo(){
        return (StaffInfo) getScreenController().userInfo;
    }

    private Analiza getAnaliza()
    {
        Boolean ALT;
        Boolean SIF;
        Boolean ANTIHTLV;
        Boolean ANTIHCV;
        Boolean ANTIHIV;
        Boolean HB;

        ALT = altPozitivToggleButton.isSelected();
        SIF = sifPozitivToggleButton.isSelected();
        ANTIHTLV = hltvPozitivToggleButton.isSelected();
        ANTIHCV = hcvPozitivToggleButton.isSelected();
        ANTIHIV = hivPozitivToggleButton.isSelected();
        HB = hbPozitivToggleButton.isSelected();

        return new Analiza(-1,ALT,SIF,ANTIHTLV,ANTIHCV,ANTIHIV,HB, GrupaSange.A2, RH.NEGATIV); //TO DO: RH si grupa
    }
    @FXML
    private void validateAnaliza(){
        String result = validate();

        if(result.equals(MESSAGE_ERROR)) {
            new CustomMessageBox("Error analiza", MESSAGE_ERROR, 1).show();
        }
        else {
            if (result.equals(VALID)) {
                new CustomMessageBox("Analiza valida", VALID, 0).show();
                cerereDonare.setStatus(Status.DISTRIBUIRE);
            } else {
                new CustomMessageBox("Analiza invalida", INVALID, 0).show();
                cerereDonare.setStatus(Status.NONCONFORM);
            }

            setGrupaAndRH(getGrupaSange(),getRHAnaliza());
            //getService().staffUpdateFormularDonare(cerereDonare,getInfo().getIdLocatie());
            getService().staffTrimiteAnaliza(getInfo().getIdLocatie(),cerereDonare,getAnaliza());
            CentruTransfuzieController cr =(CentruTransfuzieController)getScreenController().getControlledScreen("CENTRU_TRANSFUZIE");
            cr.setCenter(
                    getScreenController().getScreen("CENTRU_CERERI_DONARI"));

            update();

        }
    }

    @FXML
    private VBox selectionPane;


    private boolean validateRH(){
        return rhPozitivToggleButton.isSelected() || rhNegativToggleButton.isSelected();
    }

    private boolean validateALT(){
        return altPozitivToggleButton.isSelected() || altNegativToggleButton.isSelected();
    }
    private boolean validateSIF(){
        return sifPozitivToggleButton.isSelected() || sifNegativToggleButton.isSelected();
    }
    private boolean validateHLTV(){
        return hltvPozitivToggleButton.isSelected() || hltvNegativToggleButton.isSelected();
    }
    private boolean validateHIV(){
        return hivPozitivToggleButton.isSelected() || hivNegativToggleButton.isSelected();
    }

    private boolean validateHCV(){
        return hcvPozitivToggleButton.isSelected() || hcvNegativToggleButton.isSelected();
    }

    private boolean validateHB(){
        return hbPozitivToggleButton.isSelected() || hbNegativToggleButton.isSelected();
    }

    @FXML
    private String validate(){
      if(!(validateALT() && validateFirstRow() && validateHB() && validateHCV() && validateHIV() && validateHLTV()
              && validateSIF() && validateRH()))
          return MESSAGE_ERROR;
      else if(altPozitivToggleButton.isSelected() || hbPozitivToggleButton.isSelected() || hcvPozitivToggleButton.isSelected()
              || hivPozitivToggleButton.isSelected() || hltvPozitivToggleButton.isSelected() || sifPozitivToggleButton.isSelected()
              )
          return INVALID;
      return VALID;
    }

    @FXML
    private ToggleButton grupa1;
    @FXML
    private ToggleButton grupa2;
    @FXML
    private ToggleButton grupa3;
    @FXML
    private ToggleButton grupa4;

    private boolean validateFirstRow() {
        return grupa1.isSelected() || grupa2.isSelected() || grupa3.isSelected() || grupa4.isSelected();
    }


    @Override
    void updateThis() {

    }
}
