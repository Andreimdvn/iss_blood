package Validators;

import javafx.util.Pair;

public abstract class Validator<T> {
    /**
     * Verifica daca stringul e format doar din cifre
     * @param str - stringul de verificat
     * @return True daaca stringul contine doar cifre, False altfel
     */
    Boolean checkForDigits(String str)
    {
        for(char c : str.toCharArray())
            if(c < '0' || c > '9')
                return false;
        return true;
    }

    public abstract Pair<Boolean, String> validate(T object);
}
