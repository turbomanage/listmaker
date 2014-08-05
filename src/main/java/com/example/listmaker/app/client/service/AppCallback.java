package com.example.listmaker.app.client.service;

import com.google.gwt.user.client.Window;
import com.example.listmaker.app.client.App;
import com.turbomanage.gwt.client.Display;
import com.turbomanage.gwt.client.event.ShowMessageEvent;
import com.turbomanage.gwt.client.ui.widget.MessageWidget;
import org.fusesource.restygwt.client.FailedStatusCodeException;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.logging.Level;

/**
 * Created by Gene on 7/10/2014.
 */
public abstract class AppCallback<R> implements MethodCallback<R> {

    private static final String LOGIN_FORM = "/login.html";
    private static final String SIGNUP_URL = "/signup.html";

    private final Display display;

    public AppCallback() {
        this.display = null;
    }

    public AppCallback(Display display) {
        this.display = display;
        display.startProcessing();
    }

    protected void handleFailure(Throwable e) {
        // Standard error handling
        // Allow override, but not required
        String s = "";
        for (StackTraceElement element : e.getStackTrace()) {
            s += element + "\n";
        }
        App.getEventBus().fireEvent(new ShowMessageEvent("An error has occurred", MessageWidget.MessageType.WARN));
    }

    public abstract void handleSuccess(R result);

    protected void reset(Throwable e) {
        if (display != null) {
            display.stopProcessing();
        }
    }

    @Override
    public void onSuccess(Method method, R responseObj) {
        try {
            handleSuccess(responseObj);
        } finally {
            reset(null);
        }
    }

    @Override
    public void onFailure(Method method, Throwable throwable) {
        String url = method.builder.getUrl();

        App.getLogger().log(Level.SEVERE, "Error calling service " + url, throwable);
        try {
            // Decode the exception
            if (throwable instanceof FailedStatusCodeException) {
                FailedStatusCodeException sce = (FailedStatusCodeException) throwable;
                App.getLogger().log(Level.SEVERE, "Service returned " + sce.getStatusCode() + sce.getMessage());
                if (401 == sce.getStatusCode()) {
                    Window.Location.replace(LOGIN_FORM);
                } else if (500 == sce.getStatusCode()) {
                    if ("UserNotRegisteredException".equals(sce.getMessage())) {
                        Window.Location.replace(SIGNUP_URL);
                    }
                }
            }
            handleFailure(throwable);
        } finally {
            reset(null);
        }
    }

}