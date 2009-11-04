package com.codebrane.couchdb;

import org.junit.runners.Suite;
import org.junit.runner.RunWith;

@RunWith(Suite.class)
@Suite.SuiteClasses( { ConnectionTest.class,
                       CreateDBTest.class,
                       AddDocumentTest.class,
                       GetDocumentTest.class,
                       DeleteDocumentTest.class,
                       DeleteDBTest.class} )
public class TestSuite {
}
