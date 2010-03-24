package no.bouvet.gwt.v2.server;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TemperatureServiceGwtRpcImplTest {
    TemperatureServiceGwtRpcImpl subject;

    @Before
    public void setUp() {
        subject = new TemperatureServiceGwtRpcImpl();
    }

    @Test
    public void testConversion() {
        assertEquals(-12.22, subject.fahrToCelc(10.0), 0.01);
    }
}
