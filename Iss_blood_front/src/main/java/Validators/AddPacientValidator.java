package Validators;

import Model.Pacient;
import javafx.util.Pair;

public class AddPacientValidator {

    /**
     *  Validate a pacient
     * @param pacient represents given pacient
     * @return a pair as (True, ok) if pacient info are ok
     *                   (False, errors) else
     */
    public Pair<Boolean, String> validate(Pacient pacient) {

        String errors =  "";

        if("".equals(pacient.getNume()))
            errors += "Campul nume trebuie completat \n";
        if(pacient.getNume().matches(".*\\d+.*"))
            errors += "Numele nu trebuie sa contina cifre \n";

        if("".equals(pacient.getCnp()))
            errors += "Campul CNP trebuie completat \n";
        else {
            if (pacient.getCnp().length() != 13)
                errors += "CNP-ul trebuie sa aiba 13 cifre \n";
            if (!pacient.getCnp().matches("^\\d+$"))
                errors += "CNP-ul trebuie sa contina doar cifre \n";
        }

        if("".equals(errors))
            return new Pair<>(true, "ok");
        return new Pair<>(false, errors);
    }
}
