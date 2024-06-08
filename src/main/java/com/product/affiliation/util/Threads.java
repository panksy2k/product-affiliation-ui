package com.product.affiliation.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class Threads {
    public static ExecutorService newFixedThreadPool(String connectorId, String threadName, int threadCount) {
        return Executors.newFixedThreadPool(threadCount, threadFactory(connectorId, threadName, true, false, t -> System.out.println("Finished the job for " + t.getName())));
    }

    public static ThreadFactory threadFactory(String connectorId, String threadName, boolean indexed, boolean daemon, Consumer<Thread> callback) {


        return new ThreadFactory() {
            private final AtomicInteger index = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                StringBuilder tname = new StringBuilder();
                tname.append(connectorId).append("-").append(threadName);

                if(indexed) {
                    tname.append("-").append(index.getAndIncrement());
                }

                Thread t = new Thread(r, tname.toString());
                t.setDaemon(daemon);

                if(callback != null) {
                    callback.accept(t);
                }
                return t;
            }
        };
    }
}
