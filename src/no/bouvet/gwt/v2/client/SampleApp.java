package no.bouvet.gwt.v2.client;

import no.bouvet.gwt.v2.shared.ConvertTemperature;
import no.bouvet.gwt.v2.shared.ConvertTemperatureResult;
import no.rehn.gwt.remoting.client.ActionHandlerAsync;
import no.rehn.gwt.remoting.client.DelegatingActionHandlerAsync;
import no.rehn.gwt.remoting.client.DispatchingActionServiceAsync;
import no.rehn.gwt.remoting.client.log.ActionLogger;
import no.rehn.gwt.remoting.client.log.LoggingActionServiceAsync;
import no.rehn.gwt.remoting.shared.ActionService;
import no.rehn.gwt.remoting.shared.ActionServiceAsync;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * The {@link EntryPoint} for the application.
 */
public class SampleApp implements EntryPoint {
    public void onModuleLoad() {
        DispatchingActionServiceAsync service = new DispatchingActionServiceAsync();
        service.addHandler(ConvertTemperature.class, createCherryPyService());
        HandlerManager eventBus = new HandlerManager(null);
        LoggingActionServiceAsync loggingService = new LoggingActionServiceAsync(eventBus, service);
        RootPanel.get().add(createConversionWidget(loggingService));
        RootPanel.get().add(createActionLogger(eventBus));
    }

    ActionLogger createActionLogger(HandlerManager eventBus) {
        return new ActionLogger(eventBus);
    }
    
    Widget createConversionWidget(ActionServiceAsync service) {
        ConversionMessages messages = GWT.create(ConversionMessages.class);
        ConversionPresenter presenter = new ConversionPresenter(messages, service);
        ConversionDisplay.Images images = GWT.create(ConversionDisplay.Images.class);
        ConversionDisplay.Binder uiBinder = GWT.create(ConversionDisplay.Binder.class);
        ConversionDisplay display = new ConversionDisplay(messages, images, uiBinder);
        presenter.bind(display);
        return display;
    }

    ActionHandlerAsync<ConvertTemperature, ConvertTemperatureResult> createGwtRpcService() {
        ActionServiceAsync actionService = GWT.create(ActionService.class);
        ((ServiceDefTarget) actionService).setServiceEntryPoint(GWT.getModuleBaseURL() + "actionService");
        return new DelegatingActionHandlerAsync<ConvertTemperature, ConvertTemperatureResult>(actionService);
    }

    ActionHandlerAsync<ConvertTemperature, ConvertTemperatureResult> createCherryPyService() {
        return new CherryPyConvertTemperatureHandler(GWT.getModuleBaseURL());
    }
}
