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
    System.out.println("AddDocumentTest");
    CouchPotato cp = new CouchPotato(couchDBServer);
    cp.connect();
    try {
      CouchPotatoResult cpResult = cp.addDocument(TEST_DB_NAME, TEST_DOC_ID, cpTestDoc);
      Assert.assertNotNull(cpResult);
      Assert.assertEquals("true", cpResult.ok);
      Assert.assertEquals("111", cpResult.id);
    }
    catch(CouchPotatoException cpe) {
      fail(cpe.getMessage());
    }
    finally {
      cp.disconnect();
    }
  }
}
