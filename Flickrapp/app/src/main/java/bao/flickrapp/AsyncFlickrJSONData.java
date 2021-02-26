package bao.flickrapp;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
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

public class AsyncFlickrJSONData extends AsyncTask<String, Void, JSONObject> {

    private AppCompatActivity mainAct;

    public AsyncFlickrJSONData(AppCompatActivity mainAct){
        this.mainAct = mainAct;
    }

    @Override
    protected JSONObject doInBackground(String... strings){

        URL url = null;

        String s;

        try {
            url = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                s = readStream(in);

                //removing the part making it not JSON format
                s = s.substring("jsonFlickrFeed(".length(), s.length() - 1);
                Log.i("JFL", s);

                JSONObject j = new JSONObject(s);

                return j;

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

        return null;

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


    @Override
    protected void onPostExecute(JSONObject j)
    {

        try {
            //retrieve data from JSON, separate into items
            JSONArray array = j.getJSONArray("items");

            for(int i = 0; i<array.length(); i++){

                //separate into objects
                JSONObject temp = array.getJSONObject(i);

                //get the url for each object
                String url = temp.getJSONObject("media").getString("m");
                Log.i("imageurl",url);

                //bitmap download
                AsyncBitmapDownloader asyncDownloader = new AsyncBitmapDownloader(mainAct);
                asyncDownloader.execute(url);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
