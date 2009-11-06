package com.codebrane.couchdb;

import org.junit.Test;
import org.junit.Assert;
import static junit.framework.Assert.fail;

/**
 * Test that simulates a bad connection
 *
 * @author alistair
 */
public class ConnectionTest extends CouchDBTest {
  @Test
  public void test() {
    System.out.println("ConnectionTest");
    CouchKit cp = new CouchKit("http://localhost:11111");
    cp.connect();

    boolean connected = true;
    try {
      CouchKitResult cpResult = cp.createDatabase(TEST_DB_NAME);
    }
    catch(CouchKitException cpe) {
      Assert.assertEquals(CouchKitException.CONNECTION_ERROR, cpe.getReason());
      connected = false;
    }

    cp.disconnect();

    if (connected) {
      fail("Connection to non existent server succeeded!");
    }
  }
}
