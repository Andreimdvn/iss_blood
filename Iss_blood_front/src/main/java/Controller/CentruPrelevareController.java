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

    @FXML
    private void validareClicked(){
        boolean ok = validate();

        if(!ok)
            new CustomMessageBox("Nu poate dona","Pacient nu poate dona",1).show();
        else
            new CustomMessageBox("Poate dona","Donatorul poate dona",0).show();

    }

    @FXML
    private boolean validate(){
        boolean counter = false;
        int bothSelected = 0;
        for (Node node : selectionPane.getChildren()) {
            if(node instanceof HBox)
            {
                HBox box = (HBox) node;
                for (Node a : box.getChildren())
                if(a instanceof ToggleButton) {
                    if (((ToggleButton) a).isSelected() && counter)
                        return false;
                    if(((ToggleButton) a).isSelected())
                        bothSelected++;
                    if(bothSelected == 0)
                        return false;

                    counter = !counter;
                    if(counter == false)
                    {
                        bothSelected = 0;
                    }
                }
            }
        }
        return true;
    }

    @FXML
    private JFXTextField donatFullnameTextField;

    @FXML
    private JFXTextField donatCnpTextField;

}
