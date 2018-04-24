package Communication;

import javafx.util.Pair;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class FlaskClient {

    private String urlRoot;

    public FlaskClient() {
        this.urlRoot = "http://127.0.0.1:16000";
    }

    /**
     * Creates a http connection using the given path
     * @param urlRelativePath: String containing the type of request, for example /login
     * @return HttpURLConnection that will be used to send json to the server
     */
    private HttpURLConnection getConnection(String urlRelativePath) {
        try {

            URL url = new URL(this.urlRoot + urlRelativePath);
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection) con;
            http.setRequestMethod("POST");
            http.setDoOutput(true);

            return http;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param http : HttpURLConnection returned by getConnection method
     * @param jsonString : String in json format to be sent to the server
     * @return JsonObject returned by the server
     */
    private JSONObject sendRequest(HttpURLConnection http, String jsonString){
        byte[] outJson = jsonString.getBytes(StandardCharsets.UTF_8);
        int length = outJson.length;

        http.setFixedLengthStreamingMode(length);
        http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        try {
            http.connect();
            try(OutputStream os = http.getOutputStream()) {
                os.write(outJson);
            }
            InputStream inputStream = http.getInputStream();
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);
            return new JSONObject(responseStrBuilder.toString());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Sends a login request to the server
     * @param user : Username for login
     * @param password : Password for login
     * @return <bool,string> true if the login was succesfully , false otheewise
     * + a string message describing the status
     */
    public Pair<Boolean, String> login(String user, String password){

        HttpURLConnection http = getConnection("/login");

        if(http == null){
            return new Pair<>(false, "Client connection request Error");
        }

        String jsonString = new JSONObject().put("username", user).put("password", password).toString();
        System.out.println(String.format("Sending %s",jsonString));

        JSONObject jsonResponse = this.sendRequest(http, jsonString);
        System.out.println(jsonResponse);

        if(jsonResponse == null) {
            return new Pair<>(false, "Connection error.");
        }
        if (jsonResponse.getString("status").equals("1")) {
            return new Pair<>(true, "Success!");
        }
        else {
            return new Pair<>(false, jsonResponse.getString("message"));
        }
    }

    /** Sends a json string to a given relative path
     * @param jsonString : String to be sent to the server
     * @param urlRelateivePath : Type of request, like /login
     * @return JsonObject returned from the server
     */
    public JSONObject send_json_request(String jsonString, String urlRelateivePath) {
        HttpURLConnection http = getConnection(urlRelateivePath);

        if(http == null){
            return null;
        }

        return this.sendRequest(http, jsonString);
    }
}