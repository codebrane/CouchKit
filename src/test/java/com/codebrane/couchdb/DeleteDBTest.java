package com.codebrane.couchdb;

import org.junit.Test;
import org.junit.Assert;
import static junit.framework.Assert.fail;

/**
 * Test that deletes a CouchDB database
 *
 * @author alistair
 */
public class DeleteDBTest extends CouchDBTest {
  @Test
  public void test() {
    System.out.println("DeleteDBTest");
    CouchKit cp = new CouchKit(couchDBServer);
    cp.connect();

    boolean found = true;
    try {
      // Try to delete a non existent database
      cp.deleteDatabase("nonexistentdb");
    }
    catch(CouchKitException cpe) {
      Assert.assertEquals(CouchKitException.OBJECT_NOT_FOUND, cpe.getReason());
      found = false;
    }
    if (found) {
      cp.disconnect();
      fail("Deleted non existent database!");
    }

    try {
      CouchKitResult cpResult = cp.deleteDatabase(TEST_DB_NAME);
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
