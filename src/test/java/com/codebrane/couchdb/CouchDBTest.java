package com.codebrane.couchdb;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import static junit.framework.Assert.fail;

import java.util.ResourceBundle;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Base class for all tests. Before running the tests, change:<br />
 * src/test/resources/test.properties : couchdb.server
 *
 * @author alistair
 */
public abstract class CouchDBTest {
  protected static final String TEST_DB_NAME = "testdb";

  protected static final String TEST_DOC_ID = "111";
  protected static final String TEST_DOC_SUBJECT = "I like Cakes!";
  protected static final String TEST_DOC_AUTHOR = "codeBrane";
  protected static final String TEST_DOC_POSTED_DATE = "2009-11-03T17:30:12-04:00";
  protected static final String[] TEST_DOC_TAGS = new String[] {"cakes", "eating", "munching"};
  protected static final int TEST_DOC_NUM_TAGS = 3;
  protected static final String TEST_DOC_BODY = "I do like eating cakes you know!";

  // Definitions from /testdoc.json
  protected static final String TEST_JSON_DOC_ID = "test_json_doc";
  protected static final String TEST_JSON_DOC_REV = "1234";

  protected static ResourceBundle props = null;
  protected static String couchDBServer = null;
  protected static String testJSON = null;
  protected static CouchPotatoDocument cpTestDoc = null;
  
  @BeforeClass
  public static void startup() {
    props = ResourceBundle.getBundle("test");
    couchDBServer = props.getString("couchdb.server");
    loadTestDocument();
    loadTestJSONDocument();
  }

  @AfterClass
  public static void teardown() {
  }

  public static void loadTestDocument() {
    cpTestDoc = new CouchPotatoDocument();
    cpTestDoc.setSubject(TEST_DOC_SUBJECT);
    cpTestDoc.setAuthor(TEST_DOC_AUTHOR);
    cpTestDoc.setPostedDate(TEST_DOC_POSTED_DATE);
    cpTestDoc.setTags(TEST_DOC_TAGS);
    cpTestDoc.setBody(TEST_DOC_BODY);
  }

  protected static void loadTestJSONDocument() {
    testJSON = "";
    try {
      FileReader payloadFileReader = new FileReader(CouchDBTest.class.getResource("/testdoc.json").getPath());
      BufferedReader bReader = new BufferedReader(payloadFileReader);
      String buffer = "";
      while ((buffer = bReader.readLine()) != null) {
        testJSON = testJSON + buffer;
      }
      bReader.close();
      payloadFileReader.close();
    }
    catch(FileNotFoundException fnfe) {
      fail(fnfe.getMessage());
    }
    catch(IOException ioe) {
      fail(ioe.getMessage());
    }
  }
}
