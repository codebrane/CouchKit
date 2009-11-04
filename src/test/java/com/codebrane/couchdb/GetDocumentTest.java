package com.codebrane.couchdb;

import org.junit.Test;
import org.junit.Assert;

/**
 * Test that gets a document from a CouchDB database
 *
 * @author alistair
 */
public class GetDocumentTest extends CouchDBTest {
  @Test
  public void test() {
    System.out.println("GetDocumentTest");
    CouchPotato cp = new CouchPotato(props.getString(PROP_COUCHDBSERVER));
    cp.connect();
    CouchPotatoDocument cpDoc = (CouchPotatoDocument)cp.getDocument(TEST_DB_NAME, TEST_DOC_ID, CouchPotatoDocument.class);
    Assert.assertNotNull(cpDoc);
    Assert.assertEquals(TEST_DOC_ID, cpDoc.getId());
    Assert.assertNotNull(cpDoc.getRev());
    Assert.assertEquals(TEST_DOC_SUBJECT, cpDoc.getSubject());
    Assert.assertEquals(TEST_DOC_AUTHOR, cpDoc.getAuthor());
    Assert.assertEquals(TEST_DOC_POSTED_DATE, cpDoc.getPostedDate());
    Assert.assertNotNull(cpDoc.getTags());
    Assert.assertEquals(TEST_DOC_NUM_TAGS, cpDoc.getTags().length);
    Assert.assertEquals(TEST_DOC_BODY, cpDoc.getBody());
    cp.disconnect();
  }
}
