package com.codebrane.couchdb;

import org.junit.Test;
import org.junit.Assert;
import static junit.framework.Assert.fail;

/**
 * Test that creates a CouchDB database
 *
 * @author alistair
 */
public class CreateDBTest extends CouchDBTest {
  @Test
  public void test() {
    System.out.println("CreateDBTest");
    CouchPotato cp = new CouchPotato(couchDBServer);
    cp.connect();
    try {
      CouchPotatoResult cpResult = cp.createDatabase(TEST_DB_NAME);
      Assert.assertNotNull(cpResult);
      Assert.assertEquals("true", cpResult.ok);
    }
    catch(CouchPotatoException cpe) {
      fail(cpe.getMessage());
    }
    finally {
      cp.disconnect();
    }
  }
}
