package Validators;

import Model.CerereSange;
import javafx.util.Pair;

public class CerereSangeValidator extends Validator<CerereSange> {

    @Override
    public Pair<Boolean, String> validate(CerereSange cerere) {
        String erori = "";

        if(cerere.getNumePacient().equals(""))

        if(info.getAddress().equals("") || info.getEmail().equals("") || info.getNume().equals("") ||
                info.getPassword().equals("") || info.getPhone().equals("") || info.getUsername().equals(""))
            erori += "Toate campurile sunt obligatorii\n";

        if(info.getPhone().length() != 10)
            erori += "Numarul de telefon trebuie a aiba 10 cifre\n";

        if(!checkForDigits(info.getPhone()))
            erori += "Numarul de telefon nu e valid\n";

        if(info.getCnp().length() != 13)
            erori += "CNP-ul trebuie sa aiba 13 cifre\n";

        if(!checkForDigits(info.getCnp()))
            erori += "CNP-ul nu e valid\n";

        if (erori.equals(""))
            return new Pair<>(true, "Ok");
        return new Pair<>(false, erori);
    }
}
