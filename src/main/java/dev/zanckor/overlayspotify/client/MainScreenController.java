package dev.zanckor.overlayspotify.client;

import dev.zanckor.overlayspotify.common.event.ClientEventHandler;
import dev.zanckor.overlayspotify.common.util.SpotifyManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

import static dev.zanckor.overlayspotify.Main.eventHandler;

public class MainScreenController {
    @FXML
    private Button btClose;
    @FXML
    private Button btMinimize;
    @FXML
    private Button sync;
    @FXML
    private Pane barTitle;
    @FXML
    private ImageView imageBackground;

    @FXML
    protected void closeScreen(ActionEvent e) {
        Stage stage = (Stage) btClose.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void minimizeScreen(ActionEvent e) {
        Stage stage = (Stage) btMinimize.getScene().getWindow();

        stage.setIconified(true);
    }

    @FXML
    public void movePane(MouseEvent mouseEvent) {
        Stage stage = (Stage) barTitle.getScene().getWindow();

        stage.setX(mouseEvent.getScreenX() - 255);
        stage.setY(mouseEvent.getScreenY());
    }

    @FXML
    public void minimizeBtGray(MouseEvent mouseEvent) {
        btMinimize.setStyle("-fx-background-color: #404040");
    }

    @FXML
    public void closeBtRed(MouseEvent mouseEvent) {
        btClose.setStyle("-fx-background-color: red");
    }

    @FXML
    public void btBlack(MouseEvent mouseEvent) {
        btMinimize.setStyle("-fx-background-color: black");
        btClose.setStyle("-fx-background-color: black");
    }

    @FXML
    public void startLyrics(ActionEvent actionEvent) {
        sync.setDisable(true);
        sync.setVisible(false);

        ClientEventHandler.syncTimer();
        eventHandler.startTicker();
    }

    @FXML
    public void reloadSong(ActionEvent actionEvent) throws IOException {
        ClientEventHandler.currentSongSecond = SpotifyManager.getSongCurrentMS();
        SpotifyManager.currentSongLyrics();
    }

    public void changeBG(ActionEvent actionEvent) {
        imageBackground.setVisible(!imageBackground.isVisible());
    }
}