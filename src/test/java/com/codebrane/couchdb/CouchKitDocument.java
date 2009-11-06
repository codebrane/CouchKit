package com.codebrane.couchdb;

/**
 * Test class for storing to and retrieving from the test database
 */
public class CouchKitDocument {
  private String _id = null;
  private String _rev = null;

  private String subject = null;
  private String author = null;
  private String postedDate = null;
  private String[] tags = null;
  private String body = null;
  
  public CouchKitDocument() {}

  public String getId() { return _id; }
  public String getRev() { return _rev; }
  public String getSubject() { return subject; }
  public String getAuthor() { return author; }
  public String getPostedDate() { return postedDate; }
  public String[] getTags() { return tags; }
  public String getBody() { return body; }

  public void setSubject(String subject) { this.subject = subject; }
  public void setAuthor(String author) { this.author = author; }
  public void setPostedDate(String postedDate) { this.postedDate = postedDate; }
  public void setTags(String[] tags) { this.tags = tags; }
  public void setBody(String body) { this.body = body; }
}
