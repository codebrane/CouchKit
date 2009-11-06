package com.codebrane.couchdb;

import org.junit.Test;
import org.junit.Assert;
import static junit.framework.Assert.fail;

/**
 * Test that deletes a document from a CouchDB database
 *
 * @author alistair
 */
public class DeleteDocumentTest extends CouchDBTest {
  @Test
  public void test() {
    System.out.println("DeleteDocumentTest");
    CouchKit cp = new CouchKit(couchDBServer);
    cp.connect();

    boolean found = true;
    try {
      // Try to delete a non existent document
      cp.deleteDocument(TEST_DB_NAME, "nonexistentid", "nonexistentrev");
    }
    catch(CouchKitException cpe) {
      Assert.assertEquals(CouchKitException.OBJECT_NOT_FOUND, cpe.getReason());
      found = false;
    }
    if (found) {
      cp.disconnect();
      fail("Found non existent document!");
    }

    found = true;
    try {
      // Try to delete the document but with a non existent rev
      cp.deleteDocument(TEST_DB_NAME, TEST_DOC_ID, "nonexistentrev");
    }
    catch(CouchKitException cpe) {
      Assert.assertEquals(CouchKitException.BAD_REQUEST, cpe.getReason());
      found = false;
    }
    if (found) {
      cp.disconnect();
      fail("Found non existent rev!");
    }

    try {
      CouchKitDocument cpDoc = (CouchKitDocument)cp.getDocument(TEST_DB_NAME, TEST_DOC_ID, null, CouchKitDocument.class);
      CouchKitResult cpResult = cp.deleteDocument(TEST_DB_NAME, TEST_DOC_ID, cpDoc.getRev());
      Assert.assertNotNull(cpResult);
      Assert.assertEquals("true", cpResult.ok);
    }
    catch(CouchKitException cpe) {
      fail(cpe.getMessage());
    }
    finally {
      cp.disconnect();
    }
  }
}
