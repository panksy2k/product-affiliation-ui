package com.product.affiliation.util;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CompletableFutures {
    public static final CompletableFuture<List<String>> completedEmptyList = CompletableFuture.completedFuture(Collections.<String>emptyList());

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

    public static CompletionException throwCompletionExceptionIfNot(Throwable e) {
      if(e instanceof CompletionException) {
        return (CompletionException) e;
      }

      return new CompletionException(e);
    }
}
