package com.example.listmaker.app.client;

import com.google.gwt.junit.tools.GWTTestSuite;
import junit.framework.Test;
import junit.framework.TestSuite;

public class GwtTestSuite extends GWTTestSuite {
  
  public static Test suite() {
    TestSuite suite = new TestSuite("Application Tests");
    suite.addTestSuite(GwtTestCompile.class);
    
    return suite;
  }
  
}