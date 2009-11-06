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
    CouchKit cp = new CouchKit(couchDBServer);
    cp.connect();
    try {
      CouchKitResult cpResult = cp.createDatabase(TEST_DB_NAME);
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
