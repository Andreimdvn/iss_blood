package Validators;

import Model.RegisterInfo;
import javafx.util.Pair;

public class RegisterValidator {

    /**
     * @param info - the RegisterInfo to be validated
     * @return True if the info is valid, false otherwise
     */
    public Pair<Boolean, String> Validate(RegisterInfo info)
    {
//        return new Pair<>(true, "ok");

        String erori = "";

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

    /**
     * Verifica daca stringul e format doar din cifre
     * @param str - stringul de verificat
     * @return True daaca stringul contine doar cifre, False altfel
     */
    private Boolean checkForDigits(String str)
    {
        for(char c : str.toCharArray())
            if(c < '0' || c > '9')
                return false;
        return true;
    }

}

