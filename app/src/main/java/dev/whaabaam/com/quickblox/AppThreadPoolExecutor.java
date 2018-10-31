package dev.whaabaam.com.quickblox;
/*
 * Created by RahulGupta on 4/9/18
 */

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppThreadPoolExecutor {
    private static int cores = Runtime.getRuntime().availableProcessors();
    private static ExecutorService executor = Executors.newFixedThreadPool(cores + 1);

    public void stop() {
        executor.shutdown();
    }

    private Callable<Boolean> loginUser() {
        return () -> {
            return true;

        };
    }
}
