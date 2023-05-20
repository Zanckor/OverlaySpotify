package dev.zanckor.overlayspotify.server;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpServer;
import dev.zanckor.overlayspotify.common.data.Data;
import dev.zanckor.overlayspotify.common.util.HttpManager;
import se.michaelthelin.spotify.Base64;

import java.awt.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.stream.Collectors;

import static dev.zanckor.overlayspotify.common.data.Data.*;

public class Authorization {
    public static void requestAuth() throws URISyntaxException, IOException {
        //Create a temporal Server on port 8888 of my own address to obtain the response of connection
        HttpServer server = HttpServer.create(new InetSocketAddress(8888), 0);
        server.createContext("/callback", new CallbackHandler());
        server.setExecutor(null);
        server.start();

        //Start connection on default browser
        String authURL = "https://accounts.spotify.com/authorize?" +
                "response_type=code" +
                "&client_id=" + URLEncoder.encode(clientID, StandardCharsets.UTF_8) +
                "&scope=" + URLEncoder.encode("user-read-private user-read-email user-library-read playlist-read-private user-read-playback-state user-modify-playback-state", StandardCharsets.UTF_8) +
                "&redirect_uri=" + URLEncoder.encode(redirectURI, StandardCharsets.UTF_8);

        Desktop.getDesktop().browse(new URI(authURL));
    }

    public static void requestToken(String connectionCode) throws IOException {
        String tokenURL = "https://accounts.spotify.com/api/token";
        HashMap<String, String> properties = new HashMap<>();

        //Adds properties
        properties.put("grant_type", "authorization_code");
        properties.put("redirect_uri", redirectURI);
        properties.put("code", connectionCode);

        String authString = Base64.encode((clientID + ":" + clientSecret).getBytes(StandardCharsets.UTF_8));
        properties.put("Authorization", "Basic " + authString);

        //Length of the message
        String requestBody = properties.entrySet().stream()
                .map(e -> e.getKey() + "=" + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

        properties.put("Content-Length", String.valueOf(requestBody.length()));

        //Obtain URL Connection's response (JSON) and transform it to the correct data (String) deleting redundant data
        JsonObject jsonObject = (JsonObject) JsonParser.parseString(HttpManager.httpRequest(tokenURL, "POST", properties, requestBody));
        String accessToken = jsonObject.get("access_token").getAsString();
        String refreshToken = jsonObject.get("refresh_token").getAsString();

        Data.accessToken = accessToken;
        Data.refreshToken = refreshToken;
    }
}
