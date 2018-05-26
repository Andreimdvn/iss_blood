package Validators;

import Model.FormularDonare;
import javafx.util.Pair;

public class FormularValidator {


    public void Validate(FormularDonare formularDonare) throws ValidationException {
        //Campurile sa nu fie goale sau sa contina doar caractere de 'umplutura'
        if(formularDonare.getNume().replaceAll("[-\\.\\s]", "").equals("") ||
                formularDonare.getPrenume().replaceAll("[-\\.\\s]", "").equals("") ||
                formularDonare.getTelefon().replaceAll("[-\\.\\s]", "").equals("") ||
                formularDonare.getDomiciliuAdresa().replaceAll("[-\\.\\s]", "").equals("") ||
                formularDonare.getDomiciliuJudet().replaceAll("[-\\.\\s]", "").equals("") ||
                formularDonare.getDomiciliuLocalitate().replaceAll("[-\\.\\s]", "").equals("") ||
                formularDonare.getResedintaAdresa().replaceAll("[-\\.\\s]", "").equals("") ||
                formularDonare.getResedintaLocalitate().replaceAll("[-\\.\\s]", "").equals("") ||
                formularDonare.getResedintaJudet().replaceAll("[-\\.\\s]", "").equals(""))
            throw new ValidationException("Toate campurile obligatorii trebuie completate");

        String errors = "";

        //nr de telefon
        if(!formularDonare.getTelefon().matches("[0-9]{10}"))
            errors += "Numarul de telefon nu este valid\n";

        if(!formularDonare.getBeneficiarCNP().equals("") &&
                !formularDonare.getBeneficiarCNP().matches("[0-9]{13}"))
            errors += "CNP-ul beneficiarului nu este valid";

        if(!errors.equals(""))
            throw new ValidationException(errors);
    }
}
