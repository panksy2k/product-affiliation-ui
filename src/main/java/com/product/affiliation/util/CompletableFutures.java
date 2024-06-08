package com.product.affiliation.util;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CompletableFutures {

    /**
     * It waits until {@link CompletableFuture} is completed.
     * @param future the {@link CompletableFuture} to test
     * @param time the timeout
     * @param unitOfTime the timeout unit
     * @return {@code true} if completed, {@code false} if timed out.
     */
    public static boolean uncheckedAwait(CompletableFuture<?> future, long time, TimeUnit unitOfTime) throws InterruptedException {
        try {
            Objects.requireNonNull(future).get(time, Objects.requireNonNull(unitOfTime));
            return true;
        } catch (ExecutionException e) {
            return true;
        } catch(TimeoutException e) {
            return false;
        }
    }
}
