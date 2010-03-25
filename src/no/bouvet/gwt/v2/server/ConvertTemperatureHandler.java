package no.bouvet.gwt.v2.server;

import no.bouvet.gwt.v2.shared.ConvertTemperature;
import no.bouvet.gwt.v2.shared.ConvertTemperatureResult;
import no.rehn.gwt.remoting.server.ActionHandler;

/**
 * GWT-RPC implementation of a temperature converter.
 * <p>
 * Runs on the server.
 */
public class ConvertTemperatureHandler implements ActionHandler<ConvertTemperature, ConvertTemperatureResult>{
    @Override
    public ConvertTemperatureResult handle(ConvertTemperature action) throws Exception {
        double fahrenheits = action.getFahrenheits();
        double celsius = (fahrenheits - 32) * 5 / 9;
        //Thread.sleep(3000);
        return new ConvertTemperatureResult(fahrenheits, celsius);
    }
}
