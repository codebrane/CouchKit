package com.codebrane.couchdb;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.google.gson.Gson;

/**
 * Provides a Java interface to a CouchDB set of databases and documents
 *
 * @author alistair
 */
public class CouchKit {
  /** For general queries */
  private static final int MODE_GET = 1;
  /** For adding documents */
  private static final int MODE_PUT = 2;
  /** For deleting documents and databases */
  private static final int MODE_DELETE = 3;

  private String couchDBServer = null;
  private HttpClient httpclient = null;

  /**
   * Constructor
   *
   * @param couchDBServer URL of the CouchDB server, e.g. http://localhost:5984
   */
  public CouchKit(String couchDBServer) {
    this.couchDBServer = couchDBServer;
  }

  /**
   * Sets up the connection to the CouchDB server
   */
  public void connect() {
    if (httpclient == null) {
      httpclient = new DefaultHttpClient();
    }
  }

  /**
   * Disconnects from the CouchDB server
   */
  public void disconnect() {
    if (httpclient != null) {
      httpclient.getConnectionManager().shutdown();
    }
  }

  /**
   * Creates a new database
   *
   * @param databaseName The name of the database
   * @return CouchKitResult describing the result of the database operation
   * @throws CouchKitException if an error occurs
   */
  public CouchKitResult createDatabase(String databaseName) throws CouchKitException {
    return (CouchKitResult)potato(MODE_PUT, databaseName, null, CouchKitResult.class);
  }

  /**
   * Deletes an existing database
   *
   * @param databaseName The name of the database
   * @return CouchKitResult describing the result of the database operation
   * @throws CouchKitException if an error occurs
   */
  public CouchKitResult deleteDatabase(String databaseName) throws CouchKitException {
    return (CouchKitResult)potato(MODE_DELETE, databaseName, null, CouchKitResult.class);
  }

  /**
   * Adds a document to a database
   *
   * @param databaseName The name of the database
   * @param documentID The ID of the document to add
   * @param document The document to add
   * @return CouchKitResult describing the result of the database operation
   * @throws CouchKitException if an error occurs
   */
  public CouchKitResult addDocument(String databaseName, String documentID, Object document) throws CouchKitException {
    return (CouchKitResult)potato(MODE_PUT, databaseName + "/" + documentID, toJSON(document), CouchKitResult.class);
  }

  /**
   * Deletes a document from a database
   *
   * @param databaseName The name of the database
   * @param documentID The ID of the document to add
   * @param documentRev The rev of the document to delete
   * @return CouchKitResult describing the result of the database operation
   * @throws CouchKitException if an error occurs
   */
  public CouchKitResult deleteDocument(String databaseName, String documentID, String documentRev) throws CouchKitException {
    return (CouchKitResult)potato(MODE_DELETE, databaseName + "/" + documentID + "?rev=" + documentRev,
                                     null, CouchKitResult.class);
  }

  /**
   * Gets a document from a database
   * @param databaseName The name of the database
   * @param documentID The ID of the document to get
   * @param documentRev The rev of the document or null for the latest rev
   * @param documentClass The document class
   * @return Returns an Object that should be casted to the type of documentClass
   * @throws CouchKitException if an error occurs
   */
  public Object getDocument(String databaseName, String documentID, String documentRev, Class documentClass) throws CouchKitException {
    String url = null;
    if (documentRev == null) {
      url = databaseName + "/" + documentID;
    }
    else {
      url = databaseName + "/" + documentID + "?rev=" + documentRev;
    }
    return potato(MODE_GET, url, null, documentClass);
  }

  /**
   * Converts a Java object to JSON
   *
   * @param obj The object to convert
   * @return String representing the object as JSON
   */
  public String toJSON(Object obj) {
    if (obj instanceof String) {
      return (String)obj;
    }
    
    Gson gson = new Gson();
    return gson.toJson(obj);  
  }

  /**
   * Handles interaction with the CouchDB server
   *
   * @param mode MODE_GET or MODE_PUT or MODE_DELETE
   * @param databaseName The name of the database to work with
   * @param data The data to be sent to CouchDB
   * @param clazz The type of data that will be assembled from returned JSON
   * @return Returns an Object that should be casted to the type of clazz
   * @throws CouchKitException if an error occurs
   */
  private Object potato(int mode, String databaseName, String data, Class clazz) throws CouchKitException {
    HttpRequestBase requestType = null;

    if (mode == MODE_GET) {
      requestType = new HttpGet(couchDBServer + "/" + databaseName);
    }
    else if (mode == MODE_PUT) {
      requestType = new HttpPut(couchDBServer + "/" + databaseName);
      if (data != null) {
        try {
          ((HttpPut)(requestType)).setEntity(new StringEntity(data));
        }
        catch(UnsupportedEncodingException use) {
          throw new CouchKitException(use);
        }
      }
    }
    else if (mode == MODE_DELETE) {
      requestType = new HttpDelete(couchDBServer + "/" + databaseName);
    }
    else {
      throw new CouchKitException("Unknown Request Type");
    }

    ResponseHandler<String> responseHandler = new BasicResponseHandler();
    try {
      String responseBody = httpclient.execute(requestType, responseHandler);
      Gson gson = new Gson();
      return gson.fromJson(responseBody, clazz);
    }
    catch(HttpResponseException hre) {
      throw new CouchKitException(hre);
    }
    catch(ClientProtocolException cpe) {
      throw new CouchKitException(cpe);
    }
    catch(IOException ioe) {
      throw new CouchKitException(ioe);
    }
  }
}
