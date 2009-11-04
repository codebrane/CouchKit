package com.codebrane.couchdb;

import org.junit.Test;
import org.junit.Assert;
import static junit.framework.Assert.fail;

/**
 * Test that gets a document from a CouchDB database
 *
 * @author alistair
 */
public class GetDocumentTest extends CouchDBTest {
  @Test
  public void test() {
    System.out.println("GetDocumentTest");
    CouchPotato cp = new CouchPotato(couchDBServer);
    cp.connect();

    boolean found = true;
    try {
      // Try to get a non existent document
      cp.getDocument(TEST_DB_NAME, "nonexistentid", null, CouchPotatoDocument.class);
    }
    catch(CouchPotatoException cpe) {
      Assert.assertEquals(CouchPotatoException.OBJECT_NOT_FOUND, cpe.getReason());
      found = false;
    }
    if (found) {
      cp.disconnect();
      fail("Found non existent document!");      
    }

    found = true;
    try {
      // Try to get an existing document but with a non existent rev
      cp.getDocument(TEST_DB_NAME, TEST_DOC_ID, "nonexistentrev", CouchPotatoDocument.class);
    }
    catch(CouchPotatoException cpe) {
      Assert.assertEquals(CouchPotatoException.BAD_REQUEST, cpe.getReason());
      found = false;
    }
    if (found) {
      cp.disconnect();
      fail("Found non existent document!");
    }

    try {
      CouchPotatoDocument cpDoc = (CouchPotatoDocument)cp.getDocument(TEST_DB_NAME, TEST_DOC_ID, null, CouchPotatoDocument.class);
      Assert.assertNotNull(cpDoc);
      Assert.assertEquals(TEST_DOC_ID, cpDoc.getId());
      Assert.assertNotNull(cpDoc.getRev());
      Assert.assertEquals(TEST_DOC_SUBJECT, cpDoc.getSubject());
      Assert.assertEquals(TEST_DOC_AUTHOR, cpDoc.getAuthor());
      Assert.assertEquals(TEST_DOC_POSTED_DATE, cpDoc.getPostedDate());
      Assert.assertNotNull(cpDoc.getTags());
      Assert.assertEquals(TEST_DOC_NUM_TAGS, cpDoc.getTags().length);
      Assert.assertEquals(TEST_DOC_BODY, cpDoc.getBody());
    }
    catch(CouchPotatoException cpe) {
      fail(cpe.getMessage());
    }
    finally {
      cp.disconnect();
    }
  }
}
