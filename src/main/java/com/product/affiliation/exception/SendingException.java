package com.product.affiliation.exception;

public class SendingException extends RuntimeException {

  public SendingException(String message) {
    super(message);
  }

  public SendingException(Throwable cause) {
    super(cause);
  }
}
