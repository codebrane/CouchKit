package com.codebrane.couchdb;

import org.junit.Test;
import org.junit.Assert;
import static junit.framework.Assert.fail;

/**
 * Test that adds a document to a CouchDB database
 *
 * @author alistair
 */
public class AddDocumentTest extends CouchDBTest {
  @Test
  public void test() {
    CouchKit cp = new CouchKit(couchDBServer);
    cp.connect();
    try {
      // Add a domain object
      System.out.println("AddDocumentTest : domain object");
      CouchKitResult cpResult = cp.addDocument(TEST_DB_NAME, TEST_DOC_ID, cpTestDoc);
      Assert.assertNotNull(cpResult);
      Assert.assertEquals("true", cpResult.ok);
      Assert.assertEquals("111", cpResult.id);

      // Add a raw JSON object
      System.out.println("AddDocumentTest : JSON");
      CouchKitResult cpJSONResult = cp.addDocument(TEST_DB_NAME, TEST_JSON_DOC_ID, testJSON);
      Assert.assertNotNull(cpJSONResult);
      Assert.assertEquals("true", cpJSONResult.ok);
      Assert.assertEquals(TEST_JSON_DOC_ID, cpJSONResult.id);
    }
    catch(CouchKitException cpe) {
      fail(cpe.getMessage());
    }
    finally {
      cp.disconnect();
    }
  }
}
