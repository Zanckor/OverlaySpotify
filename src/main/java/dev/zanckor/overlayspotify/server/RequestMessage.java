package dev.zanckor.overlayspotify.server;

import dev.zanckor.overlayspotify.common.data.Data;

public class RequestMessage {
    public static void getToken(){
        System.out.println(Data.accessToken);
    }
}
