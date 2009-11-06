package com.codebrane.couchdb;

import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ClientProtocolException;

import java.io.IOException;

/**
 * Encapsulates things that go wrong with CouchDB
 *
 * @author alistair
 */
public class CouchKitException extends Exception {
  /** Can't find a document */
  public static final int OBJECT_NOT_FOUND = -1;
  /** Can mean non existent rev for a document */
  public static final int BAD_REQUEST = -1;
  /** Something went wrong when connecting */
  public static final int CONNECTION_ERROR = -2;
  /** Something went wrong! */
  public static final int PROTOCOL_ERROR = -3;

  private int reason;

  public CouchKitException() {}

  public CouchKitException(String message) {
    super(message);
  }

  public int getReason() {
    return reason;
  }

  public CouchKitException(Exception e) {
    super(e);

    if (e instanceof HttpResponseException) {
      if (((HttpResponseException)(e)).getStatusCode() == 404) {
        reason = OBJECT_NOT_FOUND;
      }

      if (((HttpResponseException)(e)).getStatusCode() == 400) {
        reason = BAD_REQUEST;
      }
    }
    else if (e instanceof ClientProtocolException) {
      reason = PROTOCOL_ERROR;
    }
    else if (e instanceof IOException) {
      reason = CONNECTION_ERROR;
    }
  }
}
