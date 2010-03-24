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
        RootPanel.get().add(createConversionWidget(createCherryPyService()));
    }
    
    Widget createConversionWidget(TemperatureServiceAsync service) {
        ConversionMessages messages = GWT.create(ConversionMessages.class);
        ConversionPresenter presenter = new ConversionPresenter(messages, service);
        ConversionDisplay.Images images = GWT.create(ConversionDisplay.Images.class);
        ConversionDisplay.Binder uiBinder = GWT.create(ConversionDisplay.Binder.class);
        ConversionDisplay display = new ConversionDisplay(messages, images, uiBinder);
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
