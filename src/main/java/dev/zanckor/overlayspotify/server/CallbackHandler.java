package dev.zanckor.overlayspotify.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dev.zanckor.overlayspotify.common.data.Data;

import java.io.IOException;
import java.io.OutputStream;

public class CallbackHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery().substring(5);
        String response = "Connected successfully!";

        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream output = exchange.getResponseBody();
        output.write(response.getBytes());
        output.flush();
        exchange.close();

        Data.connectionCode = query;
        Authorization.requestToken(query);
    }
}
