package no.bouvet.gwt.server;

import no.bouvet.gwt.shared.TemperatureService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class TemperatureServiceGwtRpcImpl extends RemoteServiceServlet implements TemperatureService {
    @Override
    public double fahrToCelc(double degrees) {
        return (degrees - 32) * 5 / 9;
    }
}