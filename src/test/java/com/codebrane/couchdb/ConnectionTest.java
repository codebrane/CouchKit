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
    CouchPotato cp = new CouchPotato("http://localhost:11111");
    cp.connect();

    boolean connected = true;
    try {
      CouchPotatoResult cpResult = cp.createDatabase(TEST_DB_NAME);
    }
    catch(CouchPotatoException cpe) {
      Assert.assertEquals(CouchPotatoException.CONNECTION_ERROR, cpe.getReason());
      connected = false;
    }

    cp.disconnect();

    if (connected) {
      fail("Connection to non existent server succeeded!");
    }
  }
}
