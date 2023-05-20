package dev.zanckor.overlayspotify.common.util;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class HttpManager {
    public static String httpRequest(String URLString, String requestMethod, HashMap<String, String> requestProperties, String requestBody) throws IOException {
        URL url = new URL(URLString);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();

        http.setDoOutput(true);
        http.setDoInput(true);
        http.setRequestMethod(requestMethod);

        if (requestProperties != null) requestProperties.forEach(http::setRequestProperty);

        http.connect();

        //If connection has properties, send the length of the message:
        if (requestBody != null) {
            try (OutputStream outputStream = http.getOutputStream()) {
                outputStream.write(requestBody.getBytes(StandardCharsets.UTF_8));
            }
        }

        String response = IOUtils.toString(http.getInputStream(), StandardCharsets.UTF_8);
        http.disconnect();

        return response;
    }
}
