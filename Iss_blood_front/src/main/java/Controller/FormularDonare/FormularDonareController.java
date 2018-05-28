package Controller.FormularDonare;

import Controller.ScreenController;
import Model.*;
import Utils.CustomMessageBox;
import Validators.ValidationException;
import javafx.util.Pair;

public class FormularDonareController extends AbstractFormularDonareController {

    private void autoFillTextFields()
    {
        DonatorInfo info = (DonatorInfo) getScreenController().userInfo;
        firstNameTextField.setText(info.getPrenume());
        lastNameTextField.setText(info.getNume());
        DomiciliuJudetTextField.setText(info.getDomiciliuJudet());
        DomiciliuLocalitateTextField.setText(info.getDomiciliuLocalitate());
        DomiciliuAdresaTextField.setText(info.getDomiciliuAdresa());
        ResedintaJudetTextField.setText(info.getResedintaJudet());
        ResedintaLocalitateTextField.setText(info.getResedintaLocalitate());
        ResedintaAdresaTextField.setText(info.getResedintaAdresa());
        phoneTextField.setText(info.getTelefon());
        sexToggleGroup.selectToggle(info.getSex() == Sex.FEMININ ? femininToggle : masculinToggle);
    }

    @Override
    public void setScreenController(ScreenController screenController) {
        super.setScreenController(screenController);

        autoFillTextFields();
    }

    @Override
    public void trimiteFormular()
    {
        //ia field-urile
        //le trimite la service
        //daca e ok, trece la ecranul urmator

        FormularDonare formularDonare = null;
        try {
            formularDonare = GetInfoFormular();
        } catch (ValidationException e) {
            CustomMessageBox msg = new CustomMessageBox("Eroare", "Formularul nu a fost completat corect. " +
                    "\n" + e.getMessage(), 1);
            msg.show();
            return;
        }

        String username = getScreenController().userInfo.getUsername();

        Pair<Boolean, String> rez = getService().userTrimiteFormularDonare(formularDonare, username);
        if(rez.getKey())
        {
            CustomMessageBox msg = new CustomMessageBox("Info", "Formularul a fost trimis cu succes", 0);
            msg.show();
            loadPostFormular();
        }
        else
        {
            CustomMessageBox msg = new CustomMessageBox("Eroare", rez.getValue(), 1);
            msg.show();
        }
    }

}
