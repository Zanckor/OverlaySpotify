package dev.zanckor.overlayspotify.common.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class EventHandler {
    List<EventHandler> eventHandlers = new ArrayList<>();

    public void tick(){
        eventHandlers.forEach(EventHandler::tick);
    }


    public void startTicker(){
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                tick();
            }
        };

        timer.schedule(task, 0, 1000);
    }
    public void registerSubclasses() {
        eventHandlers.add(new ClientEventHandler());
    }
}
