package Controller;

import Utils.CustomMessageBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CentruPrelevareController extends ControlledScreen{
    @FXML
    private VBox selectionPane;

    @FXML
    private void initialize(){

    }

    private final String ERROR="ERROR";
    private final String VALID="VALID";
    private final String INVALID="INVALID";

    @FXML
    private void validareClicked(){
        String ok = validate();
        String titlu = null;
        String mesaj =  null;
        boolean goBack = true;
        int type = 0;
        if(ok.equals(INVALID)) {
            titlu = "Nu poate dona";
            mesaj = "Persoana nu poate dona";
            type = 0;
        }
        else if(ok.equals(ERROR)) {
            titlu="Campuri gresite";
            mesaj = "Nu ai raspuns la toate intrebarile";
            type = 1;

            goBack = false;
        }
        else {
            titlu = "Poate dona";
            mesaj = "Donatorul poate dona" ;
            type = 0;
        }
        new CustomMessageBox(titlu,mesaj,type).show();
        if(goBack)
            goBack();
    }

    private void goBack(){
        ((CentruTransfuzieController)getScreenController().getControlledScreen("CENTRU_TRANSFUZIE")).setCenter(
                getScreenController().getScreen("CENTRU_CERERI_DONARI"));
    }

    @FXML
    private String validate(){
        boolean counter = false;
        int contorIntrebari = 0;
        String mesaj = VALID;

        for (Node node : selectionPane.getChildren()) {
            if(node instanceof HBox)
            {
                HBox box = (HBox) node;
                ToggleButton a = (ToggleButton) box.getChildren().get(0);
                ToggleButton b = (ToggleButton) box.getChildren().get(1);

                if(a.isSelected() || b.isSelected())
                {

                    if(contorIntrebari <  3) {
                        if(b.isSelected())
                            mesaj = INVALID;
                    }
                    else if(a.isSelected())
                        mesaj = INVALID;
                }
                else {
                    mesaj = ERROR;
                    break;
                }
                contorIntrebari++;
            }
        }

        return mesaj;
    }

    @FXML
    private JFXTextField donatFullnameTextField;

    @FXML
    private JFXTextField donatCnpTextField;

}
