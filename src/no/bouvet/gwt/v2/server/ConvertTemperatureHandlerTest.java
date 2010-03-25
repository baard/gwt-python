package no.bouvet.gwt.v2.server;

import static org.junit.Assert.*;

import no.bouvet.gwt.v2.shared.ConvertTemperature;

import org.junit.Before;
import org.junit.Test;

public class ConvertTemperatureHandlerTest {
    ConvertTemperatureHandler subject;

    @Before
    public void setUp() {
        subject = new ConvertTemperatureHandler();
    }

    @Test
    public void testConversion() throws Exception {
        ConvertTemperature action = new ConvertTemperature(10.0);
        assertEquals(-12.22, subject.handle(action).getCelsius(), 0.01);
    }
}
