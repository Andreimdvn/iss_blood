package Validators;

import Model.CerereSange;
import Model.GrupaSange;
import Model.Importanta;
import Model.RH;
import javafx.util.Pair;

public class CerereSangeValidator extends Validator<CerereSange> {

    @Override
    public Pair<Boolean, String> validate(CerereSange cerere) {
        String erori = "";

        if(cerere.getNumePacient().length() < 3)
            erori += "Nume completat gresit.\n";
        if(cerere.getCnpPacient().length() != 13 || !checkForDigits(cerere.getCnpPacient()))
            erori += "Cnp-ul trebuie sa aiba 13 cifre\n";
        if(cerere.getNumarPungiTrombocite().equals(0)
                && cerere.getNumarPungiGlobuleRosii().equals(0)
                && cerere.getNumarPungiPlasma().equals(0))
            erori+= "Nu ai cerut nicio punga\n";
        if(cerere.getGrupaSange() == null)
            erori += "Nu ai selectat o grupa de sange\n";
        if(cerere.getImportanta()== null)
            erori += "Nu ai selectat importanta\n";
        if(cerere.getRh() == null)
            erori += "Nu ai selectat rh\n";

        if (erori.equals(""))
            return new Pair<>(true, "Ok");
        return new Pair<>(false, erori);
    }
}
