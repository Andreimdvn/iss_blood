package Controller;

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

    private final String VALID = "VALID";
    private final String INVALID = "INVALID";
    private final String MESSAGE_ERROR="ERROR";

    @FXML
    private void validateAnaliza(){
        String result = validate();

        if(result.equals(MESSAGE_ERROR)) {
            new CustomMessageBox("Error analiza", MESSAGE_ERROR, 1).show();
        }
        else {
            if(result.equals(VALID))
                new CustomMessageBox("Analiza valida", VALID,0).show();
            else
                new CustomMessageBox("Analiza invalida",INVALID,0).show();

            ((CentruTransfuzieController)getScreenController().getControlledScreen("CENTRU_TRANSFUZIE")).setCenter(
                    getScreenController().getScreen("CENTRU_CERERI_DONARI"));

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


}
