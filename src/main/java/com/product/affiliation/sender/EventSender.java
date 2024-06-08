package com.product.affiliation.sender;

import com.product.affiliation.events.ProductPayload;
import com.product.affiliation.exception.SendingException;
import java.io.Closeable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.apache.kafka.clients.producer.RecordMetadata;

public interface EventSender extends Closeable {

    Future<RecordMetadata> send(ProductPayload payload);

    default RecordMetadata blockingSend(ProductPayload payload) throws SendingException, InterruptedException {
      try {
        return send(payload).get();
      } catch(ExecutionException e) {
        throw new SendingException(e.getCause());
      }
    }
}
