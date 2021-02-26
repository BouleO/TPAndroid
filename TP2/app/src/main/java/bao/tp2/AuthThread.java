package bao.tp2;



import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AuthThread extends Thread {

    private String login;
    private String password;
    private TextView resultField;

    public AuthThread(String login, String password, TextView resultField){
        this.login = login;
        this.password = password;
        this.resultField = resultField;
    }

    public void run(){

        URL url = null;
        try {

            //url = new URL("https://www.android.com/");

            url = new URL("https://httpbin.org/basic-auth/bob/sympa");

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            String userInput =login+":"+password;
            String basicAuth = "Basic " + Base64.encodeToString(userInput.getBytes(), Base64.NO_WRAP);
            urlConnection.setRequestProperty ("Authorization", basicAuth);

            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                //readStream is a user defined method that reads the input stream originating from the url connection we established with the website
                String s = readStream(in);

                JSONObject j = new JSONObject(s);
                boolean b = j.getBoolean("authenticated");

                if(b){
                    resultField.setText("Login successful!");
                }
                Log.i("JFL", s);
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }

}
