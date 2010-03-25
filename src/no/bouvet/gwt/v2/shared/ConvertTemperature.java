package no.bouvet.gwt.v2.shared;

import no.bouvet.gwt.v2.shared.lib.Action;

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
