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
        if(info.getAddress().equals("") || info.getEmail().equals("") || info.getNume().equals("") ||
                info.getPassword().equals("") || info.getPhone().equals("") || info.getUsername().equals(""))
            return new Pair<>(false, "Toate campurile sunt obligatorii");

        if(info.getPhone().length() != 10)
            return new Pair<>(false, "Numarul de telefon trebuie a aiba 10 cifre");

        if(!checkForDigits(info.getPhone()))
            return new Pair<>(false, "Numarul de telefon nu e valid");

        if(info.getCnp().length() != 13)
            return new Pair<>(false, "CNP-ul trebuie sa aiba 13 cifre");

        if(!checkForDigits(info.getCnp()))
            return new Pair<>(false, "CNP-ul nu e valid");

        return new Pair<>(true, "Ok");
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

