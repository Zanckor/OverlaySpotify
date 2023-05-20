package dev.zanckor.overlayspotify.client;

import dev.zanckor.overlayspotify.Main;
import dev.zanckor.overlayspotify.common.event.ClientEventHandler;
import dev.zanckor.overlayspotify.common.util.SpotifyManager;
import dev.zanckor.overlayspotify.common.util.Util;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static dev.zanckor.overlayspotify.Main.eventHandler;

public class MainScreenController {
    @FXML
    private Button btClose;
    @FXML
    private Button btMinimize;
    @FXML
    private Button nextSong;
    @FXML
    private Button prevSong;
    @FXML
    private Button pauseSong;
    @FXML
    private Button sync;
    @FXML
    private Pane barTitle;
    @FXML
    private Text currentLyrics;

    double xOffset;
    double yOffset;

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
    public void clickOnPane(MouseEvent mouseEvent) {
        Stage stage = (Stage) barTitle.getScene().getWindow();

        xOffset = stage.getX() - mouseEvent.getScreenX();
        yOffset = stage.getY() - mouseEvent.getScreenY();
    }

    @FXML
    public void movePane(MouseEvent mouseEvent) {
        Stage stage = (Stage) barTitle.getScene().getWindow();

        stage.setX(mouseEvent.getScreenX() + xOffset);
        stage.setY(mouseEvent.getScreenY() + yOffset);
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
    public void nextSong(ActionEvent actionEvent) {
    }

    @FXML
    public void pauseSong(ActionEvent actionEvent) {
    }

    @FXML
    public void prevSong(ActionEvent actionEvent) {

    }
}