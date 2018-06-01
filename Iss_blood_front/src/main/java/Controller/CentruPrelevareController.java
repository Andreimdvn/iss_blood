package Controller;

import Model.FormularDonare;
import Model.StaffInfo;
import Model.Status;
import Utils.CustomMessageBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class CentruPrelevareController extends ControlledScreen{
    @FXML
    private VBox selectionPane;

    @FXML
    private void initialize(){

    }

    private FormularDonare cerereDonare;

    @FXML
    private Label numeLabel;

    @FXML
    private Label prenumeLabel;

    @FXML
    private Label sexLabel;

    @FXML
    private Label telefonLabel;

    @FXML
    private Label domiciliuLocalitateLabel;

    @FXML
    private Label domiciliuJudetLabel;

    @FXML
    private Label domiciliuAdresaLabel;

    @FXML
    private Label resedintaLocalitateLabel;

    @FXML
    private Label resedintaJudetLabel;

    @FXML
    private Label resedintaAdresaLabel;


    void setFormularDonare(FormularDonare cerereDonare) {
        this.cerereDonare = cerereDonare;
        numeLabel.setText(cerereDonare.getNume());
        prenumeLabel.setText(cerereDonare.getPrenume());
        sexLabel.setText(cerereDonare.getSex().toString());
        telefonLabel.setText(cerereDonare.getTelefon());
        domiciliuAdresaLabel.setText(cerereDonare.getDomiciliuAdresa());
        domiciliuJudetLabel.setText(cerereDonare.getDomiciliuJudet());
        domiciliuLocalitateLabel.setText(cerereDonare.getDomiciliuLocalitate());
        resedintaAdresaLabel.setText(cerereDonare.getResedintaAdresa());
        resedintaJudetLabel.setText(cerereDonare.getResedintaJudet());
        resedintaLocalitateLabel.setText(cerereDonare.getResedintaLocalitate());
        if(!(Objects.equals(cerereDonare.getBeneficiarFullName(), "null")))
            donatFullnameTextField.setText(cerereDonare.getBeneficiarFullName());
        else
            donatFullnameTextField.setText("");
        if(!(Objects.equals(cerereDonare.getBeneficiarCNP(), "null")))
            donatCnpTextField.setText(cerereDonare.getBeneficiarCNP());
        else
            donatCnpTextField.setText("");

    }

    private void loadData(){

    }

    private final String ERROR="ERROR";
    private final String VALID="VALID";
    private final String INVALID="INVALID";

    private StaffInfo getInfo(){
        return (StaffInfo) getScreenController().userInfo;
    }

    @FXML
    private void validareClicked(){
        String ok = validate();
        String titlu = null;
        String mesaj =  null;
        boolean goBack = true;
        int type = 0;
        StaffInfo info = (StaffInfo)getScreenController().userInfo;
        String staffFullName = info.getNume() + " " + info.getPrenume();
        if(ok.equals(INVALID)) {
            titlu = "Nu poate dona";
            mesaj = "Persoana nu poate dona";
            type = 0;
            cerereDonare.setStatus(Status.NONCONFORM);
            getService().staffUpdateFormularDonare(cerereDonare,getInfo().getIdLocatie(), staffFullName);

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
            cerereDonare.setStatus(Status.PRELEVARE);
            cerereDonare.setBeneficiarFullName(donatFullnameTextField.getText());
            cerereDonare.setBeneficiarCNP(donatCnpTextField.getText());
            getService().staffUpdateFormularDonare(cerereDonare,getInfo().getIdLocatie(), staffFullName);

            type = 0;
        }
        new CustomMessageBox(titlu,mesaj,type).show();
        if(goBack)
             goBack();
    }

    private void goBack(){
        ((CentruTransfuzieController)getScreenController().getControlledScreen("CENTRU_TRANSFUZIE")).setCenter(
                getScreenController().getScreen("CENTRU_CERERI_DONARI"));
        update();
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

    @Override
    void updateThis() {

    }
}
