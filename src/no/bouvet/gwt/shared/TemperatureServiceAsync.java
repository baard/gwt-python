package no.bouvet.gwt.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TemperatureServiceAsync {
    void fahrToCelc(double degrees, AsyncCallback<Double> callback);
}
