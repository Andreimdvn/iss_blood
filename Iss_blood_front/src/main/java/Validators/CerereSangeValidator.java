package Validators;

import Model.CerereSange;
import javafx.util.Pair;

public class CerereSangeValidator extends Validator<CerereSange> {

    @Override
    public Pair<Boolean, String> validate(CerereSange cerere) {
        String erori = "";

        if(cerere.getNumePacient().equals(""))

        if (erori.equals(""))
            return new Pair<>(true, "Ok");
        return new Pair<>(false, erori);
    }
}
