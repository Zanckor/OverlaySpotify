package dev.zanckor.overlayspotify.common.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Util {
    public static void runnableTasks(ParameterFunction function, long period) {
        Runnable runnableTask = function::run;

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(runnableTask, 0, period, TimeUnit.SECONDS);
    }
}
