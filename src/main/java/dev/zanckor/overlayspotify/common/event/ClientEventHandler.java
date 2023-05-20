package dev.zanckor.overlayspotify.common.event;

import dev.zanckor.overlayspotify.Main;
import dev.zanckor.overlayspotify.common.util.SpotifyManager;
import dev.zanckor.overlayspotify.common.util.Util;

import java.io.IOException;

import static dev.zanckor.overlayspotify.Main.eventHandler;

public class ClientEventHandler extends EventHandler {
    static int currentSongSecond = 0;

    @Override
    public void tick() {
        currentSongSecond += 1000;
        String currentLine = SpotifyManager.getLyricsPerLine(currentSongSecond, 0);
        String nextLine = SpotifyManager.getLyricsPerLine(currentSongSecond, 1);

        if (currentLine != null) Main.currentLyric.setText(currentLine);
        if (currentLine != null) Main.nextLyric.setText(nextLine);
    }

    public static void syncTimer() {
        Util.runnableTasks(() -> {
            currentSongSecond = SpotifyManager.getSongCurrentMS();

            try {
                SpotifyManager.currentSongLyrics();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, 10);
    }
}

