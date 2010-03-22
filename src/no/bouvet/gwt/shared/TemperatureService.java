package no.bouvet.gwt.shared;

import com.google.gwt.user.client.rpc.RemoteService;

public interface TemperatureService extends RemoteService {
    double fahrToCelc(double degrees);
}
