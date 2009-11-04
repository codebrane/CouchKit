package com.codebrane.couchdb;

import org.junit.Test;
import org.junit.Assert;

/**
 * Test that creates a CouchDB database
 *
 * @author alistair
 */
public class CreateDBTest extends CouchDBTest {
  @Test
  public void test() {
    System.out.println("CreateDBTest");
    CouchPotato cp = new CouchPotato(props.getString(PROP_COUCHDBSERVER));
    cp.connect();
    CouchPotatoResult cpResult = cp.createDatabase(TEST_DB_NAME);
    Assert.assertNotNull(cpResult);
    Assert.assertEquals("true", cpResult.ok);
    cp.disconnect();
  }
}
