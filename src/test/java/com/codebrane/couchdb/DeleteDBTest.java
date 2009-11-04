package com.codebrane.couchdb;

import org.junit.Test;
import org.junit.Assert;

/**
 * Test that deletes a CouchDB database
 *
 * @author alistair
 */
public class DeleteDBTest extends CouchDBTest {
  @Test
  public void test() {
    System.out.println("DeleteDBTest");
    CouchPotato cp = new CouchPotato(props.getString(PROP_COUCHDBSERVER));
    cp.connect();
    CouchPotatoResult cpResult = cp.deleteDatabase(TEST_DB_NAME);
    Assert.assertNotNull(cpResult);
    Assert.assertEquals("true", cpResult.ok);
    cp.disconnect();
  }
}
