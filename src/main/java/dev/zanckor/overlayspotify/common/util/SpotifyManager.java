package dev.zanckor.overlayspotify.common.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.zanckor.overlayspotify.common.event.ClientEventHandler;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static dev.zanckor.overlayspotify.common.data.Data.accessToken;

public class SpotifyManager {
    static JsonArray songLyrics;

    public static JsonObject simpleBearerMessage(String URL) {
        HashMap<String, String> properties = new HashMap<>();

        //Adds properties
        properties.put("Authorization", "Bearer " + accessToken);

        //Obtain URL Connection's response (JSON) and transform it to the correct data (String) deleting redundant data
        try {
            return (JsonObject) JsonParser.parseString(HttpManager.httpRequest(URL, "GET", properties, null));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    public static String getSongName() {
        JsonObject songData = simpleBearerMessage("https://api.spotify.com/v1/me/player/currently-playing");

        JsonObject album = songData.get("item").getAsJsonObject();
        return album.get("name").getAsString();
    }
    public static List<String> getSongArtist() {
        JsonObject songData = simpleBearerMessage("https://api.spotify.com/v1/me/player/currently-playing");

        JsonObject album = songData.get("item").getAsJsonObject().get("album").getAsJsonObject();

        JsonArray artistsJson = album.get("artists").getAsJsonArray();
        List<String> artistNames = new ArrayList<>();

        for (JsonElement artistJson : artistsJson) {
            artistNames.add(artistJson.getAsJsonObject().get("name").getAsString());
        }

        return artistNames;
    }
    public static String getSongUrl() {
        JsonObject songData = simpleBearerMessage("https://api.spotify.com/v1/me/player/currently-playing");
        JsonObject album = songData.get("item").getAsJsonObject();

        String spotifyUrl = album.get("external_urls").getAsJsonObject().get("spotify").getAsString();
        return spotifyUrl;
    }


    public static int getSongCurrentMS() {
        JsonObject songData = simpleBearerMessage("https://api.spotify.com/v1/me/player/currently-playing");

        return songData.get("progress_ms").getAsInt();
    }

    public static void currentSongLyrics() throws IOException {
        String url = "https://spotify-lyric-api.herokuapp.com/?url=" + SpotifyManager.getSongUrl() + "&format=lrc";

        String response = HttpManager.httpRequest(url, "GET", null, null);
        JsonObject jsonSongData = JsonParser.parseString(response).getAsJsonObject();
        songLyrics = jsonSongData.get("lines").getAsJsonArray();
    }

    public static String getLyricsPerLine(int currentSecond, int lineOffset) {
        AtomicReference<String> text = new AtomicReference<>();
        AtomicInteger lineIndex = new AtomicInteger();
        if(songLyrics == null) {
            try {
                SpotifyManager.currentSongLyrics();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        songLyrics.forEach(jsonObjectLine -> {
            String line = jsonObjectLine.getAsJsonObject().get("timeTag").getAsString();

            String stringTimeOfLine = line.substring(0, line.length() - 3);
            DateFormat dateFormat = new SimpleDateFormat("mm:ss");

            try {
                Date reference = dateFormat.parse("00:00");
                Date date = dateFormat.parse(stringTimeOfLine);

                int lineLRC = Math.toIntExact((date.getTime() - reference.getTime()) / 1000);

                if (currentSecond / 1000 == lineLRC) {
                    JsonElement lyrics = songLyrics.get(lineIndex.get() + lineOffset).getAsJsonObject().get("words");
                    text.set(lyrics.getAsString());
                }

                lineIndex.getAndIncrement();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });

        return text.get();
    }
}
