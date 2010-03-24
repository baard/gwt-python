package no.bouvet.gwt.v2.client;

import com.google.gwt.i18n.client.Messages;

interface ConversionMessages extends Messages {
    @DefaultMessage("From Fahrenheit:")
    String fahrenheits();
    
    @DefaultMessage("Convert!")
    String convert();

    @DefaultMessage("{0} Fahrenheits is {0} Celsius")
    String output(double fahrenheits, double celsius);
    
    @DefaultMessage("Server failure: {0}")
    String serverFailure(String message);

    @DefaultMessage("Converting...")
    String converting();
}