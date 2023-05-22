package dev.zanckor.overlayspotify;

import dev.zanckor.overlayspotify.common.event.EventHandler;
import dev.zanckor.overlayspotify.server.Authorization;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main extends Application {
    public static Text currentLyric;
    public static Text prevLyric;
    public static Text nextLyric;
    public static EventHandler eventHandler = null;

    @Override
    public void start(Stage screen) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main_screen.fxml"));
        Parent root = fxmlLoader.load();
        screen.initStyle(StageStyle.UNDECORATED);

        screen.setTitle("Spotify Overlay");
        screen.setAlwaysOnTop(true);

        Scene scene = new Scene(root);
        screen.setScene(scene);
        screen.show();

        currentLyric = (Text) scene.lookup("#currentLyrics");
        currentLyric.setWrappingWidth(540);
        currentLyric.setTextAlignment(TextAlignment.CENTER);

        prevLyric = (Text) scene.lookup("#prevLine");
        prevLyric.setWrappingWidth(540);
        prevLyric.setTextAlignment(TextAlignment.CENTER);

        nextLyric = (Text) scene.lookup("#nextLine");
        nextLyric.setWrappingWidth(540);
        nextLyric.setTextAlignment(TextAlignment.CENTER);
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        Authorization.requestAuth();
        registerEventHandler();

        launch();
    }


    public static void registerEventHandler() {
        EventHandler eventHandler = new EventHandler();
        Main.eventHandler = eventHandler;

        eventHandler.registerSubclasses();
    }
}