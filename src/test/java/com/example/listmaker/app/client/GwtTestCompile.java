package com.example.listmaker.app.client;

import com.google.gwt.junit.client.GWTTestCase;

/**
 * Created by david on 2/25/15.
 */
public class GwtTestCompile extends GWTTestCase {

    @Override
    public String getModuleName() {
        return "com.example.listmaker.app.Listmaker";
    }

    public void testSandbox() {
        assertTrue(true);
    }

}
