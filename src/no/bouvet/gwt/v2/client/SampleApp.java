package no.bouvet.gwt.v2.client;

import no.bouvet.gwt.v2.shared.TemperatureService;
import no.bouvet.gwt.v2.shared.TemperatureServiceAsync;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * The {@link EntryPoint} for the application.
 */
public class SampleApp implements EntryPoint {
    public void onModuleLoad() {
        TemperatureServiceAsync service = createCherryPyService();
        RootPanel.get().add(createConversionWidget(service));
    }
    
    Widget createConversionWidget(TemperatureServiceAsync service) {
        ConversionPresenter presenter = new ConversionPresenter(service);
        ConversionDisplay display = new ConversionDisplay();
        presenter.bind(display);
        return display;
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