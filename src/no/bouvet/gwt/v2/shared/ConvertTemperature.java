package no.bouvet.gwt.v2.shared;

import no.rehn.gwt.remoting.shared.Action;

public class ConvertTemperature implements Action<ConvertTemperatureResult> {
    private double fahrenheits;
    
    ConvertTemperature() {
    }

    public ConvertTemperature(double fahrenheits) {
        this.fahrenheits = fahrenheits;
    }

    public double getFahrenheits() {
        return fahrenheits;
    }
}
