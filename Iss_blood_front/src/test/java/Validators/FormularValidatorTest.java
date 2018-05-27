package Validators;

import Model.FormularDonare;
import Model.GrupaSange;
import Model.RH;
import Model.Sex;
import org.junit.Test;
import static org.junit.Assert.*;


public class FormularValidatorTest {

    @Test
    public void testVali()
    {
        //Nume gol
        testFormularInvalid(new FormularDonare("", "das", Sex.MASCULIN,
                "1234567890", "qwe", "qwe", "qwe", "wqe",
                "qwe", "ewq", "ewe", "", GrupaSange.O1,
                RH.POZITIV, (short)32));

        //Numele are niste trash
        testFormularInvalid(new FormularDonare("--.  ", "das", Sex.MASCULIN,
                "1234567890", "qwe", "qwe", "qwe", "wqe",
                "qwe", "ewq", "ewe", "", GrupaSange.O1,
                RH.POZITIV, (short)32));

        //sex null
        testFormularInvalid(new FormularDonare("sa", "das", null,
                "1234567890", "qwe", "qwe", "qwe", "wqe",
                "qwe", "ewq", "ewe", "", GrupaSange.O1,
                RH.POZITIV, (short)32));

        //nr tel
        testFormularInvalid(new FormularDonare("sa", "das", Sex.MASCULIN,
                "1651", "qwe", "qwe", "qwe", "wqe",
                "qwe", "ewq", "ewe", "", GrupaSange.O1,
                RH.POZITIV, (short)32));

        //beneficiar cnp
        testFormularInvalid(new FormularDonare("sa", "das", Sex.MASCULIN,
                "1234567890", "qwe", "qwe", "qwe", "wqe",
                "qwe", "ewq", "ewe", "ewqe", GrupaSange.O1,
                RH.POZITIV, (short)32));

        //daca nu are beneficiar
        testFormularValid(new FormularDonare("sa", "das", Sex.MASCULIN,
                "1234567890", "qwe", "qwe", "qwe", "wqe",
                "qwe", "ewq", "", "", GrupaSange.O1,
                RH.POZITIV, (short)32));
    }

    private void testFormularInvalid(FormularDonare formularDonare)
    {
        FormularValidator validator = new FormularValidator();

        try {
            validator.Validate(formularDonare);
            assert false;
        }
        catch (ValidationException e)
        {
            assert true;
        }
    }

    private void testFormularValid(FormularDonare formularDonare)
    {
        FormularValidator validator = new FormularValidator();

        try {
            validator.Validate(formularDonare);
            assert true;
        }
        catch (ValidationException e)
        {
            assert false;
        }
    }

}
