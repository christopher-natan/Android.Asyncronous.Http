package easyload.natan.com.easyload.util;

import android.os.AsyncTask;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import javax.net.ssl.HttpsURLConnection;

/**
 * Asynchronous http
 * This class is used to connect asynchronously to http server with callbacks implementation and returns JSON object
 *
 * @author Christopher M. Natan
 * @version 2018.1.1
 * @since 1.0
 */

public class Http extends AsyncTask<String, Void, String> {
    /**
     * use logging debug output to Logcat
     */
    private static final String LOG_TAG = "Http";
    /**
     * set the default character encoding
     */
    private static final String CHAR_SET = "UTF-8";
    /**
     * http read timeout length in milliseconds
     */
    private static final Integer HTTP_READ_TIMEOUT = 15000;
    /**
     * http connect timeout length in milliseconds
     */
    private static final Integer HTTP_CONNECT_TIMEOUT = 15000;
    /**
     * http request method
     */
    private static final String HTTP_REQUEST_METHOD = "POST";
    /**
     * root element in the results
     */
    private static final String JSON_ROOT_TAG = "result";
    /**
     * it contains HttpListener object
     */
    private HttpListener mHttpListener;
    /**
     * it contains HttpURLConnection object
     */
    private HttpURLConnection mURLConnection;
    /**
     * the result after the http request
     */
    private Integer mResponseCode;
    /**
     * it contains BufferedWriter object
     */
    private BufferedWriter mBufferedWriter;
    /**
     * it contains HttpResponse object
     */
    private HttpResponse mHttpResponse = new HttpResponse();

    /**
     * Perform background operations
     *
     * @param arg0 contains http url string
     */
    protected String doInBackground(String... arg0) {
        this.setURLConnection(arg0);
        this.setOutputStream();
        this.mHttpResponse.setStringContent();

        return this.mHttpResponse.getStringContent();
    }

    /**
     * Open a new HttpURLConnection and casting the result to this.mURLConnection.
     *
     * @param arg0 contains http url string
     */
    private void setURLConnection(String... arg0) {
        String httpUrl = arg0[0].trim();

        try {
            URL url = new URL(httpUrl);
            this.mURLConnection = (HttpURLConnection) url.openConnection();
            this.mURLConnection.setReadTimeout(HTTP_READ_TIMEOUT);
            this.mURLConnection.setConnectTimeout(HTTP_CONNECT_TIMEOUT);
            this.mURLConnection.setRequestMethod(HTTP_REQUEST_METHOD);
            this.mURLConnection.setDoInput(true);
            this.mURLConnection.setDoOutput(true);
            this.mResponseCode = this.mURLConnection.getResponseCode();
        } catch (IOException e) {
            if (this.mHttpListener != null) mHttpListener.onError(e.getMessage());
        }
    }

    /**
     * Return the result of mURLConnection
     *
     * @return Contains HttpURLConnection object
     */
    private HttpURLConnection getUrlConnection() {
        return this.mURLConnection;
    }

    /**
     * Writes the output to a character-output stream using BufferedWriter object
     *
     * @return void
     */
    private void setOutputStream() {
        try {
            JSONObject jsonOutputStream = new JSONObject();
            HttpURLConnection URLConnection = this.mURLConnection;
            OutputStream connectionOutputStream = URLConnection.getOutputStream();
            this.mBufferedWriter = new BufferedWriter(new OutputStreamWriter(connectionOutputStream, CHAR_SET));
            this.mBufferedWriter.write(URLEncoder.encode(jsonOutputStream.toString(), CHAR_SET));

            this.mBufferedWriter.flush();
            this.mBufferedWriter.close();
            connectionOutputStream.close();
        } catch (Exception e) {
            if (this.mHttpListener != null) mHttpListener.onResponseError(e.getMessage());
        }
    }

    /**
     * A class that process http response into proper content
     *
     */
    private class HttpResponse {
        private StringBuilder mStringContent = new StringBuilder();

        public void setStringContent() {
            try {
                if (getResponseCode() == HttpsURLConnection.HTTP_OK) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getUrlConnection().getInputStream()));
                    this.mStringContent = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        this.mStringContent.append(line);
                    }

                    bufferedReader.close();
                    if (mHttpListener != null && this.mStringContent.length() <= 0)
                        mHttpListener.onResponseNull();
                }
            } catch (IOException e) {
                this.mStringContent = null;
                if (mHttpListener != null) mHttpListener.onResponseError(e.getMessage());
            }
        }

        public String getStringContent() {
            return this.mStringContent.toString();
        }
    }

    /**
     * Method that returns the http code
     *
     * @return The resulting http code
     */
    private Integer getResponseCode() {
        return this.mResponseCode;
    }

    /**
     * This method normally used to setup the task
     *
     */
    @Override
    protected void onPreExecute() {
        if (mHttpListener != null) mHttpListener.onBeforeConnect();
    }

    /**
     * Http result is passed to this method as parameter
     *
     * @param stringContent Whether or not to throw an exception
     */
    @Override
    protected void onPostExecute(String stringContent) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(stringContent);
            JSONObject response = jsonObject.getJSONObject(JSON_ROOT_TAG);
            if (mHttpListener != null) mHttpListener.onResponseOk(response);
        } catch (JSONException e) {
            if (mHttpListener != null) mHttpListener.onResponseNull();
        }
    }

    public void setHttpListener(HttpListener listener) {
        mHttpListener = listener;
    }

    /**
     * Http listener interface
     */
    public interface HttpListener {

        void onResponseOk(JSONObject result);
        void onResponseNull();
        void onResponseError(String errorMessage);
        void onError(String errorMessage);
        void onBeforeConnect();
    }
}
