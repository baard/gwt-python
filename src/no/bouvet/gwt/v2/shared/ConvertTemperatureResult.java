package no.bouvet.gwt.v2.shared;

import no.bouvet.gwt.v2.shared.lib.Result;

public class ConvertTemperatureResult implements Result {
    private double fahrenheits;
    private double celsius;
    
    ConvertTemperatureResult() {
    }
    
    public ConvertTemperatureResult(double fahrenheits, double celsius) {
        this.fahrenheits = fahrenheits;
        this.celsius = celsius;
    }
    
    public double getFahrenheits() {
        return fahrenheits;
    }
    
    public double getCelsius() {
        return celsius;
    }
}
