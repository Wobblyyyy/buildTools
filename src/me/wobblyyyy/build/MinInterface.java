package me.wobblyyyy.build;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class MinInterface
{
    public static String minify (byte[] bytes) throws IOException
    {
        StringBuilder ret = new StringBuilder();
        URL url = new URL("https://javascript-minifier.com/raw");
        String data = URLEncoder.encode("input", StandardCharsets.UTF_8) +
                '=' +
                URLEncoder.encode(new String(bytes), StandardCharsets.UTF_8);
        bytes = data.getBytes(StandardCharsets.UTF_8);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("charset", "utf-8");
        connection.setRequestProperty("Content-Length", Integer.toString(bytes.length));
        try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream()))
        {
            wr.write(bytes);
        }
        int responseCode = connection.getResponseCode();
        if (responseCode == 200)
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String ip;
            while ((ip = in.readLine()) != null)
            {
                ret.append(ip);
            }
            in.close();
        }
        return ret.toString();
    }
}
