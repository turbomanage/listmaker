package com.turbomanage.gwt.client;

/**
 * Created by david on 6/21/14.
 */
public interface Display {
    /**
     * Indicate to the display that processing is being done.
     */
    void startProcessing();

    /**
     * Indicate to the display that processing has completed.
     */
    void stopProcessing();
}