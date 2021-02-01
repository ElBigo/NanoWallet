package nanoapps.equensworldlie.com.controller;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import org.json.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class Request extends AsyncTask<String, String, String> {

    JSONObject postData;
    RequestCallback onEndReq;

    public Request(Map<String, String> postData, RequestCallback callb){

        if (postData != null){
            this.postData = new JSONObject(postData);
        }
        this.onEndReq = callb;
    }

    @Override
    protected String doInBackground(String... params) {

        InputStream inputStream = null;
        HttpURLConnection conn = null;
        String stringUrl = params[0];

        try {
            URL url = new URL(stringUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(15000);

            if (this.postData != null) {
                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
                writer.write(postData.toString());
                writer.flush();
            }
            conn.connect();

            int response = conn.getResponseCode();

            if (response != 200) {return null;}

            inputStream = conn.getInputStream();

            if (inputStream == null) {return null;}

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader reader = new BufferedReader(inputStreamReader);
            StringBuffer buffer = new StringBuffer();
            String line;

            while ((line = reader.readLine()) != null) {
                buffer.append(line);
                buffer.append("\n");
            }

            return new String(buffer);
        } catch (IOException e) {
            return null;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    @Override
    protected void onPostExecute(String s) {

        Log.e("Output",s);
        super.onPostExecute(s);
        if(onEndReq!=null){
            onEndReq.setResponse(s);
            onEndReq.run();
        }

        // int entier = (int) json.getInt("protocol_version");
        // String chaine = json.getString("network_identifier");
        // System.out.println("protocol_version: "+ entier);
    }

}

