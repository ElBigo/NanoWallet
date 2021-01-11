package nanoapps.equensworldlie.com.controller;

import android.os.AsyncTask;
import android.util.Log;

import org.json.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class PostHttp extends AsyncTask<String, Void, String> {
//
//    public interface AsyncResponse {
//        void processFinish(JSONObject asyncOutput);
//    }
//
//    public PostHttp(AsyncResponse delegate){
//        this.delegate = delegate;
//    }
//
//    public AsyncResponse delegate = null;

    @Override
    protected String doInBackground(String... params) {

        String data = "";

        HttpURLConnection httpURLConnection = null;
        try {

            httpURLConnection = (HttpURLConnection) new URL(params[0]).openConnection();
            httpURLConnection.setRequestMethod("POST");

            httpURLConnection.setDoOutput(true);
            
            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(params[1]);
            wr.flush();
            wr.close();

            InputStream in = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(in);

//            BufferedReader br = new BufferedReader(inputStreamReader);
//            String data = br.readLine();

            int inputStreamData = inputStreamReader.read();
            while (inputStreamData != -1) {
                char current = (char) inputStreamData;
                inputStreamData = inputStreamReader.read();
                data += current;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }

        return data;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        try {
            // *** Object JSON à récuprer pour etre traiter dans d'autres Activités ou Classe ***
            JSONObject json = new JSONObject(result);

//            delegate.processFinish(json);
//            int entier = (int) json.getInt("protocol_version");
//            String chaine = json.getString("network_identifier");
//            System.out.println("protocol_version: "+ entier);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}