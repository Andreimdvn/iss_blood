package Communication;

import java.io.IOException;
import java.net.*;

public class FlaskClient {

    private HttpURLConnection getConnection(String urlString)
    {
        try {

            URL url = new URL(urlString);
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection)con;
            http.setRequestMethod("POST"); // PUT is another valid option
            http.setDoOutput(true);

            return http;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
