package dev.zanckor.overlayspotify.common.event;

import dev.zanckor.overlayspotify.Main;
import dev.zanckor.overlayspotify.common.util.SpotifyManager;
import dev.zanckor.overlayspotify.common.util.Util;

import java.io.IOException;

public class ClientEventHandler extends EventHandler {
    public static int currentSongSecond = 0;

    @Override
    public void tick() {
        currentSongSecond += 1000;
        String currentLine = SpotifyManager.getLyricsPerLine(currentSongSecond, 0);
        String nextLine = SpotifyManager.getLyricsPerLine(currentSongSecond, 1);
        String prevLine = SpotifyManager.getLyricsPerLine(currentSongSecond, -1);

        if (currentLine != null) Main.currentLyric.setText(currentLine);
        if (nextLine != null) Main.nextLyric.setText(nextLine);
        if (prevLine != null) Main.prevLyric.setText(prevLine);
    }

    public static void syncTimer() {
        Util.runnableTasks(() -> {
            currentSongSecond = SpotifyManager.getSongCurrentMS();

            try {
                SpotifyManager.currentSongLyrics();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, 5);
    }
}
