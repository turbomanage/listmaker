package com.example.listmaker.test.helper;

import com.example.listmaker.test.UserTestCase;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalTaskQueueTestConfig;
import com.example.listmaker.app.shared.domain.User;
import com.turbomanage.gwt.server.servlet.AuthFilter;
import junit.framework.TestCase;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * A simple test base class that can be extended to build unit tests that
 * properly construct and tear down AppEngine test environments.
 */
public abstract class BaseTest extends TestCase {

    protected LocalServiceTestHelper helper;
    private LocalDatastoreServiceTestConfig datastoreConfig;
    private LocalMemcacheServiceTestConfig memcacheConfig;
    private LocalTaskQueueTestConfig taskQueueConfig;

    /**
     * Sets up the AppEngine environment and initializes Guice.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        datastoreConfig = new LocalDatastoreServiceTestConfig();
        memcacheConfig = new LocalMemcacheServiceTestConfig();
        taskQueueConfig = new LocalTaskQueueTestConfig();
        taskQueueConfig.setQueueXmlPath("src/main/webapp/WEB-INF/queue.xml");

        HashMap<String, Object> envAttr = new HashMap<String, Object>();
        envAttr.put("com.google.appengine.api.users.UserService.user_id_key", "13");

        helper = new LocalServiceTestHelper(datastoreConfig, taskQueueConfig);
        helper.setEnvAuthDomain("auth");
        helper.setEnvEmail("test@example.com");
        helper.setEnvAttributes(envAttr);
        helper.setEnvIsAdmin(true);
        helper.setEnvIsLoggedIn(true);
        helper.setUp();
        User user = UserTestCase.addTestUser();

        // Mock the session so AuthFilter will also return the test user
        HttpServletRequest mockServletRequest = Mockito.mock(HttpServletRequest.class);
        HttpSession mockHttpSession = Mockito.mock(HttpSession.class);
        Mockito.when(mockHttpSession.getAttribute(AuthFilter.USER_KEY)).thenReturn(user);
        Mockito.when(mockServletRequest.getSession()).thenReturn(mockHttpSession);
        AuthFilter.testLogin(mockServletRequest, user);
    }

    public LocalMemcacheServiceTestConfig getMemcacheConfig() {
        return memcacheConfig;
    }

    @Override
    protected void runTest() throws Throwable {
        super.runTest();
    }

    /**
     * Deconstructs the AppEngine environment.
     */
    @Override
    protected void tearDown() throws Exception {
        helper.tearDown();
        super.tearDown();
    }

}