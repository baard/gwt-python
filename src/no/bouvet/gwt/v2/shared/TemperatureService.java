package no.bouvet.gwt.v2.shared;

import com.google.gwt.user.client.rpc.RemoteService;

/**
 * Service that converts temperatures.
 */
public interface TemperatureService extends RemoteService {
    /**
     * Convert from Fahrenheit degrees to Celsius.
     * 
     * @param degrees
     *            Fahrenheit degrees
     * @return Celsius degrees
     */
    double fahrToCelc(double degrees);
}
