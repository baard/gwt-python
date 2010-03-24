package no.bouvet.gwt.v2.server;

import no.bouvet.gwt.v2.shared.TemperatureService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * GWT-RPC implementation of {@link TemperatureService}.
 */
@SuppressWarnings("serial")
public class TemperatureServiceGwtRpcImpl extends RemoteServiceServlet implements TemperatureService {
    @Override
    public double fahrToCelc(double degrees) {
        return (degrees - 32) * 5 / 9;
    }
}