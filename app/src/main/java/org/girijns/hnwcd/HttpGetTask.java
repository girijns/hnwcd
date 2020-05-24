package org.girijns.hnwcd;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpGetTask extends AsyncTask<String,Object,String> {
    private ProgressDialog p;
    private ProcessCallback c;

    public static final String REQUEST_METHOD = "GET";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;
    public HttpGetTask(final ProgressDialog p, final ProcessCallback c) {
        this.p = p;
        this.c = c;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        p.setMessage("Retrieving devices...");
        p.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String stringUrl = params[0];
        String result = null;
        String inputLine;
        try {
            URL myURL = new URL(stringUrl);
            HttpURLConnection connection = (HttpURLConnection) myURL.openConnection();
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.connect();
            InputStreamReader streamReader = new
                    InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();
            while((inputLine = reader.readLine()) != null){
                stringBuilder.append(inputLine);
            }
            reader.close();
            streamReader.close();
            result = stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        c.run(result);
        p.dismiss();
    }
}
