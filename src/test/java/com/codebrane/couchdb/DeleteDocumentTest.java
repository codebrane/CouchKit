package com.codebrane.couchdb;

import org.junit.Test;
import org.junit.Assert;

/**
 * Test that deletes a document from a CouchDB database
 *
 * @author alistair
 */
public class DeleteDocumentTest extends CouchDBTest {
  @Test
  public void test() {
    System.out.println("DeleteDocumentTest");
    CouchPotato cp = new CouchPotato(props.getString(PROP_COUCHDBSERVER));
    cp.connect();
    CouchPotatoDocument cpDoc = (CouchPotatoDocument)cp.getDocument(TEST_DB_NAME, TEST_DOC_ID, CouchPotatoDocument.class);
    CouchPotatoResult cpResult = cp.deleteDocument(TEST_DB_NAME, TEST_DOC_ID, cpDoc.getRev());
    Assert.assertNotNull(cpResult);
    Assert.assertEquals("true", cpResult.ok);
    cp.disconnect();
  }
}
