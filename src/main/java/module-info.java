module dev.zanckor.overlayspotify {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.commons.io;
    requires java.desktop;
    requires com.google.gson;
    requires jdk.httpserver;
    requires se.michaelthelin.spotify;


    opens dev.zanckor.overlayspotify to javafx.fxml;
    exports dev.zanckor.overlayspotify;
    exports dev.zanckor.overlayspotify.client;
    opens dev.zanckor.overlayspotify.client to javafx.fxml;
}