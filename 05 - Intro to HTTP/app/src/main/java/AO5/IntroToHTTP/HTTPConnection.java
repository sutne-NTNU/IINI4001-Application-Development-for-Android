package AO5.IntroToHTTP;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Objects;


class HTTPConnection
{
    private MainActivity activity;
    private String urlToServer;
    private final String TAG = "HTTP Connection";
    final String ENCODING = "ISO-8859-1";

    public HTTPConnection(MainActivity activity, String urlToServer)
    {
        this.activity = activity;
        this.urlToServer = urlToServer;
        CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
    }

    public void startThread(Map<String, String> requestValues)
    {
        new RequestThread().execute(requestValues);
    }


    private class RequestThread extends AsyncTask<Map<String, String>, String, String>
    {
        @SafeVarargs
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        protected final String doInBackground(Map<String, String>... v)
        {
            try
            {
                return GET(v[0]);
            }
            catch (Exception e)
            {
                Log.e(TAG, Objects.requireNonNull(e.getMessage()));
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String response)
        {
            activity.setResponse(response);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String GET(Map<String, String> parameterList) throws IOException
    {
        String url = urlToServer + "?" + encodeParameters(parameterList);
        URLConnection connection = new URL(url).openConnection();
        connection.setRequestProperty("Accept-Charset", ENCODING);

        try (InputStream response = connection.getInputStream())
        {
            return this.readResponseBody(response, getCharSet(connection));
        }
    }


    private String encodeParameters(Map<String, String> parameters)
    {
        StringBuilder s = new StringBuilder();
        for (String key : parameters.keySet())
        {
            String value = parameters.get(key);
            try
            {
                s.append(URLEncoder.encode(key, ENCODING));
                s.append("=");
                s.append(URLEncoder.encode(value, ENCODING));
                s.append("&");
            }
            catch (UnsupportedEncodingException e)
            {
                Log.e(TAG, e.toString());
            }
        }
        return s.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private String readResponseBody(InputStream is, String charset)
    {
        StringBuilder body = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, charset)))
        {
            for (String line; (line = reader.readLine()) != null; )
            {
                body.append(line);
            }
        }
        catch (Exception e)
        {
            Log.e(TAG, e.toString());
        }
        return body.toString();
    }

    private String getCharSet(URLConnection connection)
    {
        String contentType = connection.getHeaderField("Content-Type");
        String charset = null;

        for (String param : contentType.replace(" ", "").split(";"))
        {
            if (param.startsWith("charset="))
            {
                charset = param.split("=", 2)[1];
                break;
            }
        }
        return charset;
    }
}
