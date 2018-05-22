package Utils;

import Model.DonatorInfo;
import Model.UserInfo;
import org.json.JSONObject;

public class UserUtils {
    public static UserInfo GetUserInfoFromResponse (JSONObject loginResponse)
    {
        int type = loginResponse.getInt("user_type");
        UserInfo info = null;
        if(type == 1) //donator
        {
            info = new DonatorInfo(loginResponse.getString("username"),
                    loginResponse.getString("nume"),
                    loginResponse.getString("prenume"),
                    loginResponse.getString("cnp"),
                    loginResponse.getString("telefon"),
                    loginResponse.getString("domiciliu_localitate"),
                    loginResponse.getString("domiciliu_judet"),
                    loginResponse.getString("domiciliu_adresa"),
                    loginResponse.getString("resedinta_localitate"),
                    loginResponse.getString("resedinta_judet"),
                    loginResponse.getString("resedinta_adresa")
                    );
        }
        return info;
    }
}
