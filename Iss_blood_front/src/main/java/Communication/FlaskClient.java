package Communication;

import Model.RegisterInfo;
import javafx.util.Pair;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class FlaskClient {

    private String urlRoot;

    public FlaskClient(Properties properties) {
        this.urlRoot = "http://"+properties.getProperty("serverIp")+":"+properties.getProperty("serverPort");
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

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Sends a login request to the server
     * @param user : Username for login
     * @param password : Password for login
     * @return <bool,string> true if the login was successfully , false otherwise
     * + a string message describing the status
     */
    public Pair<Integer, String> login(String user, String password){

        HttpURLConnection http = getConnection("/login");

        if(http == null){
            return new Pair<>(0, "Client connection request Error");
        }

        String jsonString = new JSONObject().put("username", user).put("password", password).toString();
        System.out.println(String.format("Sending %s",jsonString));

        JSONObject jsonResponse = this.sendRequest(http, jsonString);
        System.out.println(jsonResponse);

        if(jsonResponse == null) {
            return new Pair<>(0, "Connection error.");
        }
        if (jsonResponse.getInt("status")== 0) {
            return new Pair<>(jsonResponse.getInt("user_type"), "Success!");
        }
        else {
            return new Pair<>(0, jsonResponse.getString("message"));
        }
    }

    public Pair<Boolean, String> register(RegisterInfo info)
    {
        HttpURLConnection connection = getConnection("/register");

        if(connection == null)
            return new Pair<>(false, "Client connection request Error");

        String jsonString = new JSONObject().put("username", info.getUsername()).put("password", info.getPassword())
                .put("email", info.getEmail()).put("nume", info.getNume()).put("prenume", info.getPrenume())
                .put("cnp", info.getCnp()).put("judet", info.getJudet())
                .put("localitate", info.getLocalitate()).put("address", info.getAddress())
                .put("phone", info.getPhone()).put("accountType", info.getAccountType().toString())
                .put("license", info.getLicence()).toString();

        System.out.println("Sending " + jsonString);

        JSONObject jsonResponse = sendRequest(connection, jsonString);
        System.out.println(jsonResponse);
        if(jsonResponse == null)
            return new Pair<>(false, "Connection error.");

        if(jsonResponse.getString("status").equals("0"))
            return new Pair<>(true, "Registered successfully");

        return new Pair<>(false, jsonResponse.getString("message"));
    }

    /** Sends a json string to a given relative path
     * @param jsonString : String to be sent to the server
     * @param urlRelativePath : Type of request, like /login
     * @return JsonObject returned from the server
     */
    public JSONObject send_json_request(String jsonString, String urlRelativePath) {
        HttpURLConnection http = getConnection(urlRelativePath);

        if(http == null){
            return null;
        }

        return this.sendRequest(http, jsonString);
    }
}
