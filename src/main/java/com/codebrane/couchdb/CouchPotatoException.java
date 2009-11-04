package com.codebrane.couchdb;

/**
 * Encapsulates things that go wrong with CouchDB
 *
 * @author alistair
 */
public class CouchPotatoException extends Exception {
  public CouchPotatoException() {}

  public CouchPotatoException(String message) {
    super(message);
  }

  public CouchPotatoException(Exception e) {
    super(e);
  }
}
