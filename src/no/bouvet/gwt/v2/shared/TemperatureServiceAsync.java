package no.bouvet.gwt.v2.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Asynchronous version of {@link TemperatureService}.
 * <p>
 * This is the interface that the GWT-client must use.
 */
public interface TemperatureServiceAsync {
    void fahrToCelc(double degrees, AsyncCallback<Double> callback);
}
