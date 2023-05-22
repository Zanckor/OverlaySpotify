package dev.zanckor.overlayspotify.common.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Util {
    public static ScheduledExecutorService executorService;

    public static void runnableTasks(ParameterFunction function, long period) {
        Runnable runnableTask = function::run;

        executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(runnableTask, 0, period, TimeUnit.SECONDS);
    }
}
