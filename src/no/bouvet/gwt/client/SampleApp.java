package no.bouvet.gwt.client;

import no.bouvet.gwt.shared.TemperatureService;
import no.bouvet.gwt.shared.TemperatureServiceAsync;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * The {@link EntryPoint} for the application.
 */
public class SampleApp implements EntryPoint {
    public void onModuleLoad() {
        TemperatureServiceAsync service = createCherryPyService();
        RootPanel.get().add(createTestableConversionWidget(service));
    }
    
    ConversionWidget createConversionWidget(TemperatureServiceAsync service) {
        return new ConversionWidget(service);
    }

    TestableConversionWidget createTestableConversionWidget(TemperatureServiceAsync service) {
        return new TestableConversionWidget(new TestableConversionWidget.Presenter(service));
    }

    TemperatureServiceAsync createGwtRpcService() {
        TemperatureServiceAsync remoteService = GWT.create(TemperatureService.class);
        ((ServiceDefTarget) remoteService).setServiceEntryPoint(GWT.getModuleBaseURL() + "temperatureService");
        return remoteService;
    }

    TemperatureServiceCherryPyImpl createCherryPyService() {
        return new TemperatureServiceCherryPyImpl(GWT.getModuleBaseURL());
    }
}
